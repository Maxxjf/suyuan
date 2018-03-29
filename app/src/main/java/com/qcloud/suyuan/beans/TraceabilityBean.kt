package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类说明：溯源清单
 * Author: Kuzan
 * Date: 2018/3/28 20:32.
 */
class TraceabilityBean {
    var code: String? = null        // 溯源码
        get() = if (StringUtil.isBlank(field)) "" else field
    var codeUrl: String? = null     // 二维码链接地址
    var millName: String? = null    // 厂家名
        get() = if (StringUtil.isBlank(field)) "" else field
    var name: String? = null        // 商品名
        get() = if (StringUtil.isBlank(field)) "" else field
    var storeName: String? = null   // 门店名
        get() = if (StringUtil.isBlank(field)) "" else field
    var time: String? = null        // 标贴时间
}