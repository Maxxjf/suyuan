package com.qcloud.suyuan.ui.store.presenter

/**
 * 类说明：我的产品
 * Author: Kuzan
 * Date: 2018/3/30 11:01.
 */
interface IMyProductPresenter {
    /**获取产品分类数据*/
    fun loadClassify()

    /**获取产品数据*/
    fun loadData(pageNo: Int, classifyId: String?, keyword: String?)
}