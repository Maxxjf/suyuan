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

    var alreadyRepayment: Int = 0
    var serialNumber: String? = null
    var shouldRepayment: Int = 0
    var time: String? = null
    override fun toString(): String {
        return "CreditInfoBean(alreadyRepayment=$alreadyRepayment, serialNumber=$serialNumber, shouldRepayment=$shouldRepayment, time=$time)"
    }

}
