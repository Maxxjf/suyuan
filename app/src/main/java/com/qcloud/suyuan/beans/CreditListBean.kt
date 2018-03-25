package com.qcloud.suyuan.beans

/**
 * 类型：CreditListBean
 * Author: iceberg
 * Date: 2018/3/25.
 *赊账列表
 */
class CreditListBean {

    /**
     * allSumRepayment : 15076
     * list : [{"idCard":"测试内容zz0t","mobile":"测试内容kef1","name":"测试内容st2e","purchaserId":"测试内容178y","sumRepayment":71851}]
     * totalRow : 52731
     */

    var allSumRepayment: Int = 0
    var totalRow: Int = 0
    var list: List<ListBean>? = null

    class ListBean {
        /**
         * idCard : 身份证
         * mobile : 电话
         * name : 姓名
         * purchaserId : 购买者id
         * sumRepayment : 赊账金额
         */

        var idCard: String? = null
        var mobile: String? = null
        var name: String? = null
        var purchaserId: String? = null
        var sumRepayment: Int = 0
        override fun toString(): String {
            return "ListBean(idCard=$idCard, mobile=$mobile, name=$name, purchaserId=$purchaserId, sumRepayment=$sumRepayment)"
        }
    }

    fun isNext(pageSize: Int): Boolean {
        if (list == null) {
            return false
        }
        return list!!.size >= pageSize
    }

    override fun toString(): String {
        return "CreditListBean(allSumRepayment=$allSumRepayment, totalRow=$totalRow, list=$list)"
    }

}
