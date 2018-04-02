package com.qcloud.suyuan.model

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.EmptyReturnBean
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
    fun supplierList(keyword:String,callback: DataCallback<ReturnDataBean<SupplierBean>>)

    /** 新增或修改供应商 */
    fun supplierSaveOrUpdate(address: String, classifyId: String, id: String, name: String, phone: String, principal: String, remark: String, callback: DataCallback<EmptyReturnBean>)

    /** 修改门店密码*/
    fun editStorePassword(oldPwd: String, newPwd: String, againNewPwd: String, callback: DataCallback<EmptyReturnBean>)

    /** 修改门店信息*/
    fun editStoreInfo(address: String, phone: String, shopkeeperName: String, businessLicenseId: String, businessLicenseImage: String, businessCertificateId: String, businessCertificateImage: String, callback: DataCallback<EmptyReturnBean>)
}