package com.qcloud.suyuan.ui.goods.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.IDVerifyResultBean
import com.qcloud.suyuan.beans.SaleProductBean
import com.qcloud.suyuan.beans.SettlementResBean

/**
 * Description: 卖货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:20.
 */
interface ISellersView: BaseView {
    fun onSettlementClick()

    fun onInputPurchaserClick()

    fun onMobileClick()

    fun onRemarkClick()

    /**替换数据 */
    fun replaceList(beans: List<SaleProductBean>?)

    /**添加数据 */
    fun addBeanAtEnd(bean: SaleProductBean)

    /**获取商品出错*/
    fun searchFailure()

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()

    /**结算成功返回*/
    fun settlementSuccess(bean: SettlementResBean?)

    /**处理身份识别数据*/
    fun disposeRfidReceivedData(bean: IDVerifyResultBean)
}