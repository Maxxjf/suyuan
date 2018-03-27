package com.qcloud.suyuan.net

import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable

/**
 * 类说明：库存有关
 * Author: Kuzan
 * Date: 2018/3/27 15:17.
 */
object IStorageApi {
    /** 有效期告警列表撤消入库功能 */
    fun outStorageInValidWarn(params: HttpParams): Observable<BaseResponse<EmptyReturnBean>> {
        val type = object : TypeToken<BaseResponse<EmptyReturnBean>>() {

        }.type
        return OkGoRequest.instance.getRequest(UrlConstants.OUT_STORAGE_IN_VALID_WARN, type, params)
    }
}