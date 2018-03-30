package com.qcloud.suyuan.ui.store.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.SupplierBean

/**
 * 类型：ISupplierAddView
 * Author: iceberg
 * Date: 2018/3/26.
 * 修改供应商
 */
interface ISupplierEditView :BaseView {
    fun editClick()
    fun clearEdit()
    fun replaceInfo(bean: SupplierBean)
}