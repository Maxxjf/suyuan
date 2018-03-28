package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.FrameRequest
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.beans.*
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.net.IGoodsApi

/**
 * Description: 货物有关
 * Author: gaobaiqiang
 * 2018/3/21 上午9:05.
 */
class GoodsModelImpl: IGoodsModel {

    private val mApi: IGoodsApi = FrameRequest.instance.createRequest(IGoodsApi::class.java)

    /**
     * 获取库存列表(产品列表)
     *
     * @param pageNo
     * @param pageSize
     * @param classifyId 二级分类id
     * @param isPlatform 是否私有产品 1是0不是
     * @param keyword 搜索关键字 条形码/名称/厂家
     * */
    override fun list(pageNo: Int, pageSize: Int, classifyId: String?, isPlatform: Int, keyword: String?, callback: DataCallback<ProductReturnBean>) {
        val params = FrameRequest.getAppParams()
        if (StringUtil.isNotBlank(classifyId)) {
            params["classifyId"] = classifyId!!
        }
        params["isPlatform"] = isPlatform
        params["keyword"] = keyword ?: ""
        params["pageNo"] = pageNo
        params["pageSize"] = pageSize

        BaseApi.dispose(mApi.list(params), callback)
    }

    /**
     * 获取商品详情
     *
     * @param id 商品id
     * */
    override fun details(id: String, callback: DataCallback<ProductDetailsBean>) {
        val params = FrameRequest.getAppParams()
        params["id"] = id

        BaseApi.dispose(mApi.details(params), callback)
    }

    /**
     * 调整价格
     *
     * @param goodsId 商品id
     * @param retailPrice 价格
     * */
    override fun editPrice(goodsId: String, retailPrice: Double, callback: DataCallback<EmptyReturnBean>) {
        val params = FrameRequest.getAppParams()
        params["goodsId"] = goodsId
        params["retailPrice"] = retailPrice

        BaseApi.dispose(mApi.editPrice(params), callback)
    }

    /**
     * 调整警告线
     *
     * @param goodsId 商品id
     * @param cordon 警告线
     * */
    override fun editWarnLine(goodsId: String, cordon: Int, callback: DataCallback<EmptyReturnBean>) {
        val params = FrameRequest.getAppParams()
        params["goodsId"] = goodsId
        params["cardon"] = cordon

        BaseApi.dispose(mApi.editCordon(params), callback)
    }

    /**
     * 获取库存告警列表
     * */
    override fun getStockWarnList(pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<StockWarnBean>>) {
        val params = FrameRequest.getAppParams()
        params["pageNo"] = pageNo
        params["pageSize"] = pageSize

        BaseApi.dispose(mApi.getStockWarnList(params), callback)
    }

    /**
     * 获取有效期告警列表
     * */
    override fun getValidWarnList(pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<ValidWarnBean>>) {
        val params = FrameRequest.getAppParams()
        params["pageNo"] = pageNo
        params["pageSize"] = pageSize

        BaseApi.dispose(mApi.getValidWarnList(params), callback)
    }
    /**
     * 扫码查询
     *  @param saleId   销售单id (首次为空,从第二次开始传)
     * */
    override fun scanCode(code: String, saleId: String, callback: DataCallback<ScanCodeBean>) {
        val params = FrameRequest.getAppParams()
        params["code"] = code
        params["saleId"] = saleId

        BaseApi.dispose(mApi.scanCode(params), callback)
    }

    /**
     * 退货
     * @param traceabilityIdStr   溯源记录id字符串(多个用 , 隔开)
     * */
    override fun salesReturn(money: String, traceabilityIdStr: String, callback: DataCallback<EmptyReturnBean>) {
        val params = FrameRequest.getAppParams()
        params["money"] = money
        params["traceabilityIdStr"] = traceabilityIdStr

        BaseApi.dispose(mApi.salesReturn(params), callback)
    }

    /**
     * 获取退货记录列表
     * */
    override fun getReturnRecord(startTime: String, endTime: String, pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<CodeBean>>) {
        val params = FrameRequest.getAppParams()
        params["startTime"] = startTime
        params["endTime"] = endTime
        params["pageNo"] = pageNo
        params["pageSize"] = pageSize

        BaseApi.dispose(mApi.getReturnedRecord(params), callback)
    }

    /**
     * 卖货扫码搜索
     *
     * @param keyword 入库批次码（溯源码）
     * */
    override fun saleSearchProduct(keyword: String?, callback: DataCallback<SellersBean>) {
        val params = FrameRequest.getAppParams()
        params["keyword"] = keyword ?: ""

        BaseApi.dispose(mApi.saleSearchProduct(params), callback)
    }
}