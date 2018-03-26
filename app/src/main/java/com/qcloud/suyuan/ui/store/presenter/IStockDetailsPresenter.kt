package com.qcloud.suyuan.ui.store.presenter

import com.qcloud.qclib.base.BtnClickPresenter

/**
 * Description: 库存详情
 * Author: gaobaiqiang
 * 2018/3/24 下午2:33.
 */
interface IStockDetailsPresenter: BtnClickPresenter {
    /**获取商品详情*/
    fun loadData(id: String)

    /**调整警告线*/
    fun editWarnLine(id: String, cordon: Int)

    /**调整价格*/
    fun editPrice(id: String, price: Double)
}