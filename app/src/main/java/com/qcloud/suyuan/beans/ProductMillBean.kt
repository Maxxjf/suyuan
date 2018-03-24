package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 产品厂家信息
 * Author: gaobaiqiang
 * 2018/3/24 下午11:47.
 */
class ProductMillBean {
    var address: String? = null // 厂家地址
        get() = if (StringUtil.isBlank(field)) "" else field
    var name: String? = null    // 厂家名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var tel: String? = null     // 电话
        get() = if (StringUtil.isBlank(field)) "" else field
    var url: String? = null     // 网址
        get() = if (StringUtil.isBlank(field)) "" else field
}