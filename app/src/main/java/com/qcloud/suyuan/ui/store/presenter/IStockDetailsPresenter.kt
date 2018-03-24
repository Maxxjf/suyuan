package com.qcloud.suyuan.ui.store.presenter

import com.qcloud.qclib.base.BtnClickPresenter

/**
 * Description: 库存详情
 * Author: gaobaiqiang
 * 2018/3/24 下午2:33.
 */
interface IStockDetailsPresenter: BtnClickPresenter {
    fun loadData()
}