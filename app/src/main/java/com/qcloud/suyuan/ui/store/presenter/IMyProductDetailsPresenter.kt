package com.qcloud.suyuan.ui.store.presenter

/**
 * Description: 我的产品详情
 * Author: gaobaiqiang
 * 2018/4/1 下午5:15.
 */
interface IMyProductDetailsPresenter {
    /**获取商品详情*/
    fun loadData(id: String)
}