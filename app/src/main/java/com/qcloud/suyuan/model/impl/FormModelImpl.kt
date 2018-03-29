package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.FrameRequest
import com.qcloud.suyuan.beans.MainFormBean
import com.qcloud.suyuan.beans.SaleFormBean
import com.qcloud.suyuan.model.IFormModel
import com.qcloud.suyuan.net.IFormApi

/**
 * Description: 报表有关
 * Author: gaobaiqiang
 * 2018/3/19 下午7:31.
 */
class FormModelImpl: IFormModel {

    private val mApi: IFormApi = FrameRequest.instance.createRequest(IFormApi::class.java)

    /**
     * 获取首页报表数据
     * */
    override fun getMainForm(callback: DataCallback<MainFormBean>) {
        val params = FrameRequest.getAppParams()

        BaseApi.dispose(mApi.getMainForm(params), callback)
    }

    /**
     * 获取销售报表数据
     * */
    override fun getSaleForm(startTime:String,endTime:String,callback: DataCallback<SaleFormBean>) {
        val params = FrameRequest.getAppParams()
        params.put("startTime",startTime)
        params.put("endTime",endTime)
        BaseApi.dispose(mApi.getSaleForm(params), callback)
    }
}