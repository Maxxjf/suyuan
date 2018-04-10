package com.qcloud.suyuan.ui.goods.presenter

import com.qcloud.suyuan.beans.ScanCodeBean

/**
 * 类型：IReturnedPresenter
 * Author: iceberg
 * Date: 2018/3/19.
 * 退货
 */
interface IReturnedPresenter {
    /**
     * 得到退货列表信息
     */
    fun loadData(code:String,saleId:String)

    fun salesReturn(money: String, merchandiseBean: List<ScanCodeBean.MerchandiseBean>)
}