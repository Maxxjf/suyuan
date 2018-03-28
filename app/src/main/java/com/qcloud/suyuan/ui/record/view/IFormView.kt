package com.qcloud.suyuan.ui.record.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.SaleFormBean

/**
 * Description: 报表
 * Author: gaobaiqiang
 * 2018/3/20 上午8:58.
 */
interface IFormView:BaseView {
    fun showForm(bean: SaleFormBean)
}