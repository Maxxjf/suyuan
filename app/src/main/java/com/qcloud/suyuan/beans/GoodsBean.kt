package com.qcloud.suyuan.beans

/**
 * 类型：GoodsBean
 * Author: iceberg
 * Date: 2018/3/21.
 * 货物
 */
class GoodsBean {
    var id:String=""
    var name:String=""
    var rule:String=""
    var date:String=""
    var number:String=""
    var price:String=""
    override fun toString(): String {
        return "GoodsBean(id='$id', name='$name', rule='$rule', date='$date', number='$number', price='$price')"
    }

}