package com.qcloud.suyuan.ui.store.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.CreateProductBean
import com.qcloud.suyuan.beans.ProductClassifyBean
import com.qcloud.suyuan.beans.ProductMillBean

/**
 * Description: 创建产品第一步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:50.
 */
interface ICreateProductIView: BaseView {
    fun refreshData(bean: CreateProductBean)

    /**刷新分类集合*/
    fun replaceClassify(list: List<ProductClassifyBean>)

    /**刷新生产厂家*/
    fun replaceMill(list: List<ProductMillBean>)

    /**条形码是否重复*/
    fun isBarCodeRepeat(isRepeat: Boolean, message: String)
}