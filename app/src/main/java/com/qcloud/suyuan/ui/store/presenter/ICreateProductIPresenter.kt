package com.qcloud.suyuan.ui.store.presenter

/**
 * Description: 创建产品第一步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:52.
 */
interface ICreateProductIPresenter {
    fun loadProduct(id: String?)

    fun loadFactory(classifyId: String)
}