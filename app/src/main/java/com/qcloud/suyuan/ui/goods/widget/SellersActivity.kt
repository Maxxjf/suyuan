package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.SoundPool
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android_serialport_api.sample.Util
import com.google.gson.Gson
import com.ivsign.android.IDCReader.IDCReaderSDK
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.rxtask.RxScheduler
import com.qcloud.qclib.rxtask.task.NewTask
import com.qcloud.qclib.rxtask.task.UITask
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.BitmapUtil
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.qclib.utils.ValidateUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.SellersAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.*
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.goods.presenter.impl.SellersPresenterImpl
import com.qcloud.suyuan.ui.goods.view.ISellersView
import com.qcloud.suyuan.utils.*
import com.qcloud.suyuan.utils.NFCHelper.Companion.PLEASECHECKNETWORKERR
import com.qcloud.suyuan.utils.NFCHelper.Companion.REPEAT5001
import com.qcloud.suyuan.utils.NFCHelper.Companion.STEP5001ERR
import com.qcloud.suyuan.utils.NFCHelper.Companion.STEP5001OK
import com.qcloud.suyuan.utils.NFCHelper.Companion.STEP5012ERR
import com.qcloud.suyuan.utils.NFCHelper.Companion.STEP5012OK
import com.qcloud.suyuan.utils.NFCHelper.Companion.STEP5022ERR
import com.qcloud.suyuan.utils.NFCHelper.Companion.STEP5022OK
import com.qcloud.suyuan.utils.NFCHelper.Companion.STEP6002ERR
import com.qcloud.suyuan.utils.NFCHelper.Companion.STEP6013ERR
import com.qcloud.suyuan.utils.NFCHelper.Companion.STEP6013OK
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.*
import com.qcloud.suyuan.widgets.pop.DropDownPop
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.card_sellers_product_list.*
import kotlinx.android.synthetic.main.card_sellers_purchaser_info.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Description: 卖货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:23.
 */
class SellersActivity: BaseActivity<ISellersView, SellersPresenterImpl>(), ISellersView, View.OnClickListener {
    private var mAdapter: SellersAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var mPurchaseUsePop: DropDownPop? = null

    private var inputDialog: InputDialog? = null
    private var tipDialog: TipDialog? = null
    private var settlementDialog: SettlementDialog? = null
    private var cashDialog: CashDialog? = null
    private var inputPurchaseDialog: InputPurchaseDialog? = null

    /**身份识别有关*/
    private val mIDCReaderSDK = IDCReaderSDK()
    /**读卡完成提醒声音*/
    private var soundPool: SoundPool? = null
    private var soundId: Int = 0
    private var isload = false
    private val mIDCBuffer = ByteArray(1600)

    private var keyword: String? = null

    private val moneyStr: String = "¥%1$.2f"
    private var totalNumber = 0
    private var totalAccount: Double = 0.00
    private var list: String = ""    // 商品列表
    private var purpose: String? = null // 购买用途
    private var remark: String? = null  // 备注
    private var discount: Double = 0.00 // 优惠价格
    private var realPay: Double = 0.00  // 实付
    private var giveMoney: Double = 0.00// 找零
    private var payMethod: Int = 0      // 支付方式

    private var purchaseInfo: IDBean? = null    // 购买者信息

    private var submitProducts: MutableList<TicketProductBean> = ArrayList()

    override val layoutId: Int
        get() = R.layout.activity_sellers

    override fun initPresenter(): SellersPresenterImpl? {
        return SellersPresenterImpl()
    }

    override fun initViewAndData() {
        initView()
        initRecyclerView()
        initEditView()
        initDropDown()
        refreshCashier()
        refreshPrice()
    }

    override fun onResume() {
        super.onResume()
        initRfid()
    }

    /**
     * 初始化身份识别器
     * */
    private fun initRfid() {
        // 分别对应声音池数量，AudioManager.STREAM_MUSIC 和 0
        soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        // 为声音池设定加载完成监听事件
        soundPool?.setOnLoadCompleteListener({ _, _, _ ->
            // 表示加载完成
            isload = true
        })

        RxScheduler.doOnNewThread(object : NewTask<Void> {
            override fun doOnNewThread() {
                // 使用soundPool加载声音，该操作位异步操作，如果资源很大，需要一定的时间
                if (soundPool != null) {
                    soundId = soundPool!!.load(this@SellersActivity, R.raw.bb, 1)
                }
            }
        })

        NFCHelper.instance.isReadCard = true
    }

