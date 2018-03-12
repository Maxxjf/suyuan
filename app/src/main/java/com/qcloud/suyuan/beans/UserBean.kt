package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * 类说明：用户
 * Author: Kuzan
 * Date: 2018/3/12 15:22.
 */
open class UserBean: RealmObject() {

    var id: Long = -1L
    @PrimaryKey
    var name: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var nickname: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var headImg: String? = null


    override fun toString(): String {
        return "UserBean(id=$id, headImg=$headImg)"
    }
}