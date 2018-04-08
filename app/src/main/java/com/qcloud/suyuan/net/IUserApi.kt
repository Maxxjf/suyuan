package com.qcloud.suyuan.net

import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.LoginReturnBean
import com.qcloud.suyuan.beans.VersionBean
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap


/**
 * 类说明：用户有关
 * Author: Kuzan
 * Date: 2017/6/6 15:19.
 */
interface IUserApi {
    /** 登录 */
    @GET(UrlConstants.LOGIN)
    fun login(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<LoginReturnBean>>

    /**获取短信验证码*/
    @GET(UrlConstants.GET_CODE)
    fun getCode(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

    /**修改密码*/
    @GET(UrlConstants.FORGET_PASSWORD)
    fun forgetPassWord(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

    /**退出登录*/
    @GET(UrlConstants.LOGOUT)
    fun logout(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

    /**检查新版本*/
    @GET(UrlConstants.CHECK_VERSION)
    fun checkVersion(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<VersionBean>>

}