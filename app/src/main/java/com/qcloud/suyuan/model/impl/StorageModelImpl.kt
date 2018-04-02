package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.FrameRequest
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.InStorageBean
import com.qcloud.suyuan.beans.OutStorageBean
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.model.IStorageModel
import com.qcloud.suyuan.net.IStorageApi

/**
 * 类说明：库存有关
 * Author: Kuzan
 * Date: 2018/3/27 15:20.
 */
class StorageModelImpl: IStorageModel {
    private val mApi: IStorageApi = FrameRequest.instance.createRequest(IStorageApi::class.java)

    /**
     * 有效期告警列表撤消入库功能
     *
     * @param id
     * @param number 出库数量
     * */
    override fun outStorageInValidWarn(id: String, number: Int, callback: DataCallback<EmptyReturnBean>) {
        val params = FrameRequest.getAppParams()
        params["id"] = id
        params["number"] = number

        BaseApi.dispose(mApi.outStorageInValidWarn(params), callback)
    }

    /**
     * 出库商品查询
     *
     * @param keyword 入库批次码
     * */
    override fun outStorageSearch(keyword: String, callback: DataCallback<OutStorageBean>) {
        val params = FrameRequest.getAppParams()
        params["keyword"] = keyword
        BaseApi.dispose(mApi.outStorageSearch(params), callback)
    }

    /**
     * 搜索产品入库
     *
     * @param keyword 条码、商品名、厂家名
     * */
    override fun searchList(keyword: String, callback: DataCallback<ReturnDataBean<ProductBean>>) {
        val params = FrameRequest.getAppParams()
        params["keyword"] = keyword

        BaseApi.dispose(mApi.searchList(params), callback)
    }

    /**
     * 提交入库
     *
     * @param goodId 商品ID
     * @param number 入库数量
     * @param price 进货价格
     * @param expDate 生产日期
     * @param stopDate 有效期截止日期
     * @param supplierId supplierId
     * */
    override fun save(goodId: String, number: Int, price: Double, expDate: String, stopDate: String, supplierId: String?, callback: DataCallback<InStorageBean>) {
        val params = FrameRequest.getAppParams()
        params["goodId"] = goodId
        params["number"] = number
        params["price"] = price
        params["expDate"] = expDate
        params["stopDate"] = stopDate
        params["supplierId"] = supplierId ?: ""

        BaseApi.dispose(mApi.save(params), callback)
    }
}