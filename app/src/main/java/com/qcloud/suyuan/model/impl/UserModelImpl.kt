package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.FrameRequest
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.LoginReturnBean
import com.qcloud.suyuan.model.IUserModel
import com.qcloud.suyuan.net.IUserApi

/**
 * 类说明：用户有关
 * Author: IceBerg
 * Date: 2018/3/20
 */
class UserModelImpl : IUserModel {
    private val mApi: IUserApi = FrameRequest.instance.createRequest(IUserApi::class.java)

    /**
     * 登录
     * @time 2018/3/19 13:43
     */
    override fun login(loginAccount: String, password: String, callback: DataCallback<LoginReturnBean>) {
        val params = FrameRequest.getAppParams()
        params["account"] = loginAccount
        params["password"] = password

        BaseApi.dispose(mApi.login(params), callback)
    }

    /**
     * 获取验证码
     */
    override fun getCode(mobile:String,callback: DataCallback<EmptyReturnBean>){
        val params = FrameRequest.getAppParams()
        params["mobile"] = mobile

        BaseApi.dispose(mApi.getCode(params), callback)
    }

    /**
     * 忘记密码（修改密码）
     */
    override fun forgetPassword(code:String,mobile: String,password: String,callback: DataCallback<EmptyReturnBean>){
        val params = FrameRequest.getAppParams()
        params["code"] = code
        params["mobile"] = mobile
        params["password"] = password

        BaseApi.dispose(mApi.forgetPassWord(params), callback)
    }

    /**
     * 退出登录
     * */
    override fun logout(callback: DataCallback<EmptyReturnBean>) {
        val params = FrameRequest.getAppParams()

        BaseApi.dispose(mApi.logout(params), callback)
    }
}