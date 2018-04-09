package com.qcloud.suyuan.beans

import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.utils.DateUtil
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
    var productionDate: String? = null  // 生产日期
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var stopDate: String? = null    // 有效期截止时间
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var validDate: String? = null
        get() = productionDate + "至" + stopDate
    var recordId: String = "0"      // 入库批次id

    override fun toString(): String {
        return "ValidWarnBean(pastDue=$pastDue, surplusNum=$surplusNum, id=$id, imageUrl=$imageUrl)"
    }
}