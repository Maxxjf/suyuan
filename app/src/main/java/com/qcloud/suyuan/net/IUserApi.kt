package com.qcloud.suyuan.net

import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.LoginReturnBean
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable

/**
 * 类说明：用户有关
 * Author: Kuzan
 * Date: 2017/6/6 15:19.
 */
object IUserApi {
    /** 登录 */
    fun login(params: HttpParams): Observable<BaseResponse<LoginReturnBean>> {
        val type = object : TypeToken<BaseResponse<LoginReturnBean>>() {}.type
        return OkGoRequest.instance.getRequest(UrlConstants.LOGIN, type, params)
    }

    /**获取短信验证码*/
    fun getCode(params: HttpParams):Observable<BaseResponse<EmptyReturnBean>>{
        val type=object :TypeToken<BaseResponse<EmptyReturnBean>>(){}.type
        return OkGoRequest.instance.getRequest(UrlConstants.GET_CODE,type,params)
    }

    /**修改密码*/
    fun forgetPassWord(params: HttpParams):Observable<BaseResponse<EmptyReturnBean>>{
        val type=object :TypeToken<BaseResponse<EmptyReturnBean>>(){}.type
        return OkGoRequest.instance.getRequest(UrlConstants.FORGET_PASSWORD,type,params)
    }

    /**退出登录*/
    fun logout(params: HttpParams): Observable<BaseResponse<EmptyReturnBean>> {
        val type = object : TypeToken<BaseResponse<EmptyReturnBean>>() {

        }.type

        return OkGoRequest.instance.getRequest(UrlConstants.LOGOUT, type, params)
    }
}