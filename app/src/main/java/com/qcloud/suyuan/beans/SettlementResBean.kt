package com.qcloud.suyuan.beans

/**
 * 类说明：结算返回
 * Author: Kuzan
 * Date: 2018/3/28 20:35.
 */
class SettlementResBean {
    var purchaserId: Long = -1      // 购买者ID
    var traceabilityList: List<TraceabilityBean>? = null    // 溯源清单
}