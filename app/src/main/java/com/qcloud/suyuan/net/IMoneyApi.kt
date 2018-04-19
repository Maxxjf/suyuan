package com.qcloud.suyuan.net

import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.suyuan.beans.*
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * 类说明：金钱有关的
 * Author: iceberg
 * Date: 2018-3-25.
 */
interface IMoneyApi {
    /**赊账列表*/
    @GET(UrlConstants.GET_CREDIT_LIST)
    fun getCreditList(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<CreditListBean>>

    /**赊账详细*/
    @GET(UrlConstants.GET_CREDIT_INFO)
    fun getCreditInfo(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<CreditInfoBean>>>

    /**赊账还款*/
    @GET(UrlConstants.REPAYMENT)
    fun repayment(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

    /**还款历史*/
    @GET(UrlConstants.REPAYMENT_HISTORY)
    fun repaymentHistory(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<RepaymentListBean>>>

    /**个人声誉还款总金额*/
    @GET(UrlConstants.CREDIT_ALL_MONEY)
    fun creditAllMoney(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<CreditAllMoneyBean>>

    /**全部还清*/
    @GET(UrlConstants.REPAYMENT_ALL)
    fun repaymentAll(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>


    /**销售列表*/
    @GET(UrlConstants.SALE_LIST)
    fun getSaleList(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<SaleListBean>>>

    /**销售详情*/
    @GET(UrlConstants.SALE_INFO)
    fun getSaleInfo(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<SaleInfoBean>>

}