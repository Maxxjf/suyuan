package com.qcloud.suyuan.ui.store.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.SupplierBean

/**
 * Description: 我的供应商
 * Author: gaobaiqiang
 * 2018/3/20 上午9:02.
 */
interface IMySupplierView :BaseView{
    //替换列表
    fun replaceList(beans: List<SupplierBean>?, isNext: Boolean)
    //加列表
    fun addListAtEnd(bean: SupplierBean?, isNext: Boolean)
    //显示空布局
    fun showEmptyView(tip: String)
    //隐藏空布局
    fun hideEmptyView()

    fun hasEdit()
    fun loadData()
}