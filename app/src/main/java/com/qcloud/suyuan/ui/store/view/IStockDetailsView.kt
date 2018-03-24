package com.qcloud.suyuan.ui.store.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.InStorageRecordBean

/**
 * Description: 库存详情
 * Author: gaobaiqiang
 * 2018/3/24 下午2:33.
 */
interface IStockDetailsView: BaseView {
    fun onProductDetailsClick()

    fun onProductPriceClick()

    fun onAdjustWarnClick()

    fun replaceList(beans: List<InStorageRecordBean>?)
}