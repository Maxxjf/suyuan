package com.qcloud.suyuan.net

import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.suyuan.beans.*
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Description: 货物有关
 * Author: gaobaiqiang
 * 2018/3/21 上午9:04.
 */
interface IGoodsApi {

    /** 产品列表 */
    @GET(UrlConstants.GET_STOCK_LIST)
    fun list(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ProductReturnBean>>

    /** 产品详情 */
    @GET(UrlConstants.GET_PRODUCT_DETAILS)
    fun details(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ProductDetailsBean>>

    /** 修改价格 */
    @GET(UrlConstants.EDIT_PRICE)
    fun editPrice(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

    /** 调整警告线 */
    @GET(UrlConstants.EDIT_CORDON)
    fun editCordon(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

    /** 库存告警报表 */
    @GET(UrlConstants.GET_STOCK_WARN_LIST)
    fun getStockWarnList(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<StockWarnBean>>>

    /** 有效期告警报表 */
    @GET(UrlConstants.GET_VALID_WARN_LIST)
    fun getValidWarnList(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<ValidWarnBean>>>

    /** 退货记录 */
    @GET(UrlConstants.GET_RETURNED_RECORD_LIST)
    fun getReturnedRecord(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<CodeBean>>>

    /** 扫码查询 */
    @GET(UrlConstants.SCAN_CODE)
    fun scanCode(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ScanCodeBean>>

    /** 退货 */
    @GET(UrlConstants.SALES_RETURN)
    fun salesReturn(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

}