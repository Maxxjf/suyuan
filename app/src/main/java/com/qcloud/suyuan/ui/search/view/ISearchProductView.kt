package com.qcloud.suyuan.ui.search.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.BarCodeDetailsBean

/**
 * Description: 搜索产品条形码
 * Author: gaobaiqiang
 * 2018/4/1 下午11:13.
 */
interface ISearchProductView: BaseView {
    fun replaceData(bean: BarCodeDetailsBean)

    fun showEmptyView(tip: String)

    fun hideEmptyView()
}