package com.qcloud.suyuan.net

import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.*
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
    /**赊账还款*/
    fun repayment(params: HttpParams):Observable<BaseResponse<EmptyReturnBean>>{
        val type=object :TypeToken<BaseResponse<EmptyReturnBean>>(){}.type
        return OkGoRequest.instance.getRequest(UrlConstants.REPAYMENT,type,params)
    }
    /**商品查询*/
    fun saleSearch(params: HttpParams):Observable<BaseResponse<SaleSearchBean>>{
        val type=object :TypeToken<BaseResponse<SaleSearchBean>>(){}.type
        return OkGoRequest.instance.getRequest(UrlConstants.SALE_SEARCH,type,params)
    }
    /**销售列表*/
    fun getSaleList(params: HttpParams):Observable<BaseResponse<ReturnDataBean<SaleListBean>>>{
        val type=object :TypeToken<BaseResponse<ReturnDataBean<SaleListBean>>>(){}.type
        return OkGoRequest.instance.getRequest(UrlConstants.SALE_LIST,type,params)
    }
    /**销售详情*/
    fun getSaleInfo(params: HttpParams):Observable<BaseResponse<SaleInfoBean>>{
        val type=object :TypeToken<BaseResponse<SaleInfoBean>>(){}.type
        return OkGoRequest.instance.getRequest(UrlConstants.SALE_INFO,type,params)
    }
}