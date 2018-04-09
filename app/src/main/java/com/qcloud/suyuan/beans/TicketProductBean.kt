package com.qcloud.suyuan.beans

/**
 * 类说明：小票商品
 * Author: Kuzan
 * Date: 2018/3/29 17:32.
 */
class TicketProductBean {
    var name: String = ""       // 名称
        get() {
            val strLen = field.length
            if (strLen == 16) {
                return field
            } else if (strLen < 16) {
                val temp = 16 - strLen
                var tem = ""
                for (i in 0 until temp) {
                    tem += "$tem\t"
                }
                return field + tem
            } else {
                return field.substring(0, 16)
            }
        }
    var price: Double = 0.00    // 单价
    var priceStr: String = "0.00"   // 单价
        get() = price.toString()
    var number: Int = 1
    var numberStr: String = "1" // 数量
        get() = number.toString()

    override fun toString(): String {
        return "TicketProductBean(price=$price, number=$number)"
    }

}