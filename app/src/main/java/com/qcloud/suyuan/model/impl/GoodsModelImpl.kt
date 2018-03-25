package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.*
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.net.IGoodsApi

/**
 * Description: 货物有关
 * Author: gaobaiqiang
 * 2018/3/21 上午9:05.
 */
class GoodsModelImpl: IGoodsModel {

    /**
     * 获取库存列表(产品列表)
     * */
    override fun list(pageNo: Int, pageSize: Int, callback: DataCallback<ProductReturnBean>) {
        val params = OkGoRequest.getAppParams()
        params.put("pageNo", pageNo)
        params.put("pageSize", pageSize)

        BaseApi.dispose(IGoodsApi.list(params), callback)
    }

    /**
     * 获取商品详情
     *
     * @param id 商品id
     * */
    override fun details(id: String, callback: DataCallback<ProductDetailsBean>) {
        val params = OkGoRequest.getAppParams()
        params.put("id", id)

        BaseApi.dispose(IGoodsApi.details(params), callback)
    }

    /**
     * 调整价格
     *
     * @param goodsId 商品id
     * @param retailPrice 价格
     * */
    override fun editPrice(goodsId: String, retailPrice: Double, callback: DataCallback<EmptyReturnBean>) {
        val params = OkGoRequest.getAppParams()
        params.put("goodsId", goodsId)
        params.put("retailPrice", retailPrice)

        BaseApi.dispose(IGoodsApi.editPrice(params), callback)
    }

    /**
     * 调整警告线
     *
     * @param goodsId 商品id
     * @param cordon 警告线
     * */
    override fun editWarnLine(goodsId: String, cordon: Int, callback: DataCallback<EmptyReturnBean>) {
        val params = OkGoRequest.getAppParams()
        params.put("goodsId", goodsId)
        params.put("cordon", cordon)

        BaseApi.dispose(IGoodsApi.editCordon(params), callback)
    }

    /**
     * 获取库存告警列表
     * */
    override fun getStockWarnList(pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<StockWarnBean>>) {
        val params = OkGoRequest.getAppParams()
        params.put("pageNo", pageNo)
        params.put("pageSize", pageSize)

        BaseApi.dispose(IGoodsApi.getStockWarnList(params), callback)
    }

    /**
     * 获取有效期告警列表
     * */
    override fun getValidWarnList(pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<ValidWarnBean>>) {
        val params = OkGoRequest.getAppParams()
        params.put("pageNo", pageNo)
        params.put("pageSize", pageSize)

        BaseApi.dispose(IGoodsApi.getValidWarnList(params), callback)
    }
    /**
     * 扫码查询
     *  @param saleId   销售单id (首次为空,从第二次开始传)
     * */
    override fun scanCode(code: String, saleId: String, callback: DataCallback<ScanCodeBean>) {
        val params=OkGoRequest.getAppParams()
        params.put("code",code)
        params.put("saleId",saleId)
        BaseApi.dispose(IGoodsApi.scanCode(params),callback)
    }
    /**
     * 退货
     * @param traceabilityIdStr   溯源记录id字符串(多个用 , 隔开)
     * */
    override fun salesReturn(money: String, traceabilityIdStr: String, callback: DataCallback<EmptyReturnBean>) {
        val params=OkGoRequest.getAppParams()
        params.put("money",money)
        params.put("traceabilityIdStr",traceabilityIdStr)
        BaseApi.dispose(IGoodsApi.salesReturn(params),callback)
    }
    /**
     * 获取退货记录列表
     * */
    override fun getReturnRecord(startTime: String, endTime: String, pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<CodeBean>>) {
        val params=OkGoRequest.getAppParams()
        params.put("startTime",startTime)
        params.put("endTime",endTime)
        params.put("pageNo",pageNo)
        params.put("pageSize",pageSize)
        BaseApi.dispose(IGoodsApi.getReturnedRecord(params),callback)
    }
}