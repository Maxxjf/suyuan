package com.qcloud.suyuan.model

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.*


/**
 * 类说明：金钱有关的
 * Author: iceberg
 * Date: 2018-3-25.
 */
interface IMoneyModel {
    /**
     * 得到赊账列表
     */
    fun getCreditList(keyword:String,pageNo: Int, pageSize: Int, callback: DataCallback<CreditListBean>)

    /**
     * 得到赊账详细
     */
    fun getCreditInfo(creditId: String, pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<CreditInfoBean>>)

    /**
     * 赊账还款
     */
    fun repayment(id: String, money: Double, callback: DataCallback<EmptyReturnBean>)

    /**
     * 销售列表
     */
    fun getSaleList(dayTime:String,keyword: String, callback: DataCallback<ReturnDataBean<SaleListBean>>)
    /**
     * 销售详情
     */
    fun getSaleInfo(id: String, callback: DataCallback<SaleInfoBean>)
    /**
     * 还款记录
     */
    fun repaymentHistory(purchaserId: String, callback: DataCallback<ReturnDataBean<RepaymentListBean>>)
    /**
     * 个人剩余还款总金额
     */
    fun creditAllMoney(purchaserId: String, callback: DataCallback<CreditAllMoneyBean>)

//    全部还款
    fun repaymentAll(money: String, purchaserId: String, callback: DataCallback<EmptyReturnBean>)
}