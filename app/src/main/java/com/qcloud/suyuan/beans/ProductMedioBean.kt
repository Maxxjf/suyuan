package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

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
}