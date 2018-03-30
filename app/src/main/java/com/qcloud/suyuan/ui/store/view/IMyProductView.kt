package com.qcloud.suyuan.ui.store.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.beans.ProductClassifyBean

/**
 * 类说明：我的产品
 * Author: Kuzan
 * Date: 2018/3/30 10:59.
 */
interface IMyProductView: BaseView {
    /**产品分类*/
    fun replaceClassifyList(list: List<ProductClassifyBean>?)

    /**替换数据 */
    fun replaceList(beans: List<ProductBean>?, isNext: Boolean)

    /**添加数据 */
    fun addListAtEnd(beans: List<ProductBean>?, isNext: Boolean)

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()
}