package com.qcloud.suyuan.net

import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.suyuan.beans.*
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * Description: 货物有关
 * Author: gaobaiqiang
 * 2018/3/21 上午9:04.
 */
interface IGoodsApi {
    /** 产品分类列表 */
    @GET(UrlConstants.GET_CLASSIFY_LIST)
    fun classifyList(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<ProductClassifyBean>>>

    /** 产品列表 */
    @GET(UrlConstants.GET_STOCK_LIST)
    fun list(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<ProductBean>>>

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

    /** 卖货扫码查询 */
    @GET(UrlConstants.SALE_SEARCH_PRODUCT)
    fun saleSearchProduct(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<SellersBean>>

    /** 卖货结算 */
    @GET(UrlConstants.SALE_SETTLEMENT)
    fun saleSettlement(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<SettlementResBean>>

    /** 创建修改私有产品 */
    @GET(UrlConstants.EDIT_MY_PRODUCT)
    fun editMyProduct(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<CreateProductBean>>

    /** 选择分类-获取厂家 */
    @GET(UrlConstants.GET_FACTORY)
    fun getFactoryByClassify(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<ProductMillBean>>>

    /**检验条码是否重复*/
    @GET(UrlConstants.IS_BAR_CODE_REPEAT)
    fun isBarCodeRepeat(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

    /**创建修改私有产品 -下一步*/
    @GET(UrlConstants.CREATE_PRODUCT_NEXT)
    fun createProductNext(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<ProductAttrBean>>>

    /**保存私有产品*/
    @POST(UrlConstants.ADD_PRODUCT)
    fun add(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<EmptyReturnBean>>

    /**溯源记录列表*/
    @GET(UrlConstants.SUYUAN_LIST)
    fun suyuanList(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<ReturnDataBean<SuyuanRecordBean>>>

    /**溯源记录搜索*/
    @POST(UrlConstants.SUYUAN_SEARCH)
    fun suyuanSearch(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<SuyuanDetailsBean>>

    /**批次码搜索*/
    @POST(UrlConstants.BATCH_SEARCH)
    fun batchSearch(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<BatchDetailsBean>>

    /**条形码搜索*/
    @POST(UrlConstants.BAR_CODE_SEARCH)
    fun barCodeSearch(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<BarCodeDetailsBean>>
}