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
class StoreModelImpl : IStoreModel {

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
    override fun supplierList(keyword: String, callback: DataCallback<ReturnDataBean<SupplierBean>>) {
        val params = FrameRequest.getAppParams()
        params.put("keyword", keyword)
        BaseApi.dispose(mApi.supplierList(params), callback)
    }

    /**
     * 新增/修改供应商
     * @param address    供应商地址
     * @param  classifyId    供应品类id字符串( , 隔开)
     * @param id    供应商id(不传为新增,传为修改)
     * @param name    供应商名称
     * @param phone    联系电话
     * @param  principal    联系人
     * @param remark    备注
     */
    override fun supplierSaveOrUpdate(address: String, classifyId: String, id: String, name: String, phone: String, principal: String, remark: String, callback: DataCallback<EmptyReturnBean>) {
        val params = FrameRequest.getAppParams()
        params.put("address", address)
        params.put("classifyId", classifyId)
        params.put("id", id)
        params.put("name", name)
        params.put("phone", phone)
        params.put("principal", principal)
        params.put("remark", remark)
        BaseApi.dispose(mApi.supplierSaveOrUpdate(params), callback)
    }

    /**
     *  修改门店信息
     * @param address    门店地址
     * @param phone    联系电话
     *  @param shopkeeperName    店主
     * @param businessLicenseId 营业执照id
     * @param businessLicenseImage 营业执照图片
     *  @param businessCertificateId 经营许可证id
     *  @param businessCertificateImage 经营许可证图片
     */
    override fun editStoreInfo(address: String, phone: String, shopkeeperName: String, businessLicenseId: String, businessLicenseImage: String, businessCertificateId: String, businessCertificateImage: String, callback: DataCallback<EmptyReturnBean>) {
        val params = FrameRequest.getAppParams()
        params.put("address", address)
        params.put("phone", phone)
        params.put("shopkeeperName", shopkeeperName)
        BaseApi.dispose(mApi.editInfo(params), callback)
    }

    /**
     * 修改门店密码
     * @param oldPwd    原密码
     * @param newPwd    新密码
     *  @param againNewPwd    确认新密码
     */
    override fun editStorePassword(oldPwd: String, newPwd: String, againNewPwd: String, callback: DataCallback<EmptyReturnBean>) {
        val params = FrameRequest.getAppParams()
        params.put("oldPwd", oldPwd)
        params.put("newPwd", newPwd)
        params.put("againNewPwd", againNewPwd)
        BaseApi.dispose(mApi.editPassword(params), callback)
    }


}