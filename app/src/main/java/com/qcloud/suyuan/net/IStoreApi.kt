package com.qcloud.suyuan.net

import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.StoreBean
import com.qcloud.suyuan.beans.SupplierBean
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * 类说明：门店有关
 * Author: Kuzan
 * Date: 2018/3/28 15:55.
 */
interface IStoreApi {
    /** 门店信息 */
    @GET(UrlConstants.STORE_INFO)
    fun getInfo(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<StoreBean>>
    /** 供应商列表 */
    @GET(UrlConstants.SUPPLIER_LIST)
    fun supplierList(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<SupplierBean>>>

    /** 修改或增加供应商 */
    @GET(UrlConstants.SUPPLIER_SAVE_UPDATE)
    fun supplierSaveOrUpdate(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

    /** 修改门店密码 */
    @GET(UrlConstants.STORE_EDIT_PASSWORD)
    fun editPassword(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

    /** 修改门店信息 */
    @GET(UrlConstants.STORE_EDIT_INFO)
    fun editInfo(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

}
