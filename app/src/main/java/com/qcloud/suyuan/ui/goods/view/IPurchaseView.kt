package com.qcloud.suyuan.ui.goods.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.ProductBean

/**
 * Description: 进货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:31.
 */
interface IPurchaseView: BaseView {
    /**添加产品到列表*/
    fun replaceList(beans: List<ProductBean>?)

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()
}