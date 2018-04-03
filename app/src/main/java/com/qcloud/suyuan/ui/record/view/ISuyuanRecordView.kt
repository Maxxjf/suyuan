package com.qcloud.suyuan.ui.record.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.SuyuanRecordBean

/**
 * Description: 溯源记录
 * Author: gaobaiqiang
 * 2018/3/20 上午8:52.
 */
interface ISuyuanRecordView: BaseView {
    /**替换数据 */
    fun replaceList(beans: List<SuyuanRecordBean>?, isNext: Boolean)

    /**添加数据 */
    fun addListAtEnd(beans: List<SuyuanRecordBean>?, isNext: Boolean)

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()
}