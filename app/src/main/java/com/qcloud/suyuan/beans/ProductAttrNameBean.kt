package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 产品性性
 * Author: gaobaiqiang
 * 2018/3/26 下午6:29.
 */
class ProductAttrNameBean {
    var id: String? = null
    var name: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var title: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var createTime: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var classifyId: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var isRemove: Int = 0
    var state: Int = 0
    var type: Int = 0
    var isCrux: Int = 0
}