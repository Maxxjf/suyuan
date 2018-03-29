package com.qcloud.suyuan.ui.goods.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.ProductBean

/**
 * Description: 修改售价
 * Author: gaobaiqiang
 * 2018/3/15 上午12:35.
 */
interface IModifyPriceView: BaseView {
    /**替换数据 */
    fun replaceList(beans: List<ProductBean>?, isNext: Boolean)

    /**添加数据 */
    fun addListAtEnd(beans: List<ProductBean>?, isNext: Boolean)

    /**显示空布局 */
    fun showEmptyView()

    /**隐藏空布局 */
    fun hideEmptyView()

    /**修改价格成功*/
    fun modifyPriceSuccess()

    /**修改库存警告线成功*/
    fun modifyStockWarnSuccess()
}