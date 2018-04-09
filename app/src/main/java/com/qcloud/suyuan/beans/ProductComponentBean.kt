package com.qcloud.suyuan.beans

/**
 * Description: 产品成分
 * Author: gaobaiqiang
 * 2018/3/30 下午4:50.
 */
class ProductComponentBean {
    var id: String = "0"    // id
    var valueList: List<String> = ArrayList()
    override fun toString(): String {
        return "ProductComponentBean(id='$id', valueList=$valueList)"
    }

}