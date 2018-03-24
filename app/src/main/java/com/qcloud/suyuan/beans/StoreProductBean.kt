package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 门店产品
 * Author: gaobaiqiang
 * 2018/3/24 下午1:47.
 */
class StoreProductBean {
    var amount: Int = 0     // 库存
    var amountStr: String = "0"
        get() = amount.toString()
    var barCode: String? = null     // 条码
        get() = if (StringUtil.isBlank(field)) "" else field
    var cordon: Int = 0     // 警告线
    var cordonStr: String = "0"
        get() = cordon.toString()
    var goodsId: String? = null     // 商品id
    var imageUrl: String? = null    // 商品图片
    var millName: String? = null    // 厂家名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var name: String? = null    // 商品名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var specification: String? = null   // 规格
        get() = if (StringUtil.isBlank(field)) "" else field
    var unit: String? = null    // 单位
        get() = if (StringUtil.isBlank(field)) "" else field
    var lastTime: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var operator: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
}