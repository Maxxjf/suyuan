package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类型：CodeBean
 * Author: iceberg
 * Date: 2018/3/22.
 * 溯源码
 */
class CodeBean {

    /**
     * createTime : 测试内容bscw
     * name : 测试内容17oj
     * operaName : 测试内容92xi
     * specification : 测试内容ca82
     * traceabilityCode : 测试内容q8q7
     */

    var createTime: String? = null
        get() = if (StringUtil.isBlank(field)) "--" else field
    var name: String? = null
        get() = if (StringUtil.isBlank(field)) "--" else field
    var operaName: String? = null
        get() = if (StringUtil.isBlank(field)) "--" else field
    var specification: String? = null
        get() = if (StringUtil.isBlank(field)) "--" else field
    var traceabilityCode: String? = null
        get() = if (StringUtil.isBlank(field)) "--" else field
    override fun toString(): String {
        return "CodeBean(createTime=$createTime, name=$name, operaName=$operaName, specification=$specification, traceabilityCode=$traceabilityCode)"
    }

}
