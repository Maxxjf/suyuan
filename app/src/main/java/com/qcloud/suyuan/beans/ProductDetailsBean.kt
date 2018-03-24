package com.qcloud.suyuan.beans

/**
 * Description: 产品详情
 * Author: gaobaiqiang
 * 2018/3/24 下午11:39.
 */
class ProductDetailsBean {
    var goods: ProductBean? = null      // 商品信息
    var info: ProductInfoBean? = null   // 商品明细
    var mill: ProductMillBean? = null   // 厂家信息
    var totalAmount: Int = 0            // 总库存
    var warehouseList: List<InStorageRecordBean>? = null    // 商品入库记录
}