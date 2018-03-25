package com.qcloud.suyuan.ui.record.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.CreditInfoBean
import com.qcloud.suyuan.beans.CreditListBean

/**
 * Description: 赊账记录
 * Author: gaobaiqiang
 * 2018/3/15 上午12:54.
 */
interface ICreditRecordView :BaseView{
    /**隐藏空布局*/
    fun hideEmptyView()
    /**显示空布局*/
    fun showEmptyView(tip: String)
    /**添加赊账详细*/
    fun addCreditInfoAtEnd(bean: CreditInfoBean?, isNext: Boolean)
    /**添加赊账列表*/
    fun addCreditListAtEnd(bean: CreditListBean.ListBean?, isNext: Boolean)
    /**重新加载赊账详细*/
    fun replaceCreditInfo(beans: List<CreditInfoBean>?, isNext: Boolean)
    /**重新加载赊账列表*/
    fun replaceCreditList(beans: List<CreditListBean.ListBean>?, isNext: Boolean)
    /**得赊账详情*/
    fun getCreditInfo()
    /**的赊账列表*/
    fun getCreditList()
}