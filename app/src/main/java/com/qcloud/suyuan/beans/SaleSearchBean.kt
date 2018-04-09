package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类型：商品查询
 * Author: iceberg
 * Date: 2018/3/27  10:21
 */
class SaleSearchBean {

    /**
     * cart :商品
     */

    var cart: CartBean? = null

    class CartBean {
        /**
         * active : 是否过期：未过期(true)，已过期(false)
         * batchNum : 批次码
         * name : 商品名
         * number : 数量：默认为1
         * price :售价
         * recordId : 入库批次ID
         * specification : 规格
         * stock : 	批次库存
         */
        var isActive: Boolean = false
        var batchNum: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var name: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var number: Int = 0
        var price: Int = 0
        var recordId: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var specification: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var stock: Int = 0
        override fun toString(): String {
            return "CartBean(isActive=$isActive, batchNum=$batchNum, name=$name, number=$number, price=$price, recordId=$recordId, specification=$specification, stock=$stock)"
        }
    }

    override fun toString(): String {
        return "SaleSearchBean(cart=$cart)"
    }
}
