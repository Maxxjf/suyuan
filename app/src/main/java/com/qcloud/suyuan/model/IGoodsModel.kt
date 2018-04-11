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
    /**获取产品分类列表*/
    fun classifyList(callback: DataCallback<ReturnDataBean<ProductClassifyBean>>)

    /**获取库存列表(产品列表)*/
    fun list(pageNo: Int, pageSize: Int, classifyId: String?, isPlatform: Int, keyword: String?, callback: DataCallback<ReturnDataBean<ProductBean>>)

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

    /**卖货扫码搜索*/
    fun saleSearchProduct(keyword: String?, callback: DataCallback<SellersBean>)

    /**卖货结算*/
    fun saleSettlement(list: String, idInfo: IDBean, saleDiscount: Double,
                       saleRealPay: Double, salePayMethod: Int, salePurpose: String?, saleRemark: String?,
                       callback: DataCallback<SettlementResBean>)

    /**创建修改私有产品*/
    fun editMyProduct(id: String?, callback: DataCallback<CreateProductBean>)

    /**获取门店信息*/
    fun getFactoryByClassify(classifyId: String, callback: DataCallback<ReturnDataBean<ProductMillBean>>)

    /**检验条码是否重复*/
    fun isBarCodeRepeat(id: String?, barCode: String, name: String, specification: String, millId: String, callback: DataCallback<EmptyReturnBean>)

    /**创建修改私有产品 -下一步*/
    fun createProductNext(id: String?, classifyId: String, callback: DataCallback<ReturnDataBean<ProductAttrBean>>)

    /**保存私有产品*/
    fun add(bean: CreateProductSubmitBean, callback: DataCallback<EmptyReturnBean>)

    /**溯源记录列表*/
    fun suyuanList(keyword: String?, pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<SuyuanRecordBean>>)

    /**溯源详情*/
    fun suyuanSearch(traceabilityCode: String, callback: DataCallback<SuyuanDetailsBean>)

    /**批次码搜索*/
    fun batchSearch(code: String, callback: DataCallback<BatchDetailsBean>)

    /**条形码搜索*/
    fun barCodeSearch(code: String, callback: DataCallback<BarCodeDetailsBean>)
}