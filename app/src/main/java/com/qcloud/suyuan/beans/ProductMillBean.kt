package com.qcloud.suyuan.beans

import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 产品厂家信息
 * Author: gaobaiqiang
 * 2018/3/24 下午11:47.
 */
class ProductMillBean {
    var id: String? = "-1"
    var name: String? = null    // 厂家名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var address: String? = null // 厂家地址
        get() = if (StringUtil.isBlank(field)) "" else field
    var street: String? = null  // 厂家街道
        get() = if (StringUtil.isBlank(field)) "" else field
    var tel: String? = null     // 电话
        get() = if (StringUtil.isBlank(field)) "" else field
    var url: String? = null     // 网址
        get() = if (StringUtil.isBlank(field)) "" else field
    var email: String? = null
    var fax: String? = null
    var postcode: String? = null
    var country: String? = null
    var provinceCode: String? = null
    var provinceName: String? = null
    var type: String? = null
    var linkman: String? = null
    var number: String? = null
    var updateTime: String? = null
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var createTime: String? = null
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var isRemove: Int = 0
    var productionClassify: String? = null
    var remark: String? = null
    override fun toString(): String {
        return "ProductMillBean(id=$id, email=$email, fax=$fax, postcode=$postcode, country=$country, provinceCode=$provinceCode, provinceName=$provinceName, type=$type, linkman=$linkman, number=$number, updateTime=$updateTime, createTime=$createTime, isRemove=$isRemove, productionClassify=$productionClassify, remark=$remark)"
    }


}