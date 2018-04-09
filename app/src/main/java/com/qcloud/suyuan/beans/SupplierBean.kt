package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil
import java.io.Serializable

/**
 * 类说明：供应商
 * Author: Kuzan
 * Date: 2018/3/28 15:44.
 */
open class SupplierBean: Serializable {
    var id: String? = null
    var name: String = ""
        get() = if (StringUtil.isBlank(field)) "" else field
    var address: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var phone: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var principal: String? = null   // 负责人
        get() = if (StringUtil.isBlank(field)) "" else field
    var remark: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    override fun toString(): String {
        return "SupplierBean(id=$id, address=$address, phone=$phone, principal=$principal, remark=$remark)"
    }

}