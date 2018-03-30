package com.qcloud.suyuan.ui.store.presenter

/**
 * 类型：ISupplierAddPresenter
 * Author: iceberg
 * Date: 2018/3/26.
 * 修改供应商
 */
interface ISupplierEditPresenter {
    /**
     * @param address	供应商地址
     * @param  classifyId	供应品类id字符串( , 隔开)
     * @param id	供应商id(不传为新增,传为修改)
     * @param name	供应商名称
     * @param phone	联系电话
     * @param  principal	联系人
     * @param remark	备注
     * 新增/修改供应商
     */
    fun editSupplier(address: String,id:String,  name: String, phone: String, principal: String, remark: String)
}