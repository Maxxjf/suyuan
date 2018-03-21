package com.qcloud.suyuan.ui.goods.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.ValidWarnBean

/**
 * Description: 有效期告警
 * Author: gaobaiqiang
 * 2018/3/20 下午9:04.
 */
interface IValidWarnView: BaseView {
    /**替换数据 */
    fun replaceList(beans: List<ValidWarnBean>?, isNext: Boolean)

    /**添加数据 */
    fun addListAtEnd(beans: List<ValidWarnBean>?, isNext: Boolean)

    /**显示空布局 */
    fun showEmptyView(tip: String)

    /**隐藏空布局 */
    fun hideEmptyView()
}