package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类说明：结算返回
 * Author: Kuzan
 * Date: 2018/3/28 20:35.
 */
class SettlementResBean {
    var purchaserId: Long = -1      // 购买者ID
    var orderNo: String? = null     // 订单号
        get() = if (StringUtil.isBlank(field)) "" else field
    var orderTime: String? = null   // 下单时间
        get() = if (StringUtil.isBlank(field)) "" else field
    var traceabilityList: List<TraceabilityBean>? = null    // 溯源清单
    override fun toString(): String {
        return "SettlementResBean(purchaserId=$purchaserId, traceabilityList=$traceabilityList)"
    }


}