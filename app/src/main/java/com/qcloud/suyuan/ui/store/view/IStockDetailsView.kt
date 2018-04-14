package com.qcloud.suyuan.ui.store.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.InStorageRecordBean
import com.qcloud.suyuan.beans.ProductDetailsBean

/**
 * Description: 库存详情
 * Author: gaobaiqiang
 * 2018/3/24 下午2:33.
 */
interface IStockDetailsView: BaseView {
    fun onProductDetailsClick()

    fun refreshData(bean: ProductDetailsBean?)

    fun refreshInfo(bean: ProductDetailsBean)

    fun replaceList(beans: List<InStorageRecordBean>?)

    fun editWarnLineSuccess()

    fun editPriceSuccess()
}