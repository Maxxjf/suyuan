package com.qcloud.suyuan.ui.order.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.CodeBean
import com.qcloud.suyuan.beans.SaleInfoBean
import com.qcloud.suyuan.beans.SaleListBean

/**
 * Description: 销售流水
 * Author: gaobaiqiang
 * 2018/3/15 上午12:40.
 */
interface ISellingWaterView:BaseView {
    /**替换数据 */

    /**添加数据 */
    fun addListAtEnd(beans: CodeBean?, isNext: Boolean)

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()
    /**替换数据 */
    fun replaceSaleInfoList(beans: List<SaleInfoBean.ListBean>?, isNext: Boolean)
    /**替换数据 */
    fun replaceSaleList(beans: List<SaleListBean>?, isNext: Boolean)
    /**加载数据 */
    fun loadData()
}