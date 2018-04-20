package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类型：CreditInfoBean
 * Author: iceberg
 * Date: 2018/3/25.
 * 赊账信息
 */
class CreditInfoBean {


    /**
     * alreadyRepayment : 已经还金额
     * serialNumber : 流水号
     * shouldRepayment : 应还金额
     * time : 时间
     */

    var alreadyRepayment: Double = 0.00
    var serialNumber: String? = null
        get() = if (StringUtil.isBlank(field)) "--" else field
    var shouldRepayment:  Double = 0.00
    var time: String? = null
        get() = if (StringUtil.isBlank(field)) "--" else field
    var id: String? = null
        get() = if (StringUtil.isBlank(field)) "--" else field
    override fun toString(): String =
            "CreditInfoBean(alreadyRepayment=$alreadyRepayment, serialNumber=$serialNumber, shouldRepayment=$shouldRepayment, time=$time, id=$id)"


}
