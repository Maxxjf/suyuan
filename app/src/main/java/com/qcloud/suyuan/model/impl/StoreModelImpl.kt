package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.FrameRequest
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
     * 获取供应商列表
     *
     * @param callback
     * */
    override fun supplierList(callback: DataCallback<ReturnDataBean<SupplierBean>>) {
        val params = FrameRequest.getAppParams()

        BaseApi.dispose(mApi.supplierList(params), callback)
    }
}