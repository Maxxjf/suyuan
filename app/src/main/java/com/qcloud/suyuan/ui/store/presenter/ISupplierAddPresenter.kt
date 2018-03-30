package com.qcloud.suyuan.ui.store.presenter

/**
 * 类型：ISupplierAddPresenter
 * Author: iceberg
 * Date: 2018/3/26.
 * TODO:
 */
interface ISupplierAddPresenter{
    /**新增供应商**/
    fun addSupplier(address: String,  name: String, phone: String, principal: String, remark: String)
}