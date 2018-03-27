package com.qcloud.suyuan.beans

/**
 * 类型：销售详情
 * Author: iceberg
 * Date: 2018/3/27  9:50
 */
class SaleInfoBean {


    /**
     * list : 商品列表
     * refundPrice : 退款金额
     */
    var refundPrice: Double = 0.0
    var list: List<ListBean>? = null

    class ListBean {
        /**
         * goodsName : 商品名称
         * goodsNum : 销售数量
         * id : 明细ID
         * price : 销售单价
         * specification : 商品规格
         */
        var goodsName: String? = null
        var goodsNum: Int = 0
        var id: String? = null
        var price: Double = 0.00
        var specification: String? = null
        override fun toString(): String {
            return "ListBean(goodsName=$goodsName, goodsNum=$goodsNum, id=$id, price=$price, specification=$specification)"
        }

    }

    override fun toString(): String {
        return "SaleInfoBean(refundPrice=$refundPrice, list=$list)"
    }
    fun isNext(pageSize: Int): Boolean {
        if (list == null) {
            return false
        }
        return list!!.size >= pageSize
    }
}