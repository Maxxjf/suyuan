package com.qcloud.suyuan.beans

/**
 * 类说明：创建私有产品
 * Author: Kuzan
 * Date: 2018/4/2 10:22.
 */
class CreateProductBean {
    var categoryAll: List<ProductClassifyBean>? = null  // 分类集合
    var goods: ProductGoodsBean? = null      // 产品信息
    var info: ProductInfoBean? = null   // 商品明细
    var price: Double = 0.00            // 零售价
    var priceStr: String = "0.00"
        get() = price.toString()
}