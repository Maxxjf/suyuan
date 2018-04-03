package com.qcloud.suyuan.ui.search.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.ProductBean

/**
 * Description: 搜索批次条形码
 * Author: gaobaiqiang
 * 2018/4/1 下午11:01.
 */
interface ISearchBatchView: BaseView {
    /**添加产品到列表*/
    fun replaceData(bean: ProductBean)

    fun showEmptyView(tip: String)

    fun hideEmptyView()
}