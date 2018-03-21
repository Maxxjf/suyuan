package com.qcloud.suyuan.ui.goods.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.StockWarnBean

/**
 * Description: 库存警告
 * Author: gaobaiqiang
 * 2018/3/20 下午9:00.
 */
interface IStockWarnView: BaseView {
    /**替换数据 */
    fun replaceList(beans: List<StockWarnBean>?, isNext: Boolean)

    /**添加数据 */
    fun addListAtEnd(beans: List<StockWarnBean>?, isNext: Boolean)

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()
}