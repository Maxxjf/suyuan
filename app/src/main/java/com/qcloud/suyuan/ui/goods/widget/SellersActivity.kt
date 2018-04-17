package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.SoundPool
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android_serialport_api.sample.Util
import com.google.gson.Gson
import com.ivsign.android.IDCReader.IDCReaderSDK
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.rxtask.RxScheduler
import com.qcloud.qclib.rxtask.task.NewTask
import com.qcloud.qclib.rxtask.task.UITask
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.BaseUrlUtil
import com.qcloud.qclib.utils.BitmapUtil
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.card_sellers_product_list.*
import kotlinx.android.synthetic.main.layout_sellers_operation.*
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

    private var settlementDialog: SettlementDialog? = null
    private var cashDialog: CashDialog? = null
    private var payConfirmDialog: PayConfirmDialog? = null
    // 刷身份证
    private var brushPurchaseDialog: BrushPurchaseDialog? = null
    // 输入身份证
    private var inputPurchaseDialog: InputPurchaseDialog? = null

    /**身份识别有关*/
    private val mIDCReaderSDK = IDCReaderSDK()
    /**读卡完成提醒声音*/
    private var soundPool: SoundPool? = null
    private var soundId: Int = 0
    private var isLoad = false
    private val mIDCBuffer = ByteArray(1600)

    private var keyword: String? = null

    private val moneyStr: String = "¥%1$.2f"
    private var totalNumber = 0
    private var totalAccount: Double = 0.00
    private var list: String = ""    // 商品列表
    private var purpose: String? = null // 购买用途
    private var remark: String? = null  // 备注
    private var discount: Double = 0.00 // 优惠价格
    private var receivablePrice: Double = 0.00   // 应收金额
    private var realPay: Double = 0.00  // 实付现金
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
        refreshPrice()
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
            isLoad = true
        })

        RxScheduler.doOnNewThread(object : NewTask<Void> {
            override fun doOnNewThread() {
                // 使用soundPool加载声音，该操作位异步操作，如果资源很大，需要一定的时间
                if (soundPool != null) {
                    soundId = soundPool!!.load(this@SellersActivity, R.raw.bb, 1)
                }
            }
        })
    }

    private fun initView() {
        btn_settlement.setOnClickListener(this)
        btn_clear_all.setOnClickListener(this)
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
                receivablePrice = totalAccount
                refreshPrice()
            }
        }
        mAdapter?.onHolderClick = object : CommonRecyclerAdapter.OnHolderClickListener<SaleProductBean> {
            override fun onHolderClick(view: View, t: SaleProductBean, position: Int) {
                totalNumber -= t.number
                totalAccount -= t.number * t.price
                receivablePrice = totalAccount
                refreshPrice()
                mAdapter?.remove(position)
            }
        }

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)
        showEmptyView(getString(R.string.tip_no_product_data))
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

    private fun refreshPrice() {
        tv_goods_number.text = totalNumber.toString()
        tv_total_account.text = String.format(moneyStr, totalAccount)
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
        if (isHighToxic()) {
            showPurchaseDialog()
        } else {
            showSettlementDialog()
        }
    }

    /**
     * 是否有高毒农药
     * */
    private fun isHighToxic(): Boolean {
        var isHighToxic = false
        if (mAdapter != null) {
            for (bean in mAdapter!!.mList) {
                if (bean.highToxic) {
                    isHighToxic = true
                    break
                }
            }
        }
        return isHighToxic
    }

    /**
     * 显示结算弹窗
     * */
    private fun showSettlementDialog() {
        if (settlementDialog == null) {
            settlementDialog = SettlementDialog(this)
        }
        settlementDialog?.refreshSettlementData(totalNumber, totalAccount, receivablePrice)
        settlementDialog?.show()
        settlementDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                receivablePrice = settlementDialog!!.realPrice
                discount = totalAccount - receivablePrice
                payMethod = settlementDialog!!.payMethod
                when (view.id) {
                    R.id.btn_cash -> showCashDialog()
                    R.id.btn_credit -> showInputPurchaseDialog()
                    else -> {
                        showPayConfirmDialog()
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
        cashDialog?.refreshData(receivablePrice)
        cashDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                if (view.id == R.id.btn_select_pay_method) {
                    showSettlementDialog()
                } else {
                    realPay = cashDialog!!.realPay
                    giveMoney = cashDialog!!.giveMoney

                    mPresenter?.saleSettlement(list, purchaseInfo, discount, realPay, payMethod, purpose, remark)
                }
            }
        }
    }

    /**
     * 显示支付确认
     * */
    private fun showPayConfirmDialog() {
        if (payConfirmDialog == null) {
            payConfirmDialog = PayConfirmDialog(this)
        }
        payConfirmDialog?.show()
        payConfirmDialog?.refreshPrice(payMethod, receivablePrice)
        payConfirmDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                if (view.id == R.id.btn_select_pay_method) {
                    showSettlementDialog()
                } else {
                    mPresenter?.saleSettlement(list, purchaseInfo, discount, realPay, payMethod, purpose, remark)
                }
            }
        }
    }

    /**
     * 显示身份识别弹窗
     * */
    private fun showPurchaseDialog() {
        NFCHelper.instance.isReadCard = true
        if (brushPurchaseDialog == null) {
            brushPurchaseDialog = BrushPurchaseDialog(this)
        }
        brushPurchaseDialog?.clearData()
        brushPurchaseDialog?.show()
        brushPurchaseDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                purpose = brushPurchaseDialog!!.purpose
                remark = brushPurchaseDialog!!.remark
                purchaseInfo?.mobile = brushPurchaseDialog!!.mobile

                showSettlementDialog()
            }
        }
        brushPurchaseDialog?.setOnDismissListener {
            NFCHelper.instance.isReadCard = false
        }
    }

    /**
     * 显示输入身份证弹窗
     * */
    private fun showInputPurchaseDialog() {
        if (inputPurchaseDialog == null) {
            inputPurchaseDialog = InputPurchaseDialog(this)
        }
        inputPurchaseDialog?.clearData()
        inputPurchaseDialog?.show()
        inputPurchaseDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                purchaseInfo = inputPurchaseDialog!!.currPurchaser

                showPayConfirmDialog()
            }
        }
    }

    override fun onClearAllClick() {
        mAdapter?.replaceList(ArrayList())
        clearData()
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
                receivablePrice = totalAccount
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
                        val contentBean = PrintContentBean()
                        contentBean.content = it.code + "\n" + it.name
                        contentBean.alignIndex = 1
                        contentBean.isWalk = 1
                        val printBean = PrintBean()
                        printBean.type = 2
                        printBean.qrCode = BaseUrlUtil.getBaseUrl() + it.codeUrl
                        printBean.content = contentBean
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

                clearData()
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
        ticketInfo.realPay = realPay                // 实收现金
        ticketInfo.giveMoney = giveMoney            // 找零

        TicketUtil.printTicket(ticketInfo)
    }

    /**
     * 清除数据
     * */
    private fun clearData() {
        keyword = null

        totalNumber = 0
        totalAccount  = 0.00
        receivablePrice = 0.00
        list = ""           // 商品列表
        remark = null       // 备注
        discount = 0.00     // 优惠价格
        realPay = 0.00      // 实付
        giveMoney = 0.00    // 找零
        payMethod = 0       // 支付方式

        refreshPrice()
        mAdapter?.replaceList(ArrayList())
        showEmptyView(getString(R.string.tip_no_product_data))

        submitProducts = ArrayList()

        purchaseInfo = null
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

                            if (isLoad) {
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

                            refreshPurchaser(purchaseInfo!!)
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
    private fun refreshPurchaser(bean: IDBean?) {
        if (bean != null) {
            val bitmap = BitmapFactory.decodeFile(bean.userImg)
            purchaseInfo?.userImgBase64 = BitmapUtil.bitmapToBase64(bitmap)

            brushPurchaseDialog?.refreshPurchase(bean)
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
        payConfirmDialog.let {
            if (payConfirmDialog != null && payConfirmDialog!!.isShowing) {
                payConfirmDialog?.dismiss()
            }
        }
        brushPurchaseDialog.let {
            if (brushPurchaseDialog != null && brushPurchaseDialog!!.isShowing) {
                brushPurchaseDialog?.dismiss()
            }
        }
        inputPurchaseDialog.let {
            if (inputPurchaseDialog != null && inputPurchaseDialog!!.isShowing) {
                inputPurchaseDialog?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SellersActivity::class.java))
        }
    }
}