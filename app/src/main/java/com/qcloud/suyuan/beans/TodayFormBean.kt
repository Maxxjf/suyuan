package com.qcloud.suyuan.beans

/**
 * Description: 今日报表
 * Author: gaobaiqiang
 * 2018/3/19 下午7:21.
 */
class TodayFormBean {
    var earning: Double = 0.0    // 收入
    var earningStr: String = "0.0"
        get() = earning.toString()
    var onCredit: Double = 0.0   // 赊账
    var onCreditStr: String = "0.0"
        get() = onCredit.toString()
    var order: Int = 0      // 有效订单
    var returnMoney: Double = 0.0    // 退款
    var returnMoneyStr: String = "0.0"
        get() = returnMoney.toString()

    override fun toString(): String {
        return "TodayFormBean(earning=$earning, onCredit=$onCredit, order=$order, returnMoney=$returnMoney)"
    }
}