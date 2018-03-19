package com.qcloud.suyuan.net

import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.MainFormBean
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable

/**
 * Description: 报表有关
 * Author: gaobaiqiang
 * 2018/3/19 下午7:18.
 */
object IFormApi {
    /** 首页报表 */
    fun getMainForm(params: HttpParams): Observable<BaseResponse<MainFormBean>> {
        val type = object : TypeToken<BaseResponse<MainFormBean>>() {

        }.type

        return OkGoRequest.instance.getRequest(UrlConstants.MAIN_FORM, type, params)
    }
}