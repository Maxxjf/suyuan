package com.qcloud.suyuan.beans

/**
 * 类型：CreditInfoBean
 * Author: iceberg
 * Date: 2018/3/25.
 * TODO:
 */
class CreditInfoBean {


    /**
     * alreadyRepayment : 已经还金额
     * serialNumber : 流水号
     * shouldRepayment : 应还金额
     * time : 时间
     */

    var alreadyRepayment: Double = 0.0
    var serialNumber: String? = null
    var shouldRepayment:  Double = 0.0
    var time: String? = null
    var id: String? = null
    override fun toString(): String =
            "CreditInfoBean(alreadyRepayment=$alreadyRepayment, serialNumber=$serialNumber, shouldRepayment=$shouldRepayment, time=$time, id=$id)"


}
