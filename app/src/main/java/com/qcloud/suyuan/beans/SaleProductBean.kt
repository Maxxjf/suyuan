package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类说明：卖货
 * Author: Kuzan
 * Date: 2018/3/28 17:11.
 */
class SaleProductBean {
    var recordId: String? = null        // 入库批次ID
    var batchNum: String? = null        // 批次码
        get() = if (StringUtil.isBlank(field)) "" else field
    var name: String? = null            // 商品名
        get() = if (StringUtil.isBlank(field)) "" else field
    var number: Int = 1                 // 数量：默认为1
    var numberStr: String = "1"
        get() = number.toString()
    var price: Double = 0.00            // 售价
    var priceStr: String = "0.00"
        get() = price.toString()
    var specification: String? = null   // 规格
    var stock: Int = 0                  // 批次库存
    var stockStr: String = "0"
        get() = stock.toString()
    var active: Boolean = true          // 是否过期：未过期(true)，已过期(false)
    override fun toString(): String {
        return "SaleProductBean(recordId=$recordId, number=$number, price=$price, specification=$specification, stock=$stock, active=$active)"
    }

}