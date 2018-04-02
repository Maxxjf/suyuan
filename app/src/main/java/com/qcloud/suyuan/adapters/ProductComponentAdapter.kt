package com.qcloud.suyuan.adapters

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ProductComponentBean

/**
 * Description: 产品成分表格适配器
 * Author: gaobaiqiang
 * 2018/3/30 下午4:48.
 */
class ProductComponentAdapter(mContext: Context, private val columns: Int) : CommonRecyclerAdapter<ProductComponentBean>(mContext) {
    override val viewId: Int
        get() = R.layout.item_of_product_component

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]
        for (i in 0 until columns) {
            if (i < 10) {
                val resId = mContext.resources.getIdentifier("tv_value_$i", "id", mContext.packageName)
                val tvValue = holder.get<TextView>(resId)
                tvValue.visibility = View.VISIBLE
                if (i < bean.valueList.size) {
                    tvValue.text = bean.valueList[i]
                }
            }
        }

        val btnDel = holder.get<ImageView>(R.id.btn_delete)

        btnDel.setOnClickListener {
            onHolderClick?.onHolderClick(it, bean, position)
        }
    }
}