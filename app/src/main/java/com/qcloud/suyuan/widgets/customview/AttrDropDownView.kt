package com.qcloud.suyuan.widgets.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.qcloud.qclib.base.BaseLinearLayout
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ProductAttrBean
import com.qcloud.suyuan.beans.ProductAttrValueBean
import com.qcloud.suyuan.widgets.pop.DropDownPop
import kotlinx.android.synthetic.main.layout_of_product_attr_drop_down.view.*

/**
 * Description: 产品属性值下拉框
 * Author: gaobaiqiang
 * 2018/3/30 下午4:44.
 */
class AttrDropDownView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : BaseLinearLayout(context, attrs, defStyleAttr) {

    private var currBean: ProductAttrBean? = null
    private var dropDownPop: DropDownPop? = null

    override val viewId: Int
        get() = R.layout.layout_of_product_attr_drop_down

    override fun initViewAndData() {

    }

    /**
    * 初始化下拉弹窗
    * */
    private fun initDropDown(list: List<ProductAttrValueBean>) {
        btn_select_value.post {
            val width = btn_select_value.width
            dropDownPop = DropDownPop(mContext, list, width)

            dropDownPop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: Any?) {
                    if (value != null) {
                        tv_value.text = value.toString()
                        currBean?.value = value.toString()
                    }
                }
            }
        }

        btn_select_value.setOnClickListener {
            dropDownPop?.showAsDropDown(btn_select_value)
        }
    }

    /**
     * 初始化数据
     * */
    private fun initData() {
        if (currBean != null) {
            val attrName = currBean!!.attributeName
            if (attrName != null) {
                with(attrName) {
                    tv_required.visibility = if (isCrux == 1) View.VISIBLE else View.GONE
                    tv_attr_tag.text = name
                }
            }
            val attrValues = currBean!!.attributeValues
            if (attrValues != null) {
                initDropDown(attrValues)
            }
        }
    }

    fun refreshData(bean: ProductAttrBean) {
        currBean = bean
        initData()
    }
}