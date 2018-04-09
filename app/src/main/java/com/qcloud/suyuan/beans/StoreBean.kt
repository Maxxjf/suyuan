package com.qcloud.suyuan.beans

/**
 * 类说明：门店信息
 * Author: Kuzan
 * Date: 2018/3/29 16:49.
 */
class StoreBean {
    var store: StoreInfoBean? = null        // 门店基本信息
    var storeBusiness: List<StoreBusinessBean>? = null    // 门店证件信息
    override fun toString(): String {
        return "StoreBean(store=$store, storeBusiness=$storeBusiness)"
    }

}