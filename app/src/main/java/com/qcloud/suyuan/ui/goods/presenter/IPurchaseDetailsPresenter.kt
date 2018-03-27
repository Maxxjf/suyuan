package com.qcloud.suyuan.ui.goods.presenter

import com.qcloud.qclib.base.BtnClickPresenter

/**
 * Description: 确认入库
 * Author: gaobaiqiang
 * 2018/3/23 下午9:26.
 */
interface IPurchaseDetailsPresenter: BtnClickPresenter {
    fun save(goodId: String, number: Int, price: Double, expDate: String, stopDate: String, supplierId: String?)
}