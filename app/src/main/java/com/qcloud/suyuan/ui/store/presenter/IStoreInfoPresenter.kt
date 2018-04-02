package com.qcloud.suyuan.ui.store.presenter

/**
 * Description: 门店信息
 * Author: gaobaiqiang
 * 2018/3/20 上午9:04.
 */
interface IStoreInfoPresenter {
    fun getInfo()
    fun editPassword(oldPwd: String, newPwd: String, againNewPwd: String)
    fun editInfo(address: String, phone: String, shopkeeperName: String, businessLicenseId: String, businessLicenseImage: String, businessCertificateId: String, businessCertificateImage: String)
}