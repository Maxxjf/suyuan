package com.qcloud.suyuan.utils

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.*
import android.os.Parcelable
import android.support.annotation.NonNull
import com.qcloud.qclib.beans.RxBusEvent
import com.qcloud.qclib.rxbus.BusProvider
import com.qcloud.suyuan.R
import timber.log.Timber
import java.io.UnsupportedEncodingException

/**
 * 类说明：打印机连接工具类
 * Author: Kuzan
 * Date: 2018/3/26 20:56.
 */
class UsbController (@NonNull val mContext: Context) {
    @SuppressLint("WrongConstant")
    private val mUsbManager: UsbManager  = mContext.getSystemService("usb") as UsbManager
    private var mUseEndpoint: UsbEndpoint? = null
    private var mUsbInterface: UsbInterface? = null
    private var mUsbConnection: UsbDeviceConnection? = null

    /** 打印机权限获取返回 */
    private val mPermissionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            mContext.unregisterReceiver(this)
            if (intent.action == ACTION_USB_PERMISSION && intent.getBooleanExtra("permission", false)) {
                val dev: UsbDevice? = intent.getParcelableExtra<Parcelable>("device") as UsbDevice
                if (dev != null) {
                    BusProvider.instance.post(RxBusEvent.newBuilder(R.id.id_verify_result).setObj(USB_CONNECTED).build())
                } else {
                    BusProvider.instance.post(RxBusEvent.newBuilder(R.id.id_verify_result).setObj(USB_DISCONNECTED).build())
                }
            }
        }
    }

    /**
     * 根据vid和pid获取打印机设备
     *
     * @param vid
     * @param pid
     * */
    @Synchronized
    fun getDev(vid: Int, pid: Int): UsbDevice? {
        var dev: UsbDevice? = null
        mUseEndpoint = null
        mUsbInterface = null
        mUsbConnection = null
        val devList = mUsbManager.deviceList
        val devIterator = devList.values.iterator()

        while (devIterator.hasNext()) {
            val d = devIterator.next() as UsbDevice
            Timber.d(d.deviceName + ":" + String.format("%04X:%04X", Integer.valueOf(d.vendorId), Integer.valueOf(d.productId)))
            if (d.vendorId == vid && d.productId == pid) {
                dev = d
                break
            }
        }

        return dev
    }

    /**
     * 是否有打印机权限
     *
     * @param dev 设备
     * */
    @Synchronized
    fun isHasPermission(dev: UsbDevice): Boolean {
        return mUsbManager.hasPermission(dev)
    }

    /**
     * 获取打印机权限
     *
     * @param dev 设备
     * */
    @Synchronized
    fun getPermission(dev: UsbDevice?) {
        if (dev != null) {
            if (!isHasPermission(dev)) {
                val msg1 = PendingIntent.getBroadcast(mContext, 0, Intent(ACTION_USB_PERMISSION), 0)
                mContext.registerReceiver(mPermissionReceiver, IntentFilter(ACTION_USB_PERMISSION))
                mUsbManager.requestPermission(dev, msg1)
            } else {
                BusProvider.instance.post(RxBusEvent.newBuilder(R.id.id_verify_result).setObj(USB_CONNECTED).build())
            }
        }
    }

    /**
     * 发送数据到打印机
     *
     * @param msg 数据
     * @param charset 字体格式
     * @param dev 设备
     * */
    @Synchronized
    fun sendMsg(msg: String, charset: String, dev: UsbDevice) {
        if (msg.isNotEmpty()) {
            val send: ByteArray = try {
                msg.toByteArray(charset(charset))
            } catch (var6: UnsupportedEncodingException) {
                msg.toByteArray()
            }

            sendByte(send, dev)
            sendByte(byteArrayOf(13.toByte(), 10.toByte(), 0.toByte()), dev)
        }
    }

    /**
     * 发送字节数组到打印机
     *
     * @param bits 发送的字节数组
     * @param dev 设备
     * */
    @Synchronized
    fun sendByte(bits: ByteArray?, dev: UsbDevice): Int {
        val timeout = 5000
        if (bits != null) {
            if (mUseEndpoint != null && mUsbInterface != null && mUsbConnection != null) {
                return mUsbConnection!!.bulkTransfer(mUseEndpoint, bits, bits.size, timeout)
            } else {
                if (mUsbConnection == null) {
                    mUsbConnection = mUsbManager.openDevice(dev)
                }

                if (dev.interfaceCount == 0) {
                    return -1
                }

                mUsbInterface = dev.getInterface(0)
                if (mUsbInterface!!.endpointCount == 0) {
                    return -1
                }

                for (i in 0 until mUsbInterface!!.endpointCount) {
                    if (mUsbInterface!!.getEndpoint(i).type == 2 && mUsbInterface!!.getEndpoint(i).direction != 128) {
                        mUseEndpoint = mUsbInterface!!.getEndpoint(i)
                    }
                }

                if (mUsbConnection!!.claimInterface(mUsbInterface, true)) {
                    return mUsbConnection!!.bulkTransfer(mUseEndpoint, bits, bits.size, timeout)
                }
            }
        }
        return -1
    }

    /**
     * 获取打印机信息
     *
     * @param dev
     * */
    fun revByte(dev: UsbDevice): Byte {
        val timeout = 5000
        val bits = ByteArray(2)
        if (mUsbConnection == null) {
            mUsbConnection = mUsbManager.openDevice(dev)
        }

        mUsbConnection!!.controlTransfer(161, 1, 0, 0, bits, bits.size, timeout)
        return bits[0]
    }

    /**
     * 跑纸切刀
     *
     * @param dev 设备
     * @param n 跑多长
     * */
    @Synchronized
    fun cutPaper(dev: UsbDevice, n: Int) {
        val bits = byteArrayOf(29.toByte(), 86.toByte(), 66.toByte(), n.toByte())
        sendByte(bits, dev)
    }

    /**
     * 纸张模式
     * */
    @Synchronized
    fun catPaperByMode(dev: UsbDevice, mode: Int) {
        val bits = ByteArray(3)
        when (mode) {
            0 -> {
                bits[0] = 29
                bits[1] = 86
                bits[2] = 48
            }
            1 -> {
                bits[0] = 29
                bits[1] = 86
                bits[2] = 49
            }
        }

        sendByte(bits, dev)
    }

    @Synchronized
    fun openCashBox(dev: UsbDevice) {
        val bits = byteArrayOf(27.toByte(), 112.toByte(), 0.toByte(), 64.toByte(), 80.toByte())
        sendByte(bits, dev)
    }

    @Synchronized
    fun defaultBuzzer(dev: UsbDevice) {
        val bits = byteArrayOf(27.toByte(), 66.toByte(), 4.toByte(), 1.toByte())
        sendByte(bits, dev)
    }

    @Synchronized
    fun buzzer(dev: UsbDevice, n: Int, time: Int) {
        val bits = byteArrayOf(27.toByte(), 66.toByte(), n.toByte(), time.toByte())
        sendByte(bits, dev)
    }

    @Synchronized
    fun setBuzzerMode(dev: UsbDevice, n: Int, time: Int, mode: Int) {
        val bits = byteArrayOf(27.toByte(), 67.toByte(), n.toByte(), time.toByte(), mode.toByte())
        sendByte(bits, dev)
    }

    @Synchronized
    fun close() {
        mUseEndpoint = null
        mUsbInterface = null
        mUsbConnection.let {
            mUsbConnection?.close()
            mUsbConnection = null
        }
    }

    companion object {
        val ACTION_USB_PERMISSION = "com.hs.usbconn.USB"
        val USB_CONNECTED = 1
        val USB_DISCONNECTED = 0
    }
}