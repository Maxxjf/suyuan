package com.qcloud.suyuan.beans

/**
 * Description: 商品属性集
 * Author: gaobaiqiang
 * 2018/3/26 下午6:24.
 */
class ProductMedioBean {
    var id: String? = null
//    var value: String? = null
//        get() = if (StringUtil.isBlank(field)) "" else field
//    var attrType: String? = null
    var attributeName: ProductAttrNameBean? = null
    var attributeValues: List<ProductAttrValueBean>? = null
    override fun toString(): String {
        return "ProductMedioBean(id=$id, attributeName=$attributeName, attributeValues=$attributeValues)"
    }

}