package com.qcloud.suyuan.beans

/**
 * 类说明：提交购买产品
 * Author: Kuzan
 * Date: 2018/3/28 21:58.
 */
class SubmitProductBean {
    var recordId: String = "0"
    var goodsNum: Int = 1
    var price: Double = 0.00
    override fun toString(): String {
        return "SubmitProductBean(recordId='$recordId', goodsNum=$goodsNum, price=$price)"
    }

}