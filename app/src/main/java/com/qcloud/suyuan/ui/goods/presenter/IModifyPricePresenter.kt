package com.qcloud.suyuan.ui.goods.presenter

/**
 * Description: 修改售价
 * Author: gaobaiqiang
 * 2018/3/15 上午12:36.
 */
interface IModifyPricePresenter {
    fun loadData(pageNo: Int, classifyId: String?, keyword: String?)

    /**调整警告线*/
    fun modifyWarnLine(id: String, cordon: Int)

    /**调整价格*/
    fun modifyPrice(id: String, price: Double)
}