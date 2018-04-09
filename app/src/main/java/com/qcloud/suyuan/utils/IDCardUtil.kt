package com.qcloud.suyuan.utils

import com.qcloud.suyuan.beans.IDBean
import com.qcloud.suyuan.realm.RealmHelper
import timber.log.Timber

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
            Timber.e("Util: $bean")
            val createUser = UserInfoUtil.getUser()
            if (createUser != null) {
                bean.loginUserId = createUser.id
            }
            RealmHelper.instance.addOrUpdateBean(bean)
        }
    }

    /**
     * 获取所有身份证
     * */
    fun listAll(): List<IDBean> {
        var loginUserId = "0"
        val createUser = UserInfoUtil.getUser()
        if (createUser != null) {
            loginUserId = createUser.id
        }
        return RealmHelper.instance.queryListByValue(IDBean::class.java, "loginUserId", loginUserId)
    }
}