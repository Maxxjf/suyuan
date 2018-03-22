package com.qcloud.suyuan.beans

/**
 * Description: 今日报表
 * Author: gaobaiqiang
 * 2018/3/19 下午7:21.
 */
class TodayFormBean {
    var earning: Double = 0.00    // 收入
    var earningStr: String = "0.00"
        get() = earning.toString()
    var onCredit: Double = 0.00   // 赊账
    var onCreditStr: String = "0.00"
        get() = onCredit.toString()
    var order: Int = 0      // 有效订单
    var returnMoney: Double = 0.00    // 退款
    var returnMoneyStr: String = "0.00"
        get() = returnMoney.toString()

    override fun toString(): String {
        return "TodayFormBean(earning=$earning, onCredit=$onCredit, order=$order, returnMoney=$returnMoney)"
    }
}