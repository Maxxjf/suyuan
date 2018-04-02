package com.qcloud.suyuan.ui.store.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.ProductDetailsBean

/**
 * Description: 我的产品详情
 * Author: gaobaiqiang
 * 2018/4/1 下午5:14.
 */
interface IMyProductDetailsView: BaseView {
    fun refreshData(bean: ProductDetailsBean)
}