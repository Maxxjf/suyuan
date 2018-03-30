package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.FrameRequest
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.StoreBean
import com.qcloud.suyuan.beans.SupplierBean
import com.qcloud.suyuan.model.IStoreModel
import com.qcloud.suyuan.net.IStoreApi

/**
 * 类说明：门店有关
 * Author: Kuzan
 * Date: 2018/3/28 15:54.
 */
class StoreModelImpl: IStoreModel {

    private val mApi: IStoreApi = FrameRequest.instance.createRequest(IStoreApi::class.java)

    /**
     * 获取门店信息
     *
     * @time 2018/3/29 17:01
     */
    override fun getInfo(callback: DataCallback<StoreBean>) {
        val params = FrameRequest.getAppParams()

        BaseApi.dispose(mApi.getInfo(params), callback)
    }

    /**
     * 获取供应商列表
     *
     * @param callback
     * */
    override fun supplierList(keyword:String,callback: DataCallback<ReturnDataBean<SupplierBean>>) {
        val params = FrameRequest.getAppParams()

        BaseApi.dispose(mApi.supplierList(params), callback)
    }

    /**
     * 新增/修改供应商
     * @param address	供应商地址
     * @param  classifyId	供应品类id字符串( , 隔开)
     * @param id	供应商id(不传为新增,传为修改)
     * @param name	供应商名称
     * @param phone	联系电话
     * @param  principal	联系人
     * @param remark	备注
     */
    override fun supplierSaveOrUpdate(address:String,classifyId:String,id:String,name:String,phone:String,principal:String,remark:String,callback: DataCallback<EmptyReturnBean>) {
        val params = FrameRequest.getAppParams()
        params.put("address",address)
        params.put("classifyId",classifyId)
        params.put("id",id)
        params.put("name",name)
        params.put("phone",phone)
        params.put("principal",principal)
        params.put("remark",remark)
        BaseApi.dispose(mApi.supplierSaveOrUpdate(params), callback)
    }
}