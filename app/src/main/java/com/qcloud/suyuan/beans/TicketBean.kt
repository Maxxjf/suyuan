package com.qcloud.suyuan.beans

import com.qcloud.suyuan.enums.PayMethod

/**
 * 类说明：小票打印内容
 * Author: Kuzan
 * Date: 2018/3/29 16:44.
 */
class TicketBean {
    var storeName: String = ""      // 门店名称
    var orderNo: String = ""        // 订单编号
    var orderTime: String = ""      // 下单时间
    var list: List<TicketProductBean> = ArrayList()     // 购买商品
    var totalNumber: Int = 1        // 商品总数
    var totalAccount: Double = 0.00 // 合计
    var totalAccountStr : String = "0.00元"
        get() = String.format("%1$.2f元", totalAccount)
    var payMethod: Int = 1          // 支付方式
    var payMethodName: String = "现金"
        get() = PayMethod.getName(payMethod)
    var discount: Double = 0.00     // 优惠
    var discountStr: String = "0.00元"
        get() = String.format("%1$.2f元", discount)
    var realPay: Double = 0.00      // 实收
    var realPayStr: String = "0.00元"
        get() = String.format("%1$.2f元", realPay)
    var giveMoney: Double = 0.00    // 找零
    var giveMoneyStr: String = "0.00元"
        get() = String.format("%1$.2f元", giveMoney)

    override fun toString(): String {
        return "TicketBean(storeName='$storeName', orderNo='$orderNo', orderTime='$orderTime', list=$list, totalNumber=$totalNumber, totalAccount=$totalAccount, payMethod=$payMethod, discount=$discount, realPay=$realPay, giveMoney=$giveMoney)"
    }

}