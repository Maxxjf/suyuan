package com.qcloud.suyuan.net

import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.InStorageBean
import com.qcloud.suyuan.beans.OutStorageBean
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * 类说明：库存有关
 * Author: Kuzan
 * Date: 2018/3/27 15:17.
 */
interface IStorageApi {
    /** 有效期告警列表撤消入库功能 */
    @GET(UrlConstants.OUT_STORAGE_IN_VALID_WARN)
    fun outStorageInValidWarn(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

    /** 出库商品查询 */
    @GET(UrlConstants.OUT_STORAGE_SEARCH)
    fun outStorageSearch(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<OutStorageBean>>

    /** 搜索产品入库 */
    @GET(UrlConstants.STORAGE_SEARCH)
    fun searchList(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<ProductBean>>>

    /** 提交入库 */
    @GET(UrlConstants.STORAGE_SAVE)
    fun save(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<InStorageBean>>
}