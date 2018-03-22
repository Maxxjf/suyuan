package com.qcloud.suyuan.model.impl


import com.lzy.okgo.model.HttpParams
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

    var mParams: HttpParams = OkGoRequest.getAppParams()

    /**
     * 登录
     * @time 2018/3/19 13:43
     */
    override fun login(loginAccount: String, password: String, callback: DataCallback<LoginReturnBean>) {
        mParams.put("account", loginAccount)
        mParams.put("password", password)
        BaseApi.dispose(IUserApi.login(mParams), callback)
    }
    /**
     * 获取验证码
     */
    override fun getCode(mobile:String,callback: DataCallback<EmptyResBean>){
        mParams.put("mobile",mobile)
        BaseApi.dispose(IUserApi.getCode(mParams),callback)
    }
    /**
     * 忘记密码（修改密码）
     */
    override fun forgetPassword(code:String,mobile: String,password: String,callback: DataCallback<EmptyResBean>){
        mParams.put("code",code)
        mParams.put("mobile",mobile)
        mParams.put("password",password)
        BaseApi.dispose(IUserApi.forgetPassWord(mParams),callback)
    }



}