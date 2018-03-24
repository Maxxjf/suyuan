package com.qcloud.suyuan.ui.order.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.CodeBean

/**
 * Description: 销售流水
 * Author: gaobaiqiang
 * 2018/3/15 上午12:40.
 */
interface ISellingWaterView:BaseView {
    /**替换数据 */
    fun replaceList(beans: List<CodeBean>?, isNext: Boolean)

    /**添加数据 */
    fun addListAtEnd(beans: CodeBean?, isNext: Boolean)

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()
}