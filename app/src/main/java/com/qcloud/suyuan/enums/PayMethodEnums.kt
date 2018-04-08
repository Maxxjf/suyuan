package com.qcloud.suyuan.enums

/**
 * 类说明：支付方式
 * Author: iceberg
 * Date: 2018/4/8
 */
enum class PayMethodEnums constructor(var key: Int, var value: String) {
    payByCash(1, "现金支付"),
    payByWechat(2, "微信支付"),
    payByAlipay(3, "支付宝支付"),
    payByCard(4, "刷卡支付"),
    payCredit(5, "赊账支付");

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