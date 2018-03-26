package com.qcloud.suyuan.utils

import IDNR_JAR.IDNRSocket
import android.content.Context
import android.support.annotation.NonNull
import android_serialport_api.SerialPort
import android_serialport_api.sample.Util
import com.qcloud.qclib.beans.RxBusEvent
import com.qcloud.qclib.rxbus.BusProvider
import com.qcloud.qclib.rxtask.RxScheduler
import com.qcloud.qclib.rxtask.task.IOTask
import com.qcloud.qclib.rxtask.task.NewTask
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.IDVerifyResultBean
import com.qcloud.suyuan.constant.AppConstants
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import timber.log.Timber
import java.io.*
import java.util.concurrent.TimeUnit
import kotlin.experimental.xor

/**
 * 类说明：身份识别工具类
 * Author: Kuzan
 * Date: 2018/3/26 11:24.
 */
class NFCHelper private constructor() {
    private var mSerialPort: SerialPort? = null

    private var mRfidOutputStream: OutputStream? = null
    private var mRfidInputStream: InputStream? = null

    // 查找线程
    private var mFindThread: DisposableSubscriber<Long>? = null

    private var isRunning = true

    private var mBuffer: ByteArray = ByteArray(1)

    // 是否读卡
    var isReadCard = false

    /**
     * 初始化
     * */
    fun initSerialPort(@NonNull context: Context) {
        try {
            val file = File(AppConstants.CIDSTRING)

            // 检查参数
            if (!file.exists()) {
                Timber.e("初始化身份证识别器失败")
                return
            }
            mSerialPort = SerialPort(file, AppConstants.BAUDRATT, 0)

            mRfidOutputStream = mSerialPort!!.outputStream
            mRfidInputStream = mSerialPort!!.inputStream

            startReadThread()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        RxScheduler.doOnNewThread(object : NewTask<Void> {
            override fun doOnNewThread() {
                try {
                    doExec("echo -wdir01 1 > /sys/devices/virtual/misc/mtgpio/pin")
                    doExec("echo -wdout01 1 > /sys/devices/virtual/misc/mtgpio/pin")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                copyAssets(context, "wltlib", AppConstants.SDPATH + "/wltlib")
            }
        })

        startFindThread(context)
    }

    /**
     * 创建一个寻卡线程
     * */
    private fun startFindThread(context: Context) {
        if (mFindThread != null && !mFindThread!!.isDisposed) {
            mFindThread!!.dispose()
            Timber.e("findThread has init ")
        }

        mFindThread = object : DisposableSubscriber<Long>() {
            override fun onNext(aLong: Long?) {
                if (isReadCard) {
                    val file = File(AppConstants.SDPATH + "/wltlib")
                    if (!file.exists()) {
                        copyAssets(context, "wltlib", AppConstants.SDPATH + "/wltlib")
                    } else if (file.listFiles() != null && file.listFiles()!!.size < 4) {
                        copyAssets(context, "wltlib", AppConstants.SDPATH + "/wltlib")
                    } else {
                        if (!mIDNRsock.UartInUsing) {
                            uartSend2Head(2, Util.hexStringToBytes("5000"))
                            mIDNRsock.ReadIDInStep = 0
                            Timber.i("发送寻卡指令")
                        }
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
     * 创建一个接收线程，不断读取数据
     * */
    private fun startReadThread() {
        RxScheduler.doOnIOThread(object : IOTask<Void> {
            override fun doOnIOThread() {
                while (isRunning) {
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

                            // 发送数据给UI
                            val bean = IDVerifyResultBean()
                            bean.funret = funret
                            bean.uartRecv = uartRecv
                            BusProvider.instance.post(RxBusEvent.newBuilder(R.id.id_verify_result).setObj(bean).build())
                        }
                    } catch (e: Exception) {
                        Timber.e("mRfidInputStream = ${e.message}")
                        e.printStackTrace()
                        return
                    }
                }
            }
        })
    }

    private fun startRfidSendingThread(buffer: ByteArray, len: Int) {
        mBuffer = ByteArray(len)
        for (i in 0 until len) {
            mBuffer[i] = buffer[i]
        }
        if (mSerialPort != null) {
            if (mRfidOutputStream != null) {
                try {
                    mRfidOutputStream!!.write(mBuffer)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun uartSend2Head(j: Int, buff: ByteArray?) {
        if (buff == null) {
            return
        }
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

    /**
     * 发送命令
     * */
    fun doExec(cmd: String): String {
        Timber.e("exec command: $cmd")

        var dos: DataOutputStream? = null

        try {
            val process: Process = Runtime.getRuntime().exec("su")
            dos = DataOutputStream(process.outputStream)
            dos.writeBytes(cmd + "\n")
            dos.writeBytes("exit\n")
            dos.flush()
            process.waitFor()
        } catch (e: Exception) {
            Timber.d("Unexpected error - Here is what I know: ${e.message}")
        } finally {
            dos.let {
                dos?.close()
            }
        }
        return cmd
    }

    /**
     * 获取assess的资源
     * */
    fun copyAssets(@NonNull context: Context, assetDir: String, dir: String) {
        val files: Array<String>
        try {
            files = context.resources.assets.list(assetDir)
        } catch (e1: IOException) {
            return
        }

        val mWorkingPath = File(dir)
        if (!mWorkingPath.exists()) {
            mWorkingPath.mkdirs()
        }

        for (i in files.indices) {
            try {
                val fileName = files[i]
                // we make sure file name not contains '.' to be a folder.
                if (!fileName.contains(".")) {
                    if (assetDir.isEmpty()) {
                        copyAssets(context, fileName, "$dir$fileName/")
                    } else {
                        copyAssets(context, "$assetDir/$fileName", "$dir$fileName/")
                    }
                    continue
                }
                val outFile = File(mWorkingPath, fileName)
                if (outFile.exists()) {
                    outFile.delete()
                }
                val iStream: InputStream = if (assetDir.isNotEmpty()) {
                    context.assets.open("$assetDir/$fileName")
                } else {
                    context.assets.open(fileName)
                }
                val out = FileOutputStream(outFile)

                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int = iStream.read(buf)
                while (len > 0) {
                    out.write(buf, 0, len)
                    len = iStream.read(buf)
                }
                iStream.close()
                out.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 关闭
     * */
    fun close() {
        isRunning = false

        mSerialPort.let {
            mSerialPort?.close()
            mSerialPort = null
        }

        if (mFindThread != null && !mFindThread!!.isDisposed) {
            mFindThread!!.dispose()
        }

        mRfidOutputStream = null
        mRfidInputStream = null

        RxScheduler.doOnNewThread(object : NewTask<Void> {
            override fun doOnNewThread() {
                doExec("echo -wdir01 0 > /sys/devices/virtual/misc/mtgpio/pin")
                doExec("echo -wdout01 0 > /sys/devices/virtual/misc/mtgpio/pin")
            }
        })
    }

    /**
     * 静态内部类
     * 一个ClassLoader下同一个类只会加载一次，保证了并发时不会得到不同的对象
     * */
    object SerialHolder {
        var instance: NFCHelper = NFCHelper()
    }

    companion object {
        val mIDNRsock = IDNRSocket()

        val STEP5001OK = 0x5001
        val STEP5012OK = 0x5012
        val STEP5022OK = 0x5022
        val STEP6013OK = 0x6013
        val STEP5001ERR = 0x7001
        val STEP5012ERR = 0x7002
        val STEP5022ERR = 0x7003
        val STEP6002ERR = 0x7004
        val STEP6013ERR = 0x7005
        val REPEAT5001 = 0x7117
        val PLEASECHECKNETWORKERR = 0x7118

        /**
         * 实现懒加载
         * 在调用getInstance()方法时才会去初始化mInstance
         */
        val instance: NFCHelper
            get() = SerialHolder.instance
    }
}