package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 有效期告警
 * Author: gaobaiqiang
 * 2018/3/21 下午4:57.
 */
class ValidWarnBean {
    var batchNum: String? = null    // 入库批次
        get() = if (StringUtil.isBlank(field)) "" else field
    var barCode: String? = null     // 条码
        get() = if (StringUtil.isBlank(field)) "" else field
    var pastDue: Int = 0     // 剩余多少天
    var pastDueStr: String = "0"
        get() = pastDue.toString()
    var surplusNum: Int = 0     // 剩余数量
    var surplusNumStr: String = "0"
        get() = surplusNum.toString()
    var id: String? = null     // 商品id
    var imageUrl: String? = null    // 商品图片
    var millName: String? = null    // 厂家名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var name: String? = null    // 商品名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var specification: String? = null   // 规格
        get() = if (StringUtil.isBlank(field)) "" else field
    var unit: String? = null    // 单位
        get() = if (StringUtil.isBlank(field)) "" else field
    var stopDate: String? = null    // 入库批次
        get() = if (StringUtil.isBlank(field)) "" else field

    override fun toString(): String {
        return "ValidWarnBean(pastDue=$pastDue, surplusNum=$surplusNum, id=$id, imageUrl=$imageUrl)"
    }
}