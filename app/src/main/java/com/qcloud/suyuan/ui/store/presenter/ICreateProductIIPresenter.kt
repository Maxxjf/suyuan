package com.qcloud.suyuan.ui.store.presenter

import com.qcloud.suyuan.beans.CreateProductSubmitBean

/**
 * Description: 创建产品第二步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:53.
 */
interface ICreateProductIIPresenter {
    /**创建产品下一步*/
    fun createProductNext(id: String?, classifyId: String)

    /**创建产品*/
    fun add(bean: CreateProductSubmitBean)
}