package com.qcloud.suyuan.utils

import com.qcloud.suyuan.beans.IDBean
import com.qcloud.suyuan.realm.RealmHelper

/**
 * Description: 身份证处理工具
 * Author: gaobaiqiang
 * 2018/3/29 下午1:52.
 */
object IDCardUtil {
    /**
     * 添加或更新身份证
     * */
    fun addOrUpdate(bean: IDBean?) {
        if (bean != null) {
            RealmHelper.instance.addOrUpdateBean(bean)
        }
    }

    /**
     * 获取所有身份证
     * */
    fun listAll(): List<IDBean> {
        return RealmHelper.instance.queryBeans(IDBean::class.java)
    }
}