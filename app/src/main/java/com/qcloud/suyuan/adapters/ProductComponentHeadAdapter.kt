package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R

/**
 * 类说明：产品属性表头
 * Author: Kuzan
 * Date: 2018/4/2 16:27.
 */
class ProductComponentHeadAdapter(mContext: Context, private val columns: Int, private val parentWidth: Int) : CommonRecyclerAdapter<String>(mContext) {
    override val viewId: Int
        get() = R.layout.item_of_product_component_head

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val tvValue = holder.get<TextView>(R.id.tv_value)
        val layoutParams = tvValue.layoutParams
        layoutParams.width = parentWidth / columns
        tvValue.layoutParams = layoutParams

        holder.setText(R.id.tv_value,if (StringUtil.isNotBlank(mList[position]))mList[position] else mContext.getString(R.string.tag_list_null) )
    }
}