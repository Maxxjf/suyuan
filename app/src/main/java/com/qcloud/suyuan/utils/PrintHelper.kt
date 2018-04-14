package com.qcloud.suyuan.utils

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.usb.UsbDevice
import android.support.annotation.NonNull
import com.qcloud.qclib.beans.RxBusEvent
import com.qcloud.qclib.rxbus.Bus
import com.qcloud.qclib.rxbus.BusProvider
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseApplication
import com.qcloud.suyuan.beans.PrintBean
import com.qcloud.suyuan.beans.PrintContentBean
import com.qcloud.suyuan.widgets.dialog.PrinterDialog
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import timber.log.Timber
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit


/**
 * 类说明：打印机实现类
 * Author: Kuzan
 * Date: 2018/3/26 21:34.
 */
class PrintHelper private constructor() {
    /**设备信息，定义5台设备 vid[0] pid[1]*/
    private var mUsbInfo: Array<IntArray> = Array(5) { IntArray(2) }
    /**设备控制器*/
    private var mUsbController: UsbController? = null
    /**当前设备*/
    private var mUsbDevice: UsbDevice? = null
    private var mEventBus: Bus? = BusProvider.instance

    private var mCheckPaperThread: DisposableSubscriber<Long>? = null
    private var mContext: Context? = null

    /**
     * 初始化打印机，建议登录成功后初始化
     * */
    fun initPrinter(@NonNull context: Context) {
        mContext = context
        initEventBus()
        mUsbController = UsbController(context)

        // 定义5台设备
        mUsbInfo = Array(5) { IntArray(2) } // vid[0] pid[1]
        mUsbInfo[0][0] = 0x1CBE
        mUsbInfo[0][1] = 0x0a03
        mUsbInfo[1][0] = 0x0485
        mUsbInfo[1][1] = 0x7541
        mUsbInfo[2][0] = 0x1CB0
        mUsbInfo[2][1] = 0x0003
        mUsbInfo[3][0] = 0x0525
        mUsbInfo[3][1] = 0xa702
        mUsbInfo[4][0] = 0x28E9
        mUsbInfo[4][1] = 0x0289

        // 查找设备
        mUsbController?.close()
        for (i in 0..4) {
            mUsbDevice = mUsbController?.getDev(mUsbInfo[i][0], mUsbInfo[i][1])
            if (mUsbDevice != null) {
                break
            }
        }
        // 获取权限
        if (mUsbController != null && mUsbDevice != null) {
            if (mUsbController!!.isHasPermission(mUsbDevice!!)) {
                Timber.e("已获取usb设备访问权限")
            } else {
                Timber.e("请求usb设备权限.")
                mUsbController!!.getPermission(mUsbDevice!!)
            }
        }
    }

