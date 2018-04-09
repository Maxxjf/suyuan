package com.qcloud.suyuan.beans

/**
 * Description: 产品详情
 * Author: gaobaiqiang
 * 2018/3/24 下午11:39.
 */
class ProductDetailsBean {
    var goods: ProductGoodsBean? = null      // 商品信息
    var info: ProductInfoBean? = null   // 商品明细
    var mill: ProductMillBean? = null   // 厂家信息
    var totalAmount: Int = 0            // 总库存
    var price: Double = 0.00            // 零售价
    var priceStr: String = "0.00"
        get() = price.toString()
    var warehouseList: List<InStorageRecordBean>? = null    // 商品入库记录
    var goodsMedio: List<ProductMedioBean>? = null
    override fun toString(): String {
        return "ProductDetailsBean(goods=$goods, info=$info, mill=$mill, totalAmount=$totalAmount, price=$price, warehouseList=$warehouseList, goodsMedio=$goodsMedio)"
    }

}