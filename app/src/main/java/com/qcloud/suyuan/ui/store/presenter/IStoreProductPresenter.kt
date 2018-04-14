package com.qcloud.suyuan.ui.store.presenter

/**
 * Description: 门店产品
 * Author: gaobaiqiang
 * 2018/3/15 上午12:49.
 */
interface IStoreProductPresenter {
    /**获取产品分类数据*/
    fun loadClassify()

    /**获取产品数据*/
    fun loadData(pageNo: Int, classifyId: String?, platformKey: Int, keyword: String?)
}