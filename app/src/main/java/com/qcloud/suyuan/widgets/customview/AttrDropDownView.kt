package com.qcloud.suyuan.widgets.customview

import android.content.Context
import android.util.AttributeSet
import com.qcloud.qclib.base.BaseLinearLayout
import com.qcloud.suyuan.R

/**
 * Description: 产品属性值下拉框
 * Author: gaobaiqiang
 * 2018/3/30 下午4:44.
 */
class AttrDropDownView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : BaseLinearLayout(context, attrs, defStyleAttr) {
    override val viewId: Int
        get() = R.layout.layout_of_product_attr_drop_down

    override fun initViewAndData() {

    }
}