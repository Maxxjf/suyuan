package com.qcloud.suyuan.model

import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.LoginReturnBean
import com.qcloud.suyuan.beans.VersionBean


/**
 * 类说明：用户有关
 * Author: Kuzan
 * Date: 2017/6/6 14:32.
 */
interface IUserModel {
    /**
     * 用户登录
     * */
    fun login(loginAccount: String, password: String, callback: DataCallback<LoginReturnBean>)
    /**
     * 获取验证码
     * */
    fun getCode(mobile: String, callback: DataCallback<EmptyReturnBean>)
    /**
     * 忘记密码，修改密码
     * */
    fun forgetPassword(code: String, mobile: String, password: String, callback: DataCallback<EmptyReturnBean>)

    /**
     * 退出登录
     * */
    fun logout(callback: DataCallback<EmptyReturnBean>)

    /**
     * 检查新版本
     * */
    fun checkVersion(version: Int, callback: DataCallback<VersionBean>)
}