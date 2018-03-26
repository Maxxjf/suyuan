package com.qcloud.suyuan.ui.goods.view

import com.qcloud.suyuan.beans.IDVerifyResultBean
import com.qcloud.suyuan.beans.SellersBean

/**
 * Description: 卖货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:20.
 */
interface ISellersView {
    fun onSettlementClick()

    fun onInputPurchaserClick()

    /**替换数据 */
    fun replaceList(beans: List<SellersBean>?)

    /**添加数据 */
    fun addBeanAtEnd(bean: SellersBean)

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()

    /**处理身份识别数据*/
    fun disposeRfidReceivedData(bean: IDVerifyResultBean)
}