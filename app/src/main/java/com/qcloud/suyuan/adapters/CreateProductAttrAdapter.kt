package com.qcloud.suyuan.adapters

import android.content.Context
import android.view.View
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ProductAttrBean
import com.qcloud.suyuan.widgets.customview.AttrDropDownView
import com.qcloud.suyuan.widgets.customview.AttrFormView
import com.qcloud.suyuan.widgets.customview.AttrInputView

/**
 * Description: 创建产品属性
 * Author: gaobaiqiang
 * 2018/4/1 上午11:54.
 */
class CreateProductAttrAdapter(mContext: Context) : CommonRecyclerAdapter<ProductAttrBean>(mContext) {
    override val viewId: Int
        get() = R.layout.item_of_create_product

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]

        val dropDownView = holder.get<AttrDropDownView>(R.id.view_of_drop_down)
        val inputView = holder.get<AttrInputView>(R.id.view_of_input)
        val formView = holder.get<AttrFormView>(R.id.view_of_form)

        val attrName = bean.attributeName
        if (attrName != null) {
            with(attrName) {
                when (type) {
                    1 -> {
                        dropDownView.visibility = View.VISIBLE
                        inputView.visibility = View.GONE
                        formView.visibility = View.GONE
                    }
                    2 -> {
                        dropDownView.visibility = View.GONE
                        inputView.visibility = View.VISIBLE
                        formView.visibility = View.GONE
                    }
                    3 -> {
                        dropDownView.visibility = View.GONE
                        inputView.visibility = View.GONE
                        formView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}