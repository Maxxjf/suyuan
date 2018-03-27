package com.qcloud.suyuan.beans

/**
 * 类型：
 * Author: iceberg
 * Date: 2018/3/27  10:21
 */
class SaleSearchBean {

    /**
     * cart : {"active":true,"batchNum":"测试内容fzoj","name":"测试内容3gi7","number":88237,"price":25615,"recordId":56574,"specification":"测试内容vk1e","stock":80238}
     */

    var cart: CartBean? = null

    class CartBean {
        /**
         * active : true
         * batchNum : 测试内容fzoj
         * name : 测试内容3gi7
         * number : 88237
         * price : 25615
         * recordId : 56574
         * specification : 测试内容vk1e
         * stock : 80238
         */
        var isActive: Boolean = false
        var batchNum: String? = null
        var name: String? = null
        var number: Int = 0
        var price: Int = 0
        var recordId: String? = null
        var specification: String? = null
        var stock: Int = 0
        override fun toString(): String {
            return "CartBean(isActive=$isActive, batchNum=$batchNum, name=$name, number=$number, price=$price, recordId=$recordId, specification=$specification, stock=$stock)"
        }
    }

    override fun toString(): String {
        return "SaleSearchBean(cart=$cart)"
    }
}