    private fun initEventBus() {
        if (mEventBus == null) {
            mEventBus = BusProvider.instance
        }
        try {
            mEventBus?.register(this@PrintHelper)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (mEventBus != null) {
            mEventBus!!.registerSubscriber(this@PrintHelper, mEventBus!!.obtainSubscriber(RxBusEvent::class.java, Consumer {
                when (it.type) {
                    R.id.id_printer_connect_result -> {
                        val result: Int = it.obj as Int
                        isInit = result == UsbController.USB_CONNECTED
                    }
                }
            }))
        }
    }

    /**
     * 打印数据
     * */
    @Synchronized
    fun printData(bean: PrintBean?) {
        if (bean == null) {
            return
        }
        Timber.e("print: $bean")
        if (mPrintQueue.isEmpty() && !isPrinting) {
            doPrint(bean)
        } else {
            mPrintQueue.add(bean)
        }
    }

    /**
     * 打印下一条数据
     * */
    @Synchronized
    private fun nextPrint() {
        if(!mPrintQueue.isEmpty() && !isPrinting) {
            doPrint(mPrintQueue.poll())
        }
    }

    /**
     * 开始打印
     * */
    private fun doPrint(bean: PrintBean) {
        isPrinting = true
        Observable.just(bean)
                .map {
                    var result = 0
                    try {
                        val isHasPaper: Byte = mUsbController!!.revByte(mUsbDevice!!)
                        Timber.e("isHasPaper = ${isHasPaper.toInt()}, isNoPaper = $IS_NO_PAPER")
                        if (isHasPaper.toInt() == IS_NO_PAPER) {
                            result = 0
                        } else {
                            // 设置蜂鸣指令
                            mUsbController?.sendByte(cmdBeep, mUsbDevice!!)

                            result = when (bean.type) {
                                1 -> {
                                    // 打印条形码
                                    printText(bean.content)
                                    printBarCode(bean.barCode)
                                }
                                2 -> {
                                    // 打印二维码
                                    printText(bean.content)
                                    printQrCode(bean.qrCode)
                                }
                                else -> {
                                    // 打印文本小票
                                    printText(bean.content)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Timber.e("Exception: ${e.message}")
                        result = -1
                    }

                    result
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // next
                    isPrinting = false
                    when (it) {
                        1 -> {
                            // 成功
                            if (mContext != null) {
                                QToast.success(mContext, mContext?.getString(R.string.tip_print_success))
                            }
                            nextPrint()
                        }
                        0 -> {
                            // 没纸
                            showTip(mContext?.getString(R.string.tip_no_paper_notice))
                        }
                        -1 -> {
                            // 出错
                            if (mContext != null) {
                                QToast.error(mContext, mContext?.getString(R.string.tip_re_init_printer))
                            }
                            mPrintQueue.add(bean)
                            restart()
                        }
                    }
                }, {
                    // 异常
                    isPrinting = false
                    Timber.e("Throwable: ${it.message}")
                }, {
                    // complete
                    isPrinting = false
                })
    }

    /**
     * 打印文本
     * */
    private fun printText(content: PrintContentBean?): Int {
        if (content == null || StringUtil.isEmpty(content.content)) {
            return -1
        }
        return try {
            // 初始化打印机
            mUsbController?.sendByte(cmdInitPrinter, mUsbDevice!!)
            if (content.isDouble == 1) {
                mUsbController?.sendByte(cmdDoubleWidthHeight, mUsbDevice!!)
            }
            when (content.alignIndex) {
                0 -> {
                    // 居左
                    mUsbController?.sendByte(cmdAlignLeft, mUsbDevice!!)
                }
                1 -> {
                    // 居中
                    mUsbController?.sendByte(cmdAlignCenter, mUsbDevice!!)
                }
                2 -> {
                    // 居右
                    mUsbController?.sendByte(cmdAlignRight, mUsbDevice!!)
                }
            }
            mUsbController?.sendMsg(content.content!!, "GBK", mUsbDevice!!)
            if (content.isWalk == 1) {
                mUsbController?.sendByte(cmdWalkPaper, mUsbDevice!!)
            }
            mUsbController?.sendByte(cmdInitPrinter, mUsbDevice!!)
            1
        } catch (e: Exception) {
            Timber.e("printText: ${e.message}")
            -1
        }
    }

    /**
     * 打印条形码
     * */
    private fun printBarCode(barCode: String?): Int {
        if (StringUtil.isBlank(barCode)) {
            return -1
        }
        val msgBarCodeInfo = barCode!!.toByteArray()
        val msgBarCodeSize = msgBarCodeInfo.size
        val msgBarCodeSizeByte = byteArrayOf(msgBarCodeSize.toByte())

        val cmdBarCodeHeader = byteArrayOf(0x1d, 0x6b, 73, msgBarCodeSizeByte[0])

        return try {
            // 初始化打印机
            mUsbController?.sendByte(cmdInitPrinter, mUsbDevice!!)
            // 居中显示
            mUsbController?.sendByte(cmdAlignCenter, mUsbDevice!!)
            mUsbController?.sendByte(cmdBarcodeHeight, mUsbDevice!!)
            mUsbController?.sendByte(cmdBarcodeHRIHeight, mUsbDevice!!)
            mUsbController?.sendByte(cmdBarCodeHeader, mUsbDevice!!)
            mUsbController?.sendByte(msgBarCodeInfo, mUsbDevice!!)
            mUsbController?.sendByte(cmdWalkPaper, mUsbDevice!!)
            mUsbController?.sendByte(cmdInitPrinter, mUsbDevice!!)
            1
        } catch (e: Exception) {
            Timber.e("printBarCode: ${e.message}")
            -1
        }
    }

    /**
     * 打印二维码
     * */
    private fun printQrCode(qrCode: String?): Int {
        if (StringUtil.isBlank(qrCode)) {
            return -1
        }

        val pictureTool = PrintPictureUtil.instance
        val srcImage = BarCodeUtil.createQrCode(qrCode!!, 230, 230)
        if (srcImage == null) {
            Timber.d("图片数据为空")
            return -1
        }

        pictureTool.initCanvas(srcImage.width, srcImage.height)
        pictureTool.drawImage(0.toFloat(), 0.toFloat(), srcImage)
        val sendData: ByteArray? = pictureTool.printDraw()

        if (sendData == null) {
            Timber.d("图片数据为空")
            return -1
        }
        // 居中显示
        mUsbController?.sendByte(cmdAlignCenter, mUsbDevice!!)
        var s = 0
        return try {
            // usb bulk < 16k bytes
            val temp = ByteArray(8)
            temp[s++] = 0x1D
            temp[s++] = 0x76
            temp[s++] = 0x30
            temp[s++] = 0x00
            temp[s++] = (pictureTool.mWidth / 8 % 256).toByte()
            temp[s++] = (pictureTool.mWidth / 8 / 256).toByte()
            temp[s++] = (pictureTool.mLength % 256).toByte()
            temp[s++] = (pictureTool.mLength / 256).toByte()
            if (mUsbController!!.sendByte(temp, mUsbDevice!!) < 0) {
                Timber.e("Thread:btn_print_image", "esc pos header bulkTranser failed ...")
                -1
            }
            if (mUsbController!!.sendByte(sendData, mUsbDevice!!) < 0) {
                Timber.e("Thread:btn_print_image", "sendData bulkTranser failed ...")
                return -1
            }

            // 行空白走纸
            mUsbController!!.sendByte(cmdWalkPaper, mUsbDevice!!)
            1
        } catch (e: Exception) {
            Timber.e("printImage: ${e.message}")
            -1
        }
    }

    private fun showTip(tip: String?) {
        PrinterDialog.openService(BaseApplication.mApplication, tip ?: "打印缺纸，请放入纸后重试")
    }

    /**
     * 查看是否添加了纸
     * */
    private fun checkPaper() {
        if (mCheckPaperThread != null && !mCheckPaperThread!!.isDisposed) {
            mCheckPaperThread!!.dispose()
            Timber.e("CheckPaperThread has init ")
            return
        }

        mCheckPaperThread = object : DisposableSubscriber<Long>() {
            override fun onNext(aLong: Long?) {
                nextPrint()
            }

            override fun onError(t: Throwable) {
                Timber.e("失败: ${t.message}")
            }

            override fun onComplete() {

            }
        }

        // 间隔5秒执行一次
        Flowable.interval(30, 5, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .subscribe(mCheckPaperThread)
    }

    private fun restart() {
        close()
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (BaseApplication.mApplication != null) {
                        PrintHelper.instance.initPrinter(BaseApplication.mApplication!!)
                    }
                }
    }

    /**
     * 关闭
     * */
    fun close() {
        mUsbController.let {
            mUsbController?.close()
        }
        mEventBus?.let {
            try {
                mEventBus?.unregister(this)
                mEventBus = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (mCheckPaperThread != null && !mCheckPaperThread!!.isDisposed) {
            mCheckPaperThread!!.dispose()
        }
        PrinterDialog.closeService(BaseApplication.mApplication)
    }

    object PrintHolder {
        @SuppressLint("StaticFieldLeak")
        val instance: PrintHelper = PrintHelper()
    }

    companion object {
        // 0x38
        val IS_NO_PAPER: Int = 48
        // 打印数据队列
        private val mPrintQueue = ConcurrentLinkedQueue<PrintBean>()
        // 是否初始化打印机
        private var isInit = false
        // 正在打印中
        private var isPrinting = false

        // 蜂鸣指令
        val cmdBeep = byteArrayOf(0x1B, 0x42, 0x04, 0x01)
        // 切刀指令
        val cmdCut = byteArrayOf(0x1D, 0x56, 0x42, 90)
        // 走纸
        val cmdWalkPaper = byteArrayOf(0x0a, 0x0a, 0x0a, 0x0a, 0x0a)
        // 初始化打印机
        val cmdInitPrinter = byteArrayOf(0x1B, 0x40)
        // 设置倍宽倍高
        val cmdDoubleWidthHeight = byteArrayOf(0x1B, 0x21, 0x30)
        // 重置宽高
        val cmdResetWidthHeight = byteArrayOf(0x1B, 0x21, 0x00)
        // 设置左对齐方式
        val cmdAlignLeft = byteArrayOf(0x1B, 0x61, 0x00)
        // 设置居中对齐方式
        val cmdAlignCenter = byteArrayOf(0x1B, 0x61, 0x01)
        // 设置右对齐方式
        val cmdAlignRight = byteArrayOf(0x1B, 0x61, 0x02)
        // 设置条码高度，最后一位是条码高度，这里默认100 bit pixel
        val cmdBarcodeHeight = byteArrayOf(0x1d, 0x68, 0x64)
        // 设置条码HRI高度，HRI字符输出在条码下方
        val cmdBarcodeHRIHeight = byteArrayOf(0x1d, 0x48, 0x02)

        val instance: PrintHelper
            get() = PrintHolder.instance
    }
}