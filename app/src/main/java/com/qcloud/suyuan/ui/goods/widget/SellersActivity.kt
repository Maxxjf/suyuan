package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android_serialport_api.sample.Util
import com.ivsign.android.IDCReader.IDCReaderSDK
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.rxtask.RxScheduler
import com.qcloud.qclib.rxtask.task.NewTask
import com.qcloud.qclib.rxtask.task.UITask
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.qclib.utils.ValidateUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.SellersAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.IDBean
import com.qcloud.suyuan.beans.IDVerifyResultBean
import com.qcloud.suyuan.beans.SaleProductBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.goods.presenter.impl.SellersPresenterImpl
import com.qcloud.suyuan.ui.goods.view.ISellersView
import com.qcloud.suyuan.utils.NFCHelper
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
import com.qcloud.suyuan.widgets.dialog.CashDialog
import com.qcloud.suyuan.widgets.dialog.InputPurchaseDialog
import com.qcloud.suyuan.widgets.dialog.SettlementDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
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

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)
        showEmptyView(getString(R.string.tip_to_scan_or_search))
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
                        QToast.show(this, R.string.toast_no_input_value)
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
                QToast.show(this, R.string.toast_no_input_value)
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
        btn_purchase_use.post {
            val width = btn_purchase_use.width
            mPurchaseUsePop = DropDownPop(this, list, width)

            mPurchaseUsePop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: Any?) {
                    //tv_purchase_use.text = value
                }
            }
        }

        btn_purchase_use.setOnClickListener {
            mPurchaseUsePop?.showAsDropDown(btn_purchase_use)
        }
    }

    override fun onClick(v: View) {
        mPresenter?.onBtnClick(v.id)
    }

    override fun onSettlementClick() {
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

    private fun showSettlementDialog() {
        if (settlementDialog == null) {
            settlementDialog = SettlementDialog(this)
        }
        settlementDialog?.show()
        settlementDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                when (view.id) {
                    R.id.btn_cash -> showCashDialog()
                }
            }
        }
    }

    private fun showCashDialog() {
        if (cashDialog == null) {
            cashDialog = CashDialog(this)
        }
        cashDialog?.show()
        cashDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                QToast.info(this@SellersActivity, R.string.tip_printing_suyuan_code, false)
            }
        }
    }

    override fun onInputPurchaserClick() {
        if (inputPurchaseDialog == null) {
            inputPurchaseDialog = InputPurchaseDialog(this)
        }
        inputPurchaseDialog?.show()
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
            mAdapter?.addBeanAtEnd(bean)
        }
    }

    override fun searchFailure() {
        if (isRunning) {
            QToast.show(this, R.string.tip_no_any_product_get)
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isRunning) {
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
                        }
                        PLEASECHECKNETWORKERR -> {
                            Timber.e( "请检查网络连接")
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
                            val birthday: String? = mIDCReaderSDK.GetPeopleBirthday()

                            val people = IDBean()
                            people.idCode = mIDCReaderSDK.GetPeopleIDCode()
                            people.name = mIDCReaderSDK.GetPeopleName()
                            people.sex = mIDCReaderSDK.GetPeopleSex()
                            people.nation = mIDCReaderSDK.GetPeopleNation()
                            if (StringUtil.isNotBlank(birthday)) {
                                people.birthday = birthday
                                people.year = birthday!!.substring(0, 4)
                                people.month = birthday.substring(4, 6)
                                people.day = birthday.substring(6, 8)
                            }
                            people.address = mIDCReaderSDK.GetPeopleAddress()
                            people.department = mIDCReaderSDK.GetDepartment()
                            people.startDate = mIDCReaderSDK.GetStartDate()
                            people.endDate = mIDCReaderSDK.GetEndDate()
                            people.userImg = AppConstants.SDPATH + "/wltlib/zp.bmp"

                            refreshPurchaser(people)
                            Timber.e("people = $people")
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

    private fun refreshPurchaser(bean: IDBean) {
        with(bean) {
            GlideUtil.loadCircleImageForFile(this@SellersActivity, img_user_head, bean.userImg, R.drawable.bmp_user_head)
            tv_user_name.text = bean.name
            tv_user_id.text = ValidateUtil.setIdCodeToPassword(bean.idCode)
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
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SellersActivity::class.java))
        }
    }
}