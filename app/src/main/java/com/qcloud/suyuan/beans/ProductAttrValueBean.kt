package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 商品属性(当属性为下拉框时的集合)
 * Author: gaobaiqiang
 * 2018/3/26 下午6:25.
 */
class ProductAttrValueBean {
    var id: String? = null      // id
    var name: String? = null    // 名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var nameId: String? = null  // 属性名id
    var createTime: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var classifyId: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var isRemove: Int = 0
    var state: Int = 0
    var sort: Int = 1
}