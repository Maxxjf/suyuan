package com.qcloud.suyuan.beans

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
    var name: String? = null
    var operaName: String? = null
    var specification: String? = null
    var traceabilityCode: String? = null
    override fun toString(): String {
        return "CodeBean(createTime=$createTime, name=$name, operaName=$operaName, specification=$specification, traceabilityCode=$traceabilityCode)"
    }

}
