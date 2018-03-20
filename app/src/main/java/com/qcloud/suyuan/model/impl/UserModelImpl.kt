package com.qcloud.suyuan.model.impl


import com.lzy.okgo.model.HttpParams
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.LoginReturnBean
import com.qcloud.suyuan.model.IUserModel
import com.qcloud.suyuan.net.IUserApi

/**
 * 类说明：用户有关
 * Author: Kuzan
 * Date: 2017/6/6 14:35.
 */
class UserModelImpl : IUserModel {

    var mParams: HttpParams = OkGoRequest.getAppParams()

    /**
     * 登录
     *
     * @time 2018/3/19 13:43
     */
    override fun login(loginAccount: String, password: String, callback: DataCallback<LoginReturnBean>) {
        mParams.put("account", loginAccount)
        mParams.put("password", password)
        BaseApi.dispose(IUserApi.login(mParams), callback)
    }

}