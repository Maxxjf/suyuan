package com.qcloud.suyuan.utils

import android.text.method.DigitsKeyListener

/**
 * 类说明：金额输入
 * Author: Kuzan
 * Date: 2018/4/10 19:47.
 */
open class PriceKeyListener: DigitsKeyListener() {

    override fun getAcceptedChars(): CharArray {
        return charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.')
    }
}