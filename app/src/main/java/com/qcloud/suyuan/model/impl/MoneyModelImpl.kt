package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.FrameRequest
import com.qcloud.suyuan.beans.*
import com.qcloud.suyuan.model.IMoneyModel
import com.qcloud.suyuan.net.IMoneyApi

/**
 * 类说明：金钱有关的
 * Author: iceberg
 * Date: 2018-3-25.
 */
class MoneyModelImpl : IMoneyModel {

    private val mApi: IMoneyApi = FrameRequest.instance.createRequest(IMoneyApi::class.java)

    /**
     *得到赊账列表
     * @param pageNo 页数
     * @param pageSize 一页的数量
     */
    override fun getCreditList(keyword:String,pageNo:Int,pageSize:Int,callback: DataCallback<CreditListBean>){
        val params = FrameRequest.getAppParams()
        params["keyword"] = keyword
        params["pageNo"] = pageNo
        params["pageSize"] = pageSize

        BaseApi.dispose(mApi.getCreditList(params), callback)
    }

    /**
     *得到赊账详细单
     * @param purchaserId 赊账人的id
     * @param pageNo 页数
     * @param pageSize 一页的数量
     */
    override fun getCreditInfo(purchaserId:String,pageNo:Int,pageSize:Int,callback: DataCallback<ReturnDataBean<CreditInfoBean>>){
        val params = FrameRequest.getAppParams()
        params["pageNo"] = pageNo
        params["pageSize"] = pageSize
        params["purchaserId"] = purchaserId

        BaseApi.dispose(mApi.getCreditInfo(params), callback)
    }

    /**
     *还款
     * @param id 赊账单的id
     * @param money 还的金额
     */
    override fun repayment(id:String,money:Double,callback: DataCallback<EmptyReturnBean>){
        val params = FrameRequest.getAppParams()
        params["id"] = id
        params["money"] = money

        BaseApi.dispose(mApi.repayment(params), callback)
    }


    /**
     *销售列表
     * @param keyword
     */
    override fun getSaleList(dayTime:String,keyword:String,callback: DataCallback<ReturnDataBean<SaleListBean>>){
        val params = FrameRequest.getAppParams()
        params["keyword"] = keyword
        params["dayTime"] = dayTime

        BaseApi.dispose(mApi.getSaleList(params), callback)
    }
    /**
     *销售详情
     * @param id
     */
    override fun getSaleInfo(id:String, callback: DataCallback<SaleInfoBean>){
        val params = FrameRequest.getAppParams()
        params["id"] = id

        BaseApi.dispose(mApi.getSaleInfo(params), callback)
    }
}