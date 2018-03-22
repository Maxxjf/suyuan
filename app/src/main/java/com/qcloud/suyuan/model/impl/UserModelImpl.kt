package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.EmptyResBean
import com.qcloud.suyuan.beans.LoginReturnBean
import com.qcloud.suyuan.model.IUserModel
import com.qcloud.suyuan.net.IUserApi

/**
 * 类说明：用户有关
 * Author: IceBerg
 * Date: 2018/3/20
 */
class UserModelImpl : IUserModel {

    /**
     * 登录
     * @time 2018/3/19 13:43
     */
    override fun login(loginAccount: String, password: String, callback: DataCallback<LoginReturnBean>) {
        val params = OkGoRequest.getAppParams()
        params.put("account", loginAccount)
        params.put("password", password)
        BaseApi.dispose(IUserApi.login(params), callback)
    }

    /**
     * 获取验证码
     */
    override fun getCode(mobile:String,callback: DataCallback<EmptyResBean>){
        val params = OkGoRequest.getAppParams()
        params.put("mobile",mobile)
        BaseApi.dispose(IUserApi.getCode(params),callback)
    }

    /**
     * 忘记密码（修改密码）
     */
    override fun forgetPassword(code:String,mobile: String,password: String,callback: DataCallback<EmptyResBean>){
        val params = OkGoRequest.getAppParams()
        params.put("code",code)
        params.put("mobile",mobile)
        params.put("password",password)
        BaseApi.dispose(IUserApi.forgetPassWord(params),callback)
    }

}