package com.qcloud.suyuan.model

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.*

/**
 * Description: 货物有关
 * Author: gaobaiqiang
 * 2018/3/21 上午9:04.
 */
interface IGoodsModel {
    /**获取库存列表(产品列表)*/
    fun list(pageNo: Int, pageSize: Int, callback: DataCallback<ProductReturnBean>)

    /**获取商品详情*/
    fun details(id: String, callback: DataCallback<ProductDetailsBean>)

    /**调整价格*/
    fun editPrice(goodsId: String, retailPrice: Double, callback: DataCallback<EmptyReturnBean>)

    /**调整警告线*/
    fun editWarnLine(goodsId: String, cordon: Int, callback: DataCallback<EmptyReturnBean>)

    /**获取库存告警列表*/
    fun getStockWarnList(pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<StockWarnBean>>)

    /**获取有期告警列表*/
    fun getValidWarnList(pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<ValidWarnBean>>)

    /**获取退货记录*/
    fun getReturnRecord(startTime:String,endTime:String,pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<CodeBean>>)

    fun scanCode(code: String, saleId: String, callback: DataCallback<ScanCodeBean>)

    fun salesReturn(money: String, traceabilityIdStr: String, callback: DataCallback<EmptyReturnBean>)
}