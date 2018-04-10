package com.qcloud.suyuan.utils

import android.text.method.DigitsKeyListener

/**
 * 类说明：字母数字输入监听
 * Author: Kuzan
 * Date: 2018/4/10 15:57.
 */
open class ChatNumberKeyListener: DigitsKeyListener() {

    override fun getAcceptedChars(): CharArray {
        return charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
    }
}