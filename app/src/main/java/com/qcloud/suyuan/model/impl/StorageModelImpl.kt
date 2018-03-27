package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.model.IStorageModel
import com.qcloud.suyuan.net.IStorageApi

/**
 * 类说明：库存有关
 * Author: Kuzan
 * Date: 2018/3/27 15:20.
 */
class StorageModelImpl: IStorageModel {
    /**
     * 有效期告警列表撤消入库功能
     *
     * @param id
     * @param number 出库数量
     * */
    override fun outStorageInValidWarn(id: String, number: Int, callback: DataCallback<EmptyReturnBean>) {
        val params = OkGoRequest.getAppParams()
        params.put("id", id)
        params.put("number", number)
        BaseApi.dispose(IStorageApi.outStorageInValidWarn(params), callback)
    }

    /**
     * 搜索产品入库
     *
     * @param keyword 条码、商品名、厂家名
     * */
    override fun searchList(keyword: String, callback: DataCallback<ReturnDataBean<ProductBean>>) {
        val params = OkGoRequest.getAppParams()
        params.put("keyword", keyword)
        BaseApi.dispose(IStorageApi.searchList(params), callback)
    }
}