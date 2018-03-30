package com.qcloud.suyuan.ui.store.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.SupplierBean

/**
 * 类型：ISupplierDetailView
 * Author: iceberg
 * Date: 2018/3/26.
 * TODO:
 */
interface ISupplierDetailView :BaseView {
    fun replaceInfo(bean: SupplierBean)
}