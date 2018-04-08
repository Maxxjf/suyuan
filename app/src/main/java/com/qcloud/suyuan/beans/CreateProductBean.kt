package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类说明：创建私有产品
 * Author: Kuzan
 * Date: 2018/4/2 10:22.
 */
class CreateProductBean {
    var list: List<ProductClassifyBean>? = null  // 分类集合
    var goods: ProductGoodsBean? = null      // 产品信息
    var info: ProductInfoBean? = null   // 商品明细
    var price: String = ""            // 零售价
    var priceValue: Double = 0.00
        get() = if (StringUtil.isMoneyStr(price)) price.toDouble() else 0.00
}