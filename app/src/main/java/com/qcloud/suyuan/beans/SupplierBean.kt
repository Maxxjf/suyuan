package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类说明：供应商
 * Author: Kuzan
 * Date: 2018/3/28 15:44.
 */
class SupplierBean {
    var id: String? = null
    var name: String = ""
        get() = if (StringUtil.isBlank(field)) "" else field
    var address: String? = null
    var phone: String? = null
    var principal: String? = null   // 负责人
    var remark: String? = null
}