package com.qcloud.suyuan.net

import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.CreditInfoBean
import com.qcloud.suyuan.beans.CreditListBean
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable

/**
 * 类说明：金钱有关的
 * Author: iceberg
 * Date: 2018-3-25.
 */
object IMoneyApi {
    /**赊账列表*/
    fun getCreditList(params: HttpParams):Observable<BaseResponse<CreditListBean>>{
        val type=object :TypeToken<BaseResponse<CreditListBean>>(){}.type
        return OkGoRequest.instance.getRequest(UrlConstants.GET_CREDIT_LIST,type,params)
    }
    /**赊账详细*/
    fun getCreditInfo(params: HttpParams):Observable<BaseResponse<ReturnDataBean<CreditInfoBean>>>{
        val type=object :TypeToken<BaseResponse<ReturnDataBean<CreditInfoBean>>>(){}.type
        return OkGoRequest.instance.getRequest(UrlConstants.GET_CREDIT_INFO,type,params)
    }
}