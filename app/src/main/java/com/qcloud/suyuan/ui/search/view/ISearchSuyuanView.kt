package com.qcloud.suyuan.ui.search.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.SuyuanDetailsBean

/**
 * Description: 搜索溯源码
 * Author: gaobaiqiang
 * 2018/4/1 下午10:38.
 */
interface ISearchSuyuanView: BaseView {

    fun replaceData(bean: SuyuanDetailsBean)

    fun showEmptyView(tip: String)

    fun hideEmptyView()
}