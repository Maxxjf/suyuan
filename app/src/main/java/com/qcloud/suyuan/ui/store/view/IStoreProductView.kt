package com.qcloud.suyuan.ui.store.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.beans.ProductClassifyBean

/**
 * Description: 门店产品
 * Author: gaobaiqiang
 * 2018/3/15 上午12:49.
 */
interface IStoreProductView: BaseView {
    /**产品分类*/
    fun replaceClassifyList(list: List<ProductClassifyBean>?)

    /**替换数据 */
    fun replaceList(beans: List<ProductBean>?, isNext: Boolean, total: Int)

    /**添加数据 */
    fun addListAtEnd(beans: List<ProductBean>?, isNext: Boolean)

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()

}