package com.qcloud.suyuan.beans

/**
 * 类说明：打印小票内容
 * Author: Kuzan
 * Date: 2018/3/27 12:02.
 */
class PrintContentBean {
    var content: String? = null
    var isDouble: Int = 0       // 是否倍宽倍高 0不是1是
    var alignIndex: Int = 0     // 显示位置 0居左 1居中 2居右
    var isWalk: Int = 0         // 是否走纸 0是 1不是
    override fun toString(): String {
        return "PrintContentBean(content=$content, isDouble=$isDouble, alignIndex=$alignIndex, isWalk=$isWalk)"
    }

}