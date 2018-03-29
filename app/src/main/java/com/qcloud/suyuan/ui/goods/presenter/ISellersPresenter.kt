package com.qcloud.suyuan.ui.goods.presenter

import com.qcloud.qclib.base.BtnClickPresenter
import com.qcloud.suyuan.beans.IDBean

/**
 * Description: 卖货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:21.
 */
interface ISellersPresenter: BtnClickPresenter {
    /**加载商品*/
    fun loadData(keyword: String)

    /**结算*/
    fun saleSettlement(list: String, idInfo: IDBean, saleDiscount: Double,
                       saleRealPay: Double, salePayMethod: Int, salePurpose: String?, saleRemark: String?)
}