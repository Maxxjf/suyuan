package com.qcloud.suyuan.ui.goods.view

import com.qcloud.suyuan.beans.SellersBean

/**
 * Description: 卖货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:20.
 */
interface ISellersView{
    /**替换数据 */
    fun replaceList(beans: List<SellersBean>?)

    /**添加数据 */
    fun addBeanAtEnd(bean: SellersBean)

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()
}