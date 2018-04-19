package com.qcloud.suyuan.beans

/**
 * 类型：
 * Author: iceberg
 * Date: 2018/4/18  16:11
 */
class RepaymentListBean {
    /**
     * repayment : 44266
     * time : 测试内容qjjb
     */

    var repayment: Double = 0.00
    var time: String? = ""
    override fun toString(): String {
        return "RepaymentListBean(repayment=$repayment, time=$time)"
    }

}
