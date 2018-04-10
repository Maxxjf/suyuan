package com.qcloud.suyuan.beans

/**
 * 类型：
 * Author: iceberg
 * Date: 2018/3/28  9:32
 */
class SaleFormBean {

    /**
     * alipay : 支付宝
     * cash : 现金
     * oncredit : 赊账
     * orderTotal : 订单数
     * returnMoney : 退货金额
     * totalEarning : 总收入
     * wechat : 微信
     */

    var alipay: Double = 0.00
    var alipayStr = "0.00元"
        get() = String.format("%1$.2f元", alipay)
    var cash: Double = 0.00
    var cashStr = "0.00元"
        get() = String.format("%1$.2f元", cash)
    var oncredit: Double = 0.00
    var oncreditStr = "0.00元"
        get() = String.format("%1$.2f元", oncredit)
    var orderTotal: Int = 0
    var orderTotalStr = "0笔"
        get() = String.format("%d笔", orderTotal)
    var returnMoney: Double = 0.00
    var returnMoneyStr = "0.00元"
        get() = String.format("%1$.2f元", returnMoney)
    var totalEarning: Double = 0.00
    var totalEarningStr = "0.00元"
        get() = String.format("%1$.2f元", totalEarning)
    var wechat: Double = 0.00
    var wechatStr = "0.00元"
        get() = String.format("%1$.2f元", wechat)

    override fun toString(): String {
        return "SaleFormBean(alipay=$alipay, cash=$cash, oncredit=$oncredit, orderTotal=$orderTotal, returnMoney=$returnMoney, totalEarning=$totalEarning, wechat=$wechat)"
    }

}
