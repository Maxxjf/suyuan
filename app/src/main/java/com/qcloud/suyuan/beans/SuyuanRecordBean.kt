package com.qcloud.suyuan.beans

import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 溯源记录
 * Author: gaobaiqiang
 * 2018/4/3 下午5:45.
 */
class SuyuanRecordBean {
    var goodsName: String? = null       // 商品名称
        get() = if (StringUtil.isBlank(field)) "--" else field
    var purchaser: String? = null       // 购买者姓名
        get() = if (StringUtil.isBlank(field)) "--" else field
    var purchaserTime: String? = null   // 购买时间
        get() {
            return if (StringUtil.isBlank(field)) {
                "--"
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var specification: String? = null   // 商品规格
        get() = if (StringUtil.isBlank(field)) "--" else field
    var storeAddress: String? = null    // 门店地址
        get() = if (StringUtil.isBlank(field)) "--" else field
    var traceabilityCode: String? = null// 溯源码
        get() = if (StringUtil.isBlank(field)) "--" else field
}