package com.qcloud.suyuan.ui.goods.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.ScanCodeBean

/**
 * 类型：IReturnedView
 * Author: iceberg
 * Date: 2018/3/19.
 * 退货
 */
interface IReturnedView :BaseView{
    /**替换数据 */
    fun replaceList(beans: List<ScanCodeBean.InfoListBean>?, isNext: Boolean)

    /**添加数据 */
    fun addListAtEnd(bean: ScanCodeBean.MerchandiseBean?, isNext: Boolean)

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()


    fun showSaleInfo(bean: ScanCodeBean.SaleSerialBean)
    fun loadDataSuccess(bean: ScanCodeBean)
    fun returnSuccess()
}