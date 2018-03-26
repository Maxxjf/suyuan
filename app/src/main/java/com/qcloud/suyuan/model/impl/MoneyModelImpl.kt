package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.CreditInfoBean
import com.qcloud.suyuan.beans.CreditListBean
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.model.IMoneyModel
import com.qcloud.suyuan.net.IMoneyApi

/**
 * 类说明：金钱有关的
 * Author: iceberg
 * Date: 2018-3-25.
 */
class MoneyModelImpl : IMoneyModel {



    /**
     *得到赊账列表
     * @param pageNo 页数
     * @param pageSize 一页的数量
     */
    override fun getCreditList(keyword:String,pageNo:Int,pageSize:Int,callback: DataCallback<CreditListBean>){
        val params=OkGoRequest.getAppParams()
        params.put("keyword",keyword)
        params.put("pageNo",pageNo)
        params.put("pageSize",pageSize)
        BaseApi.dispose(IMoneyApi.getCreditList(params),callback)
    }
    /**
     *得到赊账详细单
     * @param purchaserId 赊账人的id
     * @param pageNo 页数
     * @param pageSize 一页的数量
     */
    override fun getCreditInfo(purchaserId:String,pageNo:Int,pageSize:Int,callback: DataCallback<ReturnDataBean<CreditInfoBean>>){
        val params=OkGoRequest.getAppParams()
        params.put("pageNo",pageNo)
        params.put("pageSize",pageSize)
        params.put("purchaserId",purchaserId)
        BaseApi.dispose(IMoneyApi.getCreditInfo(params),callback)
    }
    /**
     *还款
     * @param id 赊账单的id
     * @param money 还的金额
     */
    override fun repayment(id:String,money:Double,callback: DataCallback<EmptyReturnBean>){
        val params=OkGoRequest.getAppParams()
        params.put("id",id)
        params.put("money",money)
        BaseApi.dispose(IMoneyApi.repayment(params),callback)
    }
}