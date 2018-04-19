package com.qcloud.suyuan.ui.record.presenter

/**
 * Description: 赊账记录
 * Author: gaobaiqiang
 * 2018/3/15 上午12:54.
 */
interface ICreditRecordPresenter {
    fun getCreditList(keyword:String,pageNo: Int, pageSize: Int)
    fun getCreditInfo(id: String, pageNo: Int, pageSize: Int)
    fun repayment(id: String, pageNo: Double)
    fun repaymentHistory(purchaserId: String)
    fun creditAllmoney(purchaserId: String)
    fun repaymentAll(money: String, purchaserId: String)
}