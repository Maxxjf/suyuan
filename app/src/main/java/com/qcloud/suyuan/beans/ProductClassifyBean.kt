package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 分类集合
 * Author: gaobaiqiang
 * 2018/3/24 下午11:21.
 */
class ProductClassifyBean {
    var id: String = ""     // 分类
    var isleaf: Int = 0     // 是否是叶子节点 0代表不是，1代表是；0值不可以作为刷选条件
    var name: String = ""    // 分类名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var level: Int = 0
    var remark: String? = null
    var state: Int = 0
    var parentId: String? = null
    var ordinal: Int = 0
    override fun toString(): String {
        return "ProductClassifyBean(id='$id', isleaf=$isleaf, level=$level, remark=$remark, state=$state, parentId=$parentId, ordinal=$ordinal)"
    }

}