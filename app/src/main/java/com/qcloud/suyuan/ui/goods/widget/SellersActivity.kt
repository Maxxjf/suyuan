package com.qcloud.suyuan.ui.goods.widget

import IDNR_JAR.IDNRSocket
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android_serialport_api.SerialPort
import android_serialport_api.sample.Util
import com.ivsign.android.IDCReader.IDCReaderSDK
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.rxtask.RxScheduler
import com.qcloud.qclib.rxtask.task.IOTask
import com.qcloud.qclib.rxtask.task.NewTask
import com.qcloud.qclib.rxtask.task.UITask
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.SellersAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.SellersBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.goods.presenter.impl.SellersPresenterImpl
import com.qcloud.suyuan.ui.goods.view.ISellersView
import com.qcloud.suyuan.utils.NFCUtil
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.CashDialog
import com.qcloud.suyuan.widgets.dialog.InputPurchaseDialog
import com.qcloud.suyuan.widgets.dialog.SettlementDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
import com.qcloud.suyuan.widgets.pop.DropDownPop
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import kotlinx.android.synthetic.main.card_sellers_product_list.*
import kotlinx.android.synthetic.main.card_sellers_purchaser_info.*
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.security.InvalidParameterException
import java.util.concurrent.TimeUnit
import kotlin.experimental.xor

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

    private fun initView() {
        btn_settlement.setOnClickListener(this)
        btn_input_purchase_info.setOnClickListener(this)
    }

    /**
     * 初始化列表
     * */
    private fun initRecyclerView() {
        list_product?.setLayoutManager(LinearLayoutManager(this))

        PullRefreshUtil.setRefresh(list_product, false, false)

        mAdapter = SellersAdapter(this)
        list_product?.setAdapter(mAdapter!!)

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        mPresenter?.loadData()
    }

    /**
     * 初始化搜索输入框
     * */
    private fun initEditView() {
        et_search.setOnKeyListener { _, action, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_UP) {
                if (action == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtil.hideKeybord(this, et_search)
                    val inputValue = et_search.text.toString().trim()
                    if (StringUtil.isNotBlank(inputValue)) {
                        getScanData(inputValue)
                    } else {
                        QToast.show(this, R.string.toast_no_input_value)
                    }
                }
            }
            false
        }
    }

    /**
     * 获取扫码数据
     * */
    private fun getScanData(inputValue: String) {
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
        val list: MutableList<String> = ArrayList()
        list.add("病虫防治1")
        list.add("病虫防治2")
        list.add("病虫防治3")
        list.add("病虫防治4")
        list.add("病虫防治5")
        btn_purchase_use.post {
            val width = btn_purchase_use.width
            mPurchaseUsePop = DropDownPop(this, list, width)

            mPurchaseUsePop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: String) {
                    tv_purchase_use.text = value
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

    override fun replaceList(beans: List<SellersBean>?) {
        if (isRunning) {
            if (beans != null && beans.isNotEmpty()) {
                mAdapter?.replaceList(beans)
            }
        }
    }

    override fun addBeanAtEnd(bean: SellersBean) {
        if (isRunning) {
            mAdapter?.addBeanAtEnd(bean)
        }
    }

    override fun showEmptyView(tip: String) {
        list_product?.showEmptyView()
    }

    override fun hideEmptyView() {
       list_product?.hideEmptyView()
    }

    /**
     * =============== 身份识别有关 ===========
     * */
    // 读卡完成提醒声音
    private var soundPool: SoundPool? = null
    private var soundId: Int = 0
    private var isload = false

    private var mBuffer: ByteArray = ByteArray(1)
    private val mIDCBuffer = ByteArray(1600)
    private var rec_index_pos = 0

    private var mRfidPort: SerialPort? = null
    private var mRfidOutputStream: OutputStream? = null
    private var mRfidInputStream: InputStream? = null

    private val mIDNRsock = IDNRSocket()
    private val mIDCReaderSDK = IDCReaderSDK()

    private var isReading: Boolean = true
    private var mFindThread: DisposableSubscriber<Long>? = null

    /**
     * 初始化身份识别器
     * */
    private fun initRfid() {
        try {
            val file = File(AppConstants.CIDSTRING)

            // 检查参数
            if (!file.exists()) {
                QToast.show(this, R.string.toast_init_nfc_failure)
                return
            }
            mRfidPort = SerialPort(file, AppConstants.BAUDRATT, 0)
            mRfidOutputStream = mRfidPort!!.outputStream
            mRfidInputStream = mRfidPort!!.inputStream

            isReading = true
            startReadThread()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 分别对应声音池数量，AudioManager.STREAM_MUSIC 和 0
        soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        // 为声音池设定加载完成监听事件
        soundPool?.setOnLoadCompleteListener({ _, _, _ ->
            // 表示加载完成
            isload = true
        })

        RxScheduler.doOnNewThread(object : NewTask<Void> {
            override fun doOnNewThread() {
                try {
                    NFCUtil.doExec("echo -wdir01 1 > /sys/devices/virtual/misc/mtgpio/pin")
                    NFCUtil.doExec("echo -wdout01 1 > /sys/devices/virtual/misc/mtgpio/pin")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                NFCUtil.copyAssets(this@SellersActivity, "wltlib", AppConstants.SDPATH + "/wltlib")
                // 使用soundPool加载声音，该操作位异步操作，如果资源很大，需要一定的时间
                if (soundPool != null) {
                    soundId = soundPool!!.load(this@SellersActivity, R.raw.bb, 1)
                }
            }

        })

        startFindThread()
    }

    /**
     * 创建一个寻卡线程
     * */
    private fun startFindThread() {
        if (mFindThread != null && !mFindThread!!.isDisposed) {
            mFindThread!!.dispose()
        }

        mFindThread = object : DisposableSubscriber<Long>() {
            override fun onNext(aLong: Long?) {
                if (!File(AppConstants.SDPATH + "/wltlib").exists()) {
                    NFCUtil.copyAssets(this@SellersActivity, "wltlib", AppConstants.SDPATH + "/wltlib")
                } else if (File(AppConstants.SDPATH + "/wltlib").listFiles()!!.size < 4) {
                    NFCUtil.copyAssets(this@SellersActivity, "wltlib", AppConstants.SDPATH + "/wltlib")
                } else {
                    if (!mIDNRsock.UartInUsing) {
                        uartSend2Head(2, Util.hexStringToBytes("5000"))
                        mIDNRsock.ReadIDInStep = 0
                        Timber.i("发送寻卡指令")
                    }
                }
            }

            override fun onError(t: Throwable) {
                Timber.e("发送寻卡指令失败: ${t.message}")
            }

            override fun onComplete() {

            }
        }

        // 间隔1秒执行一次
        Flowable.interval(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mFindThread)
    }

    /**
     * 创建一个接收线程
     * */
    private fun startReadThread() {
        RxScheduler.doOnIOThread(object : IOTask<Void> {
            override fun doOnIOThread() {
                while (isReading) {
                    try {
                        val buffer = ByteArray(128)    //32
                        if (mRfidInputStream == null) {
                            return
                        }
                        val size = mRfidInputStream!!.read(buffer)

                        if (size > 0) {
                            val uartRecv = ByteArray(1500)
                            val funret = try {
                                mIDNRsock.ReadIDC(AppConstants.ID_VERIFY_SERVER_IP, AppConstants.ID_VERIFY_SERVER_PORT, buffer, size, uartRecv)
                            } catch (e: IOException) {
                                e.printStackTrace()
                                0x7000
                            }
                            disposeRfidReceivedData(uartRecv, funret)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return
                    }
                }
            }
        })
    }

    /**
     * 处理接收回来的数据
     * */
    private fun disposeRfidReceivedData(uartRecv: ByteArray, funret: Int) {
        RxScheduler.doOnUiThread(object : UITask<Void> {
            override fun doOnUIThread() {
                when (funret) {
                    STEP5001ERR, STEP5012ERR, STEP5022ERR, STEP6002ERR, STEP6013ERR -> {
                        Timber.e("读证错误:" + Util.bytesToHexString(Util.intTo2byte(funret))!!)
                    }

                    PLEASECHECKNETWORKERR -> {
                        Timber.e( "请检查网络连接")
                    }

                    STEP5001OK -> {
                        uartSend2Head(12, uartRecv)
                        Timber.e( "寻卡成功")
                    }
                    STEP5012OK -> {
                        uartSend2Head(9, uartRecv)
                        Timber.e( "选卡成功")
                    }
                    STEP5022OK -> {
                        uartSend2Head(12, uartRecv)
                        Timber.e( "读证..")
                    }
                    STEP6013OK -> {
                        Timber.e( "读证成功")

                        rec_index_pos = 1295
                        System.arraycopy(mIDNRsock.recData, 0, mIDCBuffer, 0, rec_index_pos)

                        if (isload) {
                            soundPool?.play(soundId, 1.0f, 0.5f, 1, 0, 1.5f)
                        }
                        // 解码图片：
                        mIDCReaderSDK.DoDecodeCardInfo(mIDCBuffer)

                        val data1 = mIDCReaderSDK.GetPeopleBirthday()

                        var data2 = mIDCReaderSDK.GetStartDate()
                        data2 = data2.substring(0, 4) + "." + data2.substring(4, 6) + "." + data2.substring(6, 8) + ""

                        var data3 = mIDCReaderSDK.GetEndDate()
                        data3 = data3.substring(0, 4) + "." + data3.substring(4, 6) + "." + data3.substring(6, 8) + ""

                        Timber.e( "name = " + mIDCReaderSDK.GetPeopleName())
                        Timber.e( "sex = " + mIDCReaderSDK.GetPeopleSex())
                        Timber.e( "minzu = " + mIDCReaderSDK.GetPeopleNation())
                        Timber.e( "year = " + data1.substring(0, 4))
                        Timber.e( "month = " + data1.substring(4, 6))
                        Timber.e( "day = " + data1.substring(6, 8))
                        Timber.e( "address = " + mIDCReaderSDK.GetPeopleAddress())
                        Timber.e( "qixiao = " + mIDCReaderSDK.GetDepartment())
                        Timber.e( "jiguan = $data2-$data3")
                    }
                    REPEAT5001 -> {
                        Timber.e( "Repeat 5001...")
                    }
                    else -> {
                        Timber.e( "default Repeat 5001...")
                    }
                }
            }
        })
    }

    private fun uartSend2Head(j: Int, buff: ByteArray) {
        var verify: Byte = 0
        val txBuffer = ByteArray(50)

        txBuffer[0] = 0x02
        val len = Util.intTo2byte(j)
        txBuffer[1] = len[0]
        txBuffer[2] = len[1]
        System.arraycopy(buff, 0, txBuffer, 3, j)

        var i = 3
        while (i < j + 3) { // 校验=长数据（nB）/0x100
            verify = verify.xor(txBuffer[i])
            i++
        }
        txBuffer[j + 3] = verify
        txBuffer[j + 4] = 0x03
        startRfidSendingThread(txBuffer, j + 5)
    }

    private fun startRfidSendingThread(buffer: ByteArray, len: Int) {
        mBuffer = ByteArray(len)
        for (i in 0 until len) {
            mBuffer[i] = buffer[i]
        }
        if (mRfidPort != null) {
            if (mRfidOutputStream != null) {
                try {
                    mRfidOutputStream?.write(mBuffer)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (mFindThread != null && !mFindThread!!.isDisposed) {
            mFindThread!!.dispose()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 身份识别有关
        isReading = false


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
        private val STEP5001OK = 0x5001
        private val STEP5012OK = 0x5012
        private val STEP5022OK = 0x5022
        private val STEP6013OK = 0x6013
        private val STEP5001ERR = 0x7001
        private val STEP5012ERR = 0x7002
        private val STEP5022ERR = 0x7003
        private val STEP6002ERR = 0x7004
        private val STEP6013ERR = 0x7005
        private val REPEAT5001 = 0x7117
        private val PLEASECHECKNETWORKERR = 0x7118

        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SellersActivity::class.java))
        }
    }
}