package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.ImageView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ProductComponentBean

/**
 * Description: 产品成分表格适配器
 * Author: gaobaiqiang
 * 2018/3/30 下午4:48.
 */
class ProductComponentAdapter(mContext: Context) : CommonRecyclerAdapter<ProductComponentBean>(mContext) {
    override val viewId: Int
        get() = R.layout.item_of_product_component

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]

        val btnDel = holder.get<ImageView>(R.id.btn_delete)

        with(bean) {
            holder.setText(R.id.tv_value_i, key)
                    .setText(R.id.tv_value_ii, value)
        }

        btnDel.setOnClickListener {
            onHolderClick?.onHolderClick(it, bean, position)
        }
    }
}