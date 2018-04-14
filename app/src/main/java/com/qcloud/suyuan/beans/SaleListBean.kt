package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类型：销售列表
 * Author: iceberg
 * Date: 2018/3/27.9:41
 */
class SaleListBean {


    /**
    amount	销售总价
    createDate	销售时间
    discount	优惠价格
    id	销售单ID
    idCard	采购人身份证
    mobile	采购人电话
    payMethod	支付方式：1、现金，2、 微信，3、支付宝，4、刷卡，5、赊账
    purchaserNmae	采购人
    purpose	购买用途
    realPay	实付金额
    remark	备注
    serialNumber   流水号
     */

    var amount: Double = 0.00
    var createDate: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var discount: Double = 0.00
    var id: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var idCard: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var mobile: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var payMethod: Int = 1
    var purchaserNmae: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var purpose: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var realPay: Double = 0.00
    var remark: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var serialNumber: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var isRefund:Boolean=false
    override fun toString(): String {
        return "SaleListBean(amount=$amount, discount=$discount, payMethod=$payMethod, realPay=$realPay, isRefund=$isRefund)"
    }


}
