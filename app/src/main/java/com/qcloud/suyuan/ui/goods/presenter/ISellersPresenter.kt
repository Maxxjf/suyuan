package com.qcloud.suyuan.ui.goods.presenter

import com.qcloud.qclib.base.BtnClickPresenter

/**
 * Description: 卖货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:21.
 */
interface ISellersPresenter: BtnClickPresenter {
    fun loadData(keyword: String)
}