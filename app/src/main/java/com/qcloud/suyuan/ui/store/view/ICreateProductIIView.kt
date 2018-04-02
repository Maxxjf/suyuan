package com.qcloud.suyuan.ui.store.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.ProductAttrBean

/**
 * Description: 创建产品第二步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:52.
 */
interface ICreateProductIIView: BaseView {
    fun replaceList(beans: List<ProductAttrBean>?)

    fun createSuccess()
}