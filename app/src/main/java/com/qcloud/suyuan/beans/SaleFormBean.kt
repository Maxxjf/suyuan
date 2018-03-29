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
    var cash: Double = 0.00
    var oncredit: Double = 0.00
    var orderTotal: Int = 0
    var returnMoney: Double = 0.00
    var totalEarning: Double = 0.00
    var wechat: Double = 0.00
    override fun toString(): String {
        return "SaleFormBean(alipay=$alipay, cash=$cash, oncredit=$oncredit, orderTotal=$orderTotal, returnMoney=$returnMoney, totalEarning=$totalEarning, wechat=$wechat)"
    }

}
