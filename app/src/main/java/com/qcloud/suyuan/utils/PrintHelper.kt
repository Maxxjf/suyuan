package com.qcloud.suyuan.utils

import android.content.Context
import android.hardware.usb.UsbDevice
import android.support.annotation.NonNull

/**
 * 类说明：打印机实现类
 * Author: Kuzan
 * Date: 2018/3/26 21:34.
 */
class PrintHelper private constructor() {

    private var mUsbInfo: Array<IntArray>? = null
    private var mUsbController: UsbController? = null
    private var mUsbDevice: UsbDevice? = null

    /**
     * 初始化打印机
     * */
    fun initPrinter(@NonNull context: Context) {
        mUsbController = UsbController(context)

        mUsbInfo = Array(5) { IntArray(2) } // vid[0] pid[1]
        // heshuo
        mUsbInfo!![0][0] = 0x1CBE
        mUsbInfo!![0][1] = 0x0a03
        mUsbInfo!![1][0] = 0x0485
        mUsbInfo!![1][1] = 0x7541
        mUsbInfo!![2][0] = 0x1CB0
        mUsbInfo!![2][1] = 0x0003
        // 2015-09-19
        mUsbInfo!![3][0] = 0x0525
        mUsbInfo!![3][1] = 0xa702
        // 2016-11-24 DUOGU BLUETOOTH USE PID VID
        mUsbInfo!![4][0] = 0x28E9
        mUsbInfo!![4][1] = 0x0289
    }

    /**
     * 关闭
     * */
    fun close() {
        mUsbController.let {
            mUsbController?.close()
        }
    }

    object PrintHolder {
        val instance: PrintHelper = PrintHelper()
    }

    companion object {
        val instance: PrintHelper
            get() = PrintHolder.instance
    }
}