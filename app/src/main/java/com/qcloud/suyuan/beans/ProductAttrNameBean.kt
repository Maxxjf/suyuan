package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 产品属性名，列表的标题显示内容
 * Author: gaobaiqiang
 * 2018/3/26 下午6:29.
 */
class ProductAttrNameBean {
    var id: String? = null      // 属性名称id
    var name: String? = null    // 属性名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var title: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var createTime: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var classifyId: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var isRemove: Int = 0
    var state: Int = 0
    var type: Int = 0       // 属性类型1下拉 2文本 3表格
    var isCrux: Int = 0     // 是否必填(0否1是)
    override fun toString(): String {
        return "ProductAttrNameBean(id=$id, isRemove=$isRemove, state=$state, type=$type, isCrux=$isCrux)"
    }

}