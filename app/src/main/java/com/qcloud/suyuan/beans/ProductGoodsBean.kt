package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 产品信息
 * Author: gaobaiqiang
 * 2018/3/26 下午6:15.
 */
class ProductGoodsBean {
    var id: String? = null
    var barCode: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var createTime: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var classifyId: String? = null  // 产品分类id
    var classifyName: String? = null    // 产品分类
        get() = if (StringUtil.isBlank(field)) "" else field
    var isPlatform: Int = 0     // 是否平台 0不是 1是
    var isRemove: Int = 0
    var updateTime: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var shopId: String? = null
    var state: Int = 0
    var millId: String? = null
    var millName: String? = null        // 厂家名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var millAddress: String? = null     // 厂家地址
        get() = if (StringUtil.isBlank(field)) "" else field
}