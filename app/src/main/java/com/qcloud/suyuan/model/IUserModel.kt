package com.qcloud.suyuan.model

import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.EmptyResBean


/**
 * 类说明：用户有关
 * Author: Kuzan
 * Date: 2017/6/6 14:32.
 */
interface IUserModel {
    /**
     * 用户登录
     * */
    fun login(loginAccount: String, password: String, callback: DataCallback<EmptyResBean>)

}