    private fun initView() {
        tv_mobile.setOnClickListener(this)
        tv_other_instructions.setOnClickListener(this)
        btn_settlement.setOnClickListener(this)
        btn_input_purchase_info.setOnClickListener(this)
    }

    private fun loadData() {
        mPresenter?.loadData(keyword!!)
    }

    /**
     * 初始化列表
     * */
    private fun initRecyclerView() {
        list_product?.setLayoutManager(LinearLayoutManager(this))

        PullRefreshUtil.setRefresh(list_product, false, false)

        mAdapter = SellersAdapter(this)
        mAdapter?.replaceList(ArrayList())
        list_product?.setAdapter(mAdapter!!)
        mAdapter?.onRefreshNumClickListener = object : SellersAdapter.OnRefreshNumClickListener {
            override fun onRefreshNum(number: Int, bean: SaleProductBean) {
                totalNumber += number
                totalAccount += number * bean.price
                refreshPrice()
            }
        }
        mAdapter?.onHolderClick = object : CommonRecyclerAdapter.OnHolderClickListener<SaleProductBean> {
            override fun onHolderClick(view: View, t: SaleProductBean, position: Int) {
                totalNumber -= t.number
                totalAccount -= t.number * t.price
                refreshPrice()
                mAdapter?.remove(position)
            }
        }

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)
        showEmptyView(getString(R.string.hint_batch_code_search))
    }

    /**
     * 初始化搜索输入框
     * */
    private fun initEditView() {
        et_search.setOnKeyListener { _, action, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_UP) {
                if (action == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtil.hideKeybord(this, et_search)
                    keyword = et_search.text.toString().trim()
                    if (StringUtil.isNotBlank(keyword)) {
                        loadData()
                    } else {
                        QToast.show(this, R.string.hint_batch_code_search)
                    }
                }
            }
            false
        }
        btn_search.setOnClickListener {
            KeyBoardUtil.hideKeybord(this, et_search)
            keyword = et_search.text.toString().trim()
            if (StringUtil.isNotBlank(keyword)) {
                loadData()
            } else {
                QToast.show(this, R.string.hint_batch_code_search)
            }
        }
    }

    private fun reSetEditText() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    et_search.setText("")
                    et_search.requestFocus()
                }
    }

    /**
     * 初始化下拉弹窗
     * */
    private fun initDropDown() {
        val purchase = resources.getStringArray(R.array.purchase)
        val list: MutableList<String> = ArrayList()
        list.addAll(purchase)
        tv_purchase_use.text = list[0]
        btn_purchase_use.post {
            val width = btn_purchase_use.width
            mPurchaseUsePop = DropDownPop(this, list, width)

            mPurchaseUsePop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: Any?) {
                    if (value != null) {
                        tv_purchase_use.text = value.toString()
                    }
                }
            }
        }

        btn_purchase_use.setOnClickListener {
            mPurchaseUsePop?.showAsDropDown(btn_purchase_use)
        }
    }

    /**
     * 刷新收银员
     * */
    private fun refreshCashier() {
        val loginUser = UserInfoUtil.getUser()
        if (loginUser != null) {
            tv_money_getter.text = loginUser.nickname
        }
    }

    private fun refreshPrice() {
        tv_goods_number.text = totalNumber.toString()
        tv_total_account.text = String.format(moneyStr, totalAccount)
    }

    private fun showInput(view: TextView, type: Int) {
        if (inputDialog == null) {
            inputDialog = InputDialog(this)
        }
        inputDialog?.setBindView(view)
        inputDialog?.setInputValue(view.text.toString().trim())
        if (type == 0) {
            inputDialog?.setInputMethod(InputType.TYPE_CLASS_PHONE)
        } else {
            inputDialog?.setInputMethod(InputType.TYPE_CLASS_TEXT)
        }

        inputDialog?.show()
    }

    private fun initSaleProduct() {
        if (mAdapter != null) {
            submitProducts = ArrayList()
            val array = ArrayList<SubmitProductBean>()
            for (bean in mAdapter!!.mList) {
                val submit = SubmitProductBean()
                val ticket = TicketProductBean()
                submit.recordId = bean.recordId ?: "0"
                submit.goodsNum = bean.number
                submit.price = bean.price
                array.add(submit)

                ticket.name = bean.name ?: ""
                ticket.number = bean.number
                ticket.price = bean.price
                submitProducts.add(ticket)
            }
            list = Gson().toJson(array)
            Timber.e("list = $list")
        }
    }

    override fun onClick(v: View) {
        mPresenter?.onBtnClick(v.id)
    }

    override fun onSettlementClick() {
        if (totalNumber < 1) {
            QToast.show(this, R.string.toast_get_product_to_buy)
            return
        }
        initSaleProduct()
        if (purchaseInfo == null) {
            QToast.show(this, R.string.toast_input_purchaser_info)
            return
        }
        val mobile = tv_mobile.text.toString().trim()
        if (StringUtil.isBlank(mobile)) {
            QToast.show(this, R.string.toast_input_purchase_mobile)
            return
        }
        purchaseInfo?.mobile = mobile
        purpose = tv_purchase_use.text.toString().trim()
        remark = tv_other_instructions.text.toString().trim()

        if (tipDialog == null) {
            tipDialog = TipDialog(this)
        }
        tipDialog?.setTip(R.string.tip_buy_highly_toxic)
        tipDialog?.setConfirmBtn(R.string.btn_i_know)
        tipDialog?.show()
        tipDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                showSettlementDialog()
            }
        }
    }

    /**
     * 显示结算弹窗
     * */
    private fun showSettlementDialog() {
        if (settlementDialog == null) {
            settlementDialog = SettlementDialog(this)
        }
        settlementDialog?.refreshSettlementData(totalNumber, totalAccount)
        settlementDialog?.show()
        settlementDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                realPay = settlementDialog!!.realPay
                discount = totalAccount - realPay
                payMethod = settlementDialog!!.payMethod
                when (view.id) {
                    R.id.btn_cash -> showCashDialog()

                    else -> {
                        mPresenter?.saleSettlement(list, purchaseInfo!!, discount, realPay, payMethod, purpose, remark)
                    }
                }
            }
        }
    }

    /**
     * 显示现金支付弹窗
     * */
    private fun showCashDialog() {
        if (cashDialog == null) {
            cashDialog = CashDialog(this)
        }
        cashDialog?.show()
        cashDialog?.refreshData(realPay)
        cashDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                realPay = cashDialog!!.realPay
                giveMoney = cashDialog!!.giveMoney

                mPresenter?.saleSettlement(list, purchaseInfo!!, discount, realPay, payMethod, purpose, remark)
            }
        }
    }

    override fun onInputPurchaserClick() {
        if (inputPurchaseDialog == null) {
            inputPurchaseDialog = InputPurchaseDialog(this)
        }
        inputPurchaseDialog?.show()
        inputPurchaseDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                purchaseInfo = inputPurchaseDialog!!.currPurchaser
                refreshPurchaser(purchaseInfo, false)
            }
        }
    }

    override fun onMobileClick() {
        if (isRunning) {
            showInput(tv_mobile, 0)
        }
    }

    override fun onRemarkClick() {
        if (isRunning) {
            showInput(tv_other_instructions, 1)
        }
    }

    override fun replaceList(beans: List<SaleProductBean>?) {
        if (isRunning) {
            if (beans != null && beans.isNotEmpty()) {
                mAdapter?.replaceList(beans)
            }
        }
    }

    override fun addBeanAtEnd(bean: SaleProductBean) {
        if (isRunning) {
            if (!isFirstAdd()) {
                hideEmptyView()
            }
            reSetEditText()
            mAdapter?.refreshBean(bean)
            if (totalNumber < bean.stock) {
                totalNumber += bean.number
                totalAccount += bean.number * bean.price
                refreshPrice()
            }
        }
    }

    override fun searchFailure() {
        if (isRunning) {
            reSetEditText()
            QToast.show(this, R.string.tip_no_any_product_get)
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isRunning) {
            reSetEditText()
            if (isShow) {
                QToast.show(this, errMsg)
            } else {
                Timber.e(errMsg)
            }
        }
    }

    private fun isFirstAdd(): Boolean {
        if (mAdapter == null) {
            return false
        }
        return mAdapter!!.mList.size > 0
    }

    override fun showEmptyView(tip: String) {
        if (isRunning) {
            mEmptyView?.noData(tip)
            list_product?.showEmptyView()
        }
    }

    override fun hideEmptyView() {
       list_product?.hideEmptyView()
    }

    override fun settlementSuccess(bean: SettlementResBean?) {
        if (isRunning) {
            QToast.info(this@SellersActivity, R.string.tip_printing_suyuan_code, false)
            if (bean != null) {
                // 打印溯源码
                if (bean.traceabilityList != null) {
                    for (it in bean.traceabilityList!!) {
                        val printBean = PrintBean()
                        printBean.type = 1
                        printBean.barCode = it.code
                        PrintHelper.instance.printData(printBean)
                    }
                    initTicketData(bean.orderNo, bean.orderTime)
                } else {
                    initTicketData(bean.orderNo, bean.orderTime)
                }
                if (purchaseInfo != null) {
                    purchaseInfo?.id = bean.purchaserId
                    IDCardUtil.addOrUpdate(purchaseInfo)
                }
            }
        }
    }

    /**
     * 初始化打印小票内容
     * */
    private fun initTicketData(orderNo: String?, orderTime: String?) {
        val ticketInfo = TicketBean()

        val storeInfo = UserInfoUtil.getStore()
        if (storeInfo != null) {
            ticketInfo.storeName = storeInfo.name ?: ""     // 门店名称
        }
        ticketInfo.orderNo = orderNo ?: ""          // 订单编号
        ticketInfo.orderTime = orderTime ?: ""      // 下单时间
        ticketInfo.list = submitProducts            // 购买的产品
        ticketInfo.totalNumber = totalNumber        // 商品总数
        ticketInfo.totalAccount = totalAccount      // 合计
        ticketInfo.payMethod = payMethod            // 支付方式
        ticketInfo.discount = discount              // 优惠
        ticketInfo.realPay = realPay                // 实收
        ticketInfo.giveMoney = giveMoney            // 找零

        TicketUtil.printTicket(ticketInfo)

        clearData()
    }

    /**
     * 清除数据
     * */
    private fun clearData() {
        keyword = null

        totalNumber = 0
        totalAccount  = 0.00
        list = ""           // 商品列表
        remark = null       // 备注
        discount = 0.00     // 优惠价格
        realPay = 0.00      // 实付
        giveMoney = 0.00    // 找零
        payMethod = 0       // 支付方式

        refreshPrice()
        mAdapter?.replaceList(ArrayList())
        showEmptyView(getString(R.string.hint_batch_code_search))
        tv_other_instructions.text = ""

        submitProducts = ArrayList()

        purchaseInfo = null
        img_user_head.setImageResource(R.drawable.bmp_user_head)
        tv_user_name.text = ""
        tv_user_id.text = ""
        tv_mobile.text = ""

        initDropDown()
    }

    /**
     * 处理身份识别接收回来的数据
     * */
    override fun disposeRfidReceivedData(bean: IDVerifyResultBean) {
        if (isRunning) {
            RxScheduler.doOnUiThread(object : UITask<Void> {
                override fun doOnUIThread() {
                    when (bean.funret) {
                        STEP5001ERR, STEP5012ERR, STEP5022ERR, STEP6002ERR, STEP6013ERR -> {
                            Timber.e("读证错误:" + Util.bytesToHexString(Util.intTo2byte(bean.funret))!!)
                            //QToast.show(this@SellersActivity, R.string.toast_read_card_error)
                        }
                        PLEASECHECKNETWORKERR -> {
                            Timber.e( "请检查网络连接")
                            //QToast.show(this@SellersActivity, R.string.toast_read_card_timeout)
                        }
                        STEP5001OK -> {
                            NFCHelper.instance.uartSend2Head(12, bean.uartRecv)
                            Timber.e( "寻卡成功")
                        }
                        STEP5012OK -> {
                            NFCHelper.instance.uartSend2Head(9, bean.uartRecv)
                            Timber.e( "选卡成功")
                        }
                        STEP5022OK -> {
                            NFCHelper.instance.uartSend2Head(12, bean.uartRecv)
                            Timber.e( "读证..")
                        }
                        STEP6013OK -> {
                            Timber.e( "读证成功")

                            val recIndexPos = 1295
                            System.arraycopy(NFCHelper.mIDNRsock.recData, 0, mIDCBuffer, 0, recIndexPos)

                            if (isload) {
                                soundPool?.play(soundId, 1.0f, 0.5f, 1, 0, 1.5f)
                            }
                            // 解码图片：
                            mIDCReaderSDK.DoDecodeCardInfo(mIDCBuffer)
                            val birthday: String? = mIDCReaderSDK.GetPeopleBirthday().trim()

                            purchaseInfo = IDBean()
                            purchaseInfo?.idCode = mIDCReaderSDK.GetPeopleIDCode().trim()
                            purchaseInfo?.name = mIDCReaderSDK.GetPeopleName().trim()
                            purchaseInfo?.sex = mIDCReaderSDK.GetPeopleSex().trim()
                            purchaseInfo?.nation = mIDCReaderSDK.GetPeopleNation().trim()
                            if (StringUtil.isNotBlank(birthday)) {
                                purchaseInfo?.birthday = birthday
                                purchaseInfo?.year = birthday!!.substring(0, 4)
                                purchaseInfo?.month = birthday.substring(4, 6)
                                purchaseInfo?.day = birthday.substring(6, 8)
                            }
                            purchaseInfo?.address = mIDCReaderSDK.GetPeopleAddress().trim()
                            purchaseInfo?.department = mIDCReaderSDK.GetDepartment().trim()
                            purchaseInfo?.startDate = mIDCReaderSDK.GetStartDate().trim()
                            purchaseInfo?.endDate = mIDCReaderSDK.GetEndDate().trim()
                            purchaseInfo?.userImg = AppConstants.SDPATH + "/wltlib/zp.bmp"

                            refreshPurchaser(purchaseInfo!!, true)
                            Timber.e("people = $purchaseInfo")
                        }
                        REPEAT5001 -> {
                            Timber.e( "Repeat 5001...")
                        }
                        else -> {
                            Timber.e( "Repeat ${bean.funret}")
                        }
                    }
                }
            })
        }
    }

    /**
     * 刷新购买者信息
     * */
    private fun refreshPurchaser(bean: IDBean?, isFromRead: Boolean = true) {
        if (bean != null) {
            with(bean) {
                if (userImgBase64 != null) {
                    val bitmap = BitmapUtil.base64ToBitmap(userImgBase64!!)
                    img_user_head.setImageBitmap(bitmap)
                } else if (isFromRead) {
                    val bitmap = BitmapFactory.decodeFile(userImg)
                    purchaseInfo?.userImgBase64 = BitmapUtil.bitmapToBase64(bitmap)
                    img_user_head.setImageBitmap(bitmap)
                } else {
                    img_user_head.setImageResource(R.drawable.bmp_user_head)
                }

                tv_user_name.text = bean.name
                tv_user_id.text = ValidateUtil.setIdCodeToPassword(bean.idCode)
                tv_mobile.text = bean.mobile
            }
        }
    }

    override fun onStop() {
        super.onStop()
        NFCHelper.instance.isReadCard = false
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.let {
            soundPool?.release()
            soundPool = null
        }

        mPurchaseUsePop.let {
            if (mPurchaseUsePop != null && mPurchaseUsePop!!.isShowing) {
                mPurchaseUsePop?.dismiss()
            }
        }
        tipDialog.let {
            if (tipDialog != null && tipDialog!!.isShowing) {
                tipDialog?.dismiss()
            }
        }
        settlementDialog.let {
            if (settlementDialog != null && settlementDialog!!.isShowing) {
                settlementDialog?.dismiss()
            }
        }
        cashDialog.let {
            if (cashDialog != null && cashDialog!!.isShowing) {
                cashDialog?.dismiss()
            }
        }
        inputPurchaseDialog.let {
            if (inputPurchaseDialog != null && inputPurchaseDialog!!.isShowing) {
                inputPurchaseDialog?.dismiss()
            }
        }

        inputDialog.let {
            if (inputDialog != null && inputDialog!!.isShowing) {
                inputDialog?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SellersActivity::class.java))
        }
    }
}