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
     * 获取产品分类列表
     *
     * @param callback
     * */
    override fun classifyList(callback: DataCallback<ReturnDataBean<ProductClassifyBean>>) {
        val params = FrameRequest.getAppParams()

        BaseApi.dispose(mApi.classifyList(params), callback)
    }

    /**
     * 获取库存列表(产品列表)
     *
     * @param pageNo
     * @param pageSize
     * @param classifyId 二级分类id
     * @param isPlatform 是否私有产品 1不是0是
     * @param keyword 搜索关键字 条形码/名称/厂家
     * */
    override fun list(pageNo: Int, pageSize: Int, classifyId: String?, isPlatform: Int, keyword: String?, callback: DataCallback<ReturnDataBean<ProductBean>>) {
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
        params["cordon"] = cordon

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

    /**
     * 订单结算
     *
     * @param list json格式 [{"recordId":id,"goodsNum":1,"price":1}]
     * @param saleDiscount 优惠金额
     * @param saleRealPay 实付金额，现金支付传，其他方式可不传
     * @param salePayMethod 支付方式
     * @param salePurpose 购买用途
     * @param saleRemark 备注
     * @param callback
     * */
    override fun saleSettlement(list: String, idInfo: IDBean, saleDiscount: Double, saleRealPay: Double, salePayMethod: Int,
                                salePurpose: String?, saleRemark: String?, callback: DataCallback<SettlementResBean>) {

        val params = FrameRequest.getAppParams()
        params["list"] = list
        params["purchaser.address"] = idInfo.address ?: ""          // 地址
        params["purchaser.gender"] = idInfo.gender                  // 购买者性别 1男0女
        params["purchaser.headImage"] = idInfo.userImgBase64 ?: ""  // 照片(base64格式)
        params["purchaser.id"] = idInfo.id                          // 购买者ID
        params["purchaser.idCard"] = idInfo.idCode ?: ""            // 身份证号
        params["purchaser.issuingOrganization"] = idInfo.department ?: ""   // 签发机关
        params["purchaser.mobile"] = idInfo.mobile ?: ""            // 联系电话
        params["purchaser.nation"] = idInfo.nation ?: ""            // 民族
        params["purchaser.purchaserNmae"] = idInfo.name ?: ""       // 购买者名称
        params["purchaser.validBegin"] = idInfo.startDate ?: ""     // 有效期始
        params["purchaser.validEnd"] = idInfo.endDate ?: ""         // 有效期止
        params["sale.discount"] = saleDiscount
        params["sale.realPay"] = saleRealPay
        params["sale.payMethod"] = salePayMethod
        params["sale.purpose"] = salePurpose ?: ""
        params["sale.remark"] = saleRemark ?: ""

        BaseApi.dispose(mApi.saleSettlement(params), callback)
    }

    /**
     * 创建修改私有产品
     *
     * @param id 商品id,用于修改,可不传或传空
     * */
    override fun editMyProduct(id: String?, callback: DataCallback<CreateProductBean>) {
        val params = FrameRequest.getAppParams()
        params["id"] = id ?: ""

        BaseApi.dispose(mApi.editMyProduct(params), callback)
    }

    /**
     * 选择分类-获取厂家
     *
     * @param classifyId 分类id
     * */
    override fun getFactoryByClassify(classifyId: String, callback: DataCallback<ReturnDataBean<ProductMillBean>>) {
        val params = FrameRequest.getAppParams()
        params["classifyId"] = classifyId

        BaseApi.dispose(mApi.getFactoryByClassify(params), callback)
    }

    /**
     * 创建修改私有产品-下一步
     *
     * @param id 产品id，修改的时候不为空，新增时可为空
     * @param classifyId 分类id
     * */
    override fun createProductNext(id: String?, classifyId: String, callback: DataCallback<ReturnDataBean<ProductAttrBean>>) {
        val params = FrameRequest.getAppParams()
        params["id"] = id ?: ""
        params["classifyId"] = classifyId

        BaseApi.dispose(mApi.createProductNext(params), callback)
    }

    /**
     * 新增或修改私有产品
     *
     * @param bean 提交的数据
     * */
    override fun add(bean: CreateProductSubmitBean, callback: DataCallback<EmptyReturnBean>) {
        val params = FrameRequest.getAppParams()

        params["goods.id"] = bean.goodsId                   // 产品id，新建的时候传null或""或不传
        params["goods.barCode"] = bean.barCode              // 产品条码,最大长度20字符
        params["goods.classifyId"] = bean.classifyId        // 产品分类
        params["goods.millId"] = bean.millId                // 产品厂家id
        params["info.id"] = bean.infoId                     // 产品明细id
        params["info.name"] = bean.name                     // 产品名称,最大长度30字符
        params["info.image"] = bean.image                   // 产品图片
        params["info.specification"] = bean.specification   // 规格,最大长度20字符
        params["info.unit"] = bean.unit                     // 单位
        params["info.registerCard"] = bean.registerCard     // 登记证号,最大长度20字符
        params["info.startTime"] = bean.startTime           // 登记证有效期开始
        params["info.endTime"] = bean.endTime               // 登记证有效期结束
        params["info.licenseCard"] = bean.licenseCard       // 生产许可证
        params["info.standardCard"] = bean.standardCard     // 产品标准证号,最大长度20字符
        params["info.details"] = bean.details               // 产品明细,最大2000字符
        params["info.remark"] = bean.remark                 // 备注
        params["price"] = bean.price                        // 零售价
        params["attrId"] = bean.attrId                      // 属性，多个属性名用,号隔开
        params["attrValues"] = bean.attrValues              // 属性对应的属性值,多条数据之间用";@;"分隔，每条数据每列之间用"# @ #"隔开

        BaseApi.dispose(mApi.add(params), callback)
    }

    /**
     * 溯源记录列表
     *
     * @param keyword 溯源码/产品名称/生产厂家
     * @param pageNo
     * @param pageSize
     * */
    override fun suyuanList(keyword: String?, pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<SuyuanRecordBean>>) {
        val params = FrameRequest.getAppParams()
        params["keyword"] = keyword ?: ""
        params["pageNo"] = pageNo
        params["pageSize"] = pageSize

        BaseApi.dispose(mApi.suyuanList(params), callback)
    }

    /**
     * 溯源记录详情
     *
     * @param traceabilityCode 溯源码
     * */
    override fun suyuanSearch(traceabilityCode: String, callback: DataCallback<SuyuanDetailsBean>) {
        val params = FrameRequest.getAppParams()
        params["traceabilityCode"] = traceabilityCode

        BaseApi.dispose(mApi.suyuanSearch(params), callback)
    }

    /**
     * 批次码搜索
     *
     * @param code 批次码
     * */
    override fun batchSearch(code: String, callback: DataCallback<BatchDetailsBean>) {
        val params = FrameRequest.getAppParams()
        params["code"] = code

        BaseApi.dispose(mApi.batchSearch(params), callback)
    }

    /**
     * 条形码搜索
     *
     * @param code 条形码
     * */
    override fun barCodeSearch(code: String, callback: DataCallback<BarCodeDetailsBean>) {
        val params = FrameRequest.getAppParams()
        params["code"] = code

        BaseApi.dispose(mApi.barCodeSearch(params), callback)
    }
}