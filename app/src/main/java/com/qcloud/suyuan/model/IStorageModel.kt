package com.qcloud.suyuan.model

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.*

/**
 * 类说明：库存有关
 * Author: Kuzan
 * Date: 2018/3/27 15:18.
 */
interface IStorageModel {
    /**
     * 有效期告警列表撤消入库接口
     * */
    fun outStorageInValidWarn(id: String, number: Int, callback: DataCallback<EmptyReturnBean>)

    /**
     * 搜索产品入库
     * */
    fun searchList(keyword: String, callback: DataCallback<ReturnDataBean<PurchaseProductBean>>)

    /**
     * 提交入库
     * */
    fun save(goodId: String, number: Int, price: Double, expDate: String, stopDate: String, supplierId: String?, callback: DataCallback<InStorageBean>)
    /**
     * 出库商品查询
     * */
    fun outStorageSearch(keyword: String, callback: DataCallback<OutStorageBean>)
}