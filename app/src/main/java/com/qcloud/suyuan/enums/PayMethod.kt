package com.qcloud.suyuan.enums

/**
 * 类说明：支付方式
 * Author: Kuzan
 * Date: 2018/3/29 16:12.
 */
enum class PayMethod constructor(var key: Int, var value: String) {
    payByCash(1, "现金"),
    payByWechat(2, "微信"),
    payByAlipay(3, "支付宝"),
    payByCard(4, "刷卡"),
    payCredit(5, "赊账");

    companion object {
        fun getName(key: Int): String {
            return when (key) {
                1 -> payByCash.value
                2 -> payByWechat.value
                3 -> payByAlipay.value
                4 -> payByCard.value
                5 -> payCredit.value
                else -> payByCash.value
            }
        }
    }
}