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
    var phone: String? = null
    var principal: String? = null   // 负责人
    var remark: String? = null
    override fun toString(): String {
        return "SupplierBean(id=$id, address=$address, phone=$phone, principal=$principal, remark=$remark)"
    }

}