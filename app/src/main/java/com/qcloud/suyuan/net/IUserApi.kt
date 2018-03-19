package com.qcloud.suyuan.net

import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.EmptyResBean
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable

/**
 * 类说明：用户有关
 * Author: Kuzan
 * Date: 2017/6/6 15:19.
 */
object IUserApi {
    /** 登录 */
    fun login(params: HttpParams): Observable<BaseResponse<EmptyResBean>> {
        val type = object : TypeToken<BaseResponse<EmptyResBean>>() {

        }.type

        return OkGoRequest.instance.getRequest(UrlConstants.LOGIN, type, params)
    }
}