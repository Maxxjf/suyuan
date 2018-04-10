package com.qcloud.suyuan.beans

import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.qclib.utils.StringUtil

/**
 * 类说明：入库返回
 * Author: Kuzan
 * Date: 2018/3/27 21:41.
 */
class InStorageBean {
    var batchNum: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var date: String? = null
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
}