package com.qcloud.suyuan.utils

import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.beans.CreateProductSubmitBean
import com.qcloud.suyuan.beans.ProductAttrBean
import com.qcloud.suyuan.beans.ProductComponentBean
import com.qcloud.suyuan.realm.RealmHelper

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
        // 将中文分号转英文分号
        val attrStr2: String? = StringUtil.replace(attrStr!!, "；", ";")
        if (StringUtil.isBlank(attrStr2)) {
            return list
        }
        // 解析成list
        val attrList = StringUtil.split2List(attrStr2, ";")
        for (value in attrList) {
            val bean = ProductComponentBean()
            // 同一属性的值解析
            val valueList: List<String> = StringUtil.split2List(value, "#")
            bean.valueList = valueList
            list.add(bean)
        }

        return list
    }

    /**
     * 产品属性提交规范，提交到后台的规范
     * */
    fun productAttrStr2Submit(list: List<ProductComponentBean>): String {
        if (list.isEmpty()) {
            return ""
        }

        val submitStr = StringBuffer()
        for (bean in list) {
            // 转成String 同一属性的不同值
            val valueStr = StringUtil.combineList(bean.valueList, "# @ #")
            submitStr.append(valueStr)
            submitStr.append(";@;")     // 不同属性
        }
        if (submitStr.length >= ";@;".length) {
            submitStr.delete(submitStr.length - ";@;".length, submitStr.length)
        }

        return submitStr.toString()
    }

    /**
     * 将本地数据转换成列表数据
     * */
    fun disposeRealmData(beans: List<ProductAttrBean>): List<ProductAttrBean> {
        val createUser = UserInfoUtil.getUser()
        if (createUser != null) {
            val submitBean: CreateProductSubmitBean? =
                    RealmHelper.instance.queryBeanById(CreateProductSubmitBean::class.java, "createUserId", createUser.id)
            if (submitBean != null) {
                val attrId = submitBean.attrId
                val attrValues = submitBean.attrValues
                // 没保存数据，返回
                if (StringUtil.isBlank(attrId)) {
                    return beans
                }
                // 没保存数据，返回
                if (StringUtil.isBlank(attrValues)) {
                    return beans
                }
                val idList: List<String> = StringUtil.split2List(attrId, ",")
                val valueList: List<String> = StringUtil.split2List(attrValues, ",")
                if (idList.isEmpty()) {
                    return beans
                }
                if (valueList.isEmpty()) {
                    return beans
                }
                val list: MutableList<ProductAttrBean> = ArrayList()
                for (bean in beans) {
                    val attrName = bean.attributeName
                    if (attrName != null) {
                        var index = -1
                        for (i in idList.indices) {
                            if (StringUtil.isEquals(idList[i], attrName.id)) {
                                index = i
                                break
                            }
                        }
                        if (index >= 0) {
                            if (valueList.size > index) {
                                if (attrName.type == 3) {   // 表格
                                    bean.attrList = formStr2List(valueList[index])
                                } else {    // 下拉，输入
                                    bean.value = valueList[index]
                                }
                            }
                        }
                    }
                    list.add(bean)
                }
                return list
            } else {
                return beans
            }
        } else {
            return beans
        }
    }

    /**
     * 产品属性本地数据转成表格数据格式
     * */
    private fun formStr2List(valueStr: String?): MutableList<MutableList<String>> {
        val list: MutableList<MutableList<String>> = ArrayList()

        if (StringUtil.isBlank(valueStr)) {
            return list
        }

        // 解析每一行数据
        val strList = StringUtil.split2List(valueStr, ";@;")
        if (strList.isNotEmpty()) {
            for (str in strList) {
                val childList = StringUtil.split2List(str, "# @ #")
                list.add(childList)
            }
        }
        return list
    }

    /**
     * 产品属性list转表格属性列表格式数据
     * */
    fun productStrList2FormList(list: List<List<String>>): List<ProductComponentBean> {
        val beans: MutableList<ProductComponentBean> = ArrayList()
        if (list.isEmpty()) {
            return beans
        }
        for (i in list.indices) {
            val bean = ProductComponentBean()
            bean.id = "$i"
            bean.valueList = list[i]
            beans.add(bean)
        }

        return beans
    }
}