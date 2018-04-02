package com.qcloud.suyuan.utils

import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.beans.ProductComponentBean

/**
 * Description: 产品有关工具类
 * Author: gaobaiqiang
 * 2018/3/30 下午5:33.
 */
object ProductUtil {
    /**
     * 产品属性string转list
     * */
    fun productAttrStr2List(attrStr: String?): List<ProductComponentBean> {
        val list: MutableList<ProductComponentBean> = ArrayList()
        if (StringUtil.isBlank(attrStr)) {
            return list
        }
        val attrStr2: String? = StringUtil.replace(attrStr!!, "；", ";")
        if (StringUtil.isBlank(attrStr2)) {
            return list
        }
        val attrList = StringUtil.split2List(attrStr2, ";")
        for (value in attrList) {
            val bean = ProductComponentBean()
            val valueList: List<String> = StringUtil.split2List(value, "#")
            bean.valueList = valueList
            list.add(bean)
        }

        return list
    }

    /**
     * 产品属性提交规范
     * */
    fun productAttrStr2Submit(list: List<ProductComponentBean>): String {
        if (list.isEmpty()) {
            return ""
        }

        val submitStr = StringBuffer()
        for (bean in list) {
            val valueStr = StringUtil.combineList(bean.valueList, "# @ #")
            submitStr.append(valueStr)
            submitStr.append(";@;")

        }
        if (submitStr.length >= ";@;".length) {
            submitStr.delete(submitStr.length - ";@;".length, submitStr.length)
        }

        return submitStr.toString()
    }

    /**
     * 产品属性list转string
     * */
    fun productAttrList2Str(attrStr: String?): List<ProductComponentBean> {
        val list: MutableList<ProductComponentBean> = ArrayList()
        if (StringUtil.isBlank(attrStr)) {
            return list
        }
        val attrList: List<String> = StringUtil.split2List(attrStr, "#")
        for (value in attrList) {
            val bean = ProductComponentBean()
            val valueList: List<String> = StringUtil.split2List(value, ".")
            bean.valueList = valueList
            list.add(bean)
        }

        return list
    }
}