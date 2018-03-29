package com.qcloud.suyuan.model

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.StoreBean
import com.qcloud.suyuan.beans.SupplierBean

/**
 * 类说明：门店有关
 * Author: Kuzan
 * Date: 2018/3/28 15:54.
 */
interface IStoreModel {
    /**门店信息*/
    fun getInfo(callback: DataCallback<StoreBean>)

    /** 供应商列表 */
    fun supplierList(callback: DataCallback<ReturnDataBean<SupplierBean>>)
}