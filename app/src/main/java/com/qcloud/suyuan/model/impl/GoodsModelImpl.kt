package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.StockWarnBean
import com.qcloud.suyuan.beans.ValidWarnBean
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.net.IGoodsApi

/**
 * Description: 货物有关
 * Author: gaobaiqiang
 * 2018/3/21 上午9:05.
 */
class GoodsModelImpl: IGoodsModel {
    /**
     * 获取库存告警列表
     * */
    override fun getStockWarnList(pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<StockWarnBean>>) {
        val params = OkGoRequest.getAppParams()
        params.put("pageNo", pageNo)
        params.put("pageSize", pageSize)

        BaseApi.dispose(IGoodsApi.getStockWarnList(params), callback)
    }

    /**
     * 获取有效期告警列表
     * */
    override fun getValidWarnList(pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<ValidWarnBean>>) {
        val params = OkGoRequest.getAppParams()
        params.put("pageNo", pageNo)
        params.put("pageSize", pageSize)

        BaseApi.dispose(IGoodsApi.getValidWarnList(params), callback)
    }
}