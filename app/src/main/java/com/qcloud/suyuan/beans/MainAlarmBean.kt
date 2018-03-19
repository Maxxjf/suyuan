package com.qcloud.suyuan.beans

/**
 * Description: 首页警告
 * Author: gaobaiqiang
 * 2018/3/19 下午7:19.
 */
class MainAlarmBean {
    var indate: Int = 0     // 有效期警告
    var indateStr: String = "0"
        get() = indate.toString()
    var stock: Int = 0      // 库存警告
    var stockStr: String = "0"
        get() = stock.toString()

    override fun toString(): String {
        return "MainAlarmBean(indate=$indate, stock=$stock)"
    }
}