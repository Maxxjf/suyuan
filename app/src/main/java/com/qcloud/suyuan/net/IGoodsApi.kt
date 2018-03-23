package com.qcloud.suyuan.net

import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.CodeBean
import com.qcloud.suyuan.beans.StockWarnBean
import com.qcloud.suyuan.beans.ValidWarnBean
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable

/**
 * Description: 货物有关
 * Author: gaobaiqiang
 * 2018/3/21 上午9:04.
 */
object IGoodsApi {
    /** 库存告警报表 */
    fun getStockWarnList(params: HttpParams): Observable<BaseResponse<ReturnDataBean<StockWarnBean>>> {
        val type = object : TypeToken<BaseResponse<ReturnDataBean<StockWarnBean>>>() {

        }.type

        return OkGoRequest.instance.getRequest(UrlConstants.GET_STOCK_WARN_LIST, type, params)
    }

    /** 有效期告警报表 */
    fun getValidWarnList(params: HttpParams): Observable<BaseResponse<ReturnDataBean<ValidWarnBean>>> {
        val type = object : TypeToken<BaseResponse<ReturnDataBean<ValidWarnBean>>>() {

        }.type

        return OkGoRequest.instance.getRequest(UrlConstants.GET_STOCK_WARN_LIST, type, params)
    }
    /** 退货记录 */
    fun getReturnedRecord(params: HttpParams): Observable<BaseResponse<ReturnDataBean<CodeBean>>> {
        val type = object : TypeToken<BaseResponse<ReturnDataBean<CodeBean>>>() {
        }.type

        return OkGoRequest.instance.getRequest(UrlConstants.GET_RETURNED_RECORD_LIST, type, params)
    }
}