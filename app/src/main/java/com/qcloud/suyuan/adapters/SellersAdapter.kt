package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.ImageView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.SellersBean
import com.qcloud.suyuan.widgets.customview.RefreshNumView

/**
 * Description: 卖货(销售产品)
 * Author: gaobaiqiang
 * 2018/3/21 下午9:33.
 */
class SellersAdapter(context: Context): CommonRecyclerAdapter<SellersBean>(context) {
    override val viewId: Int
        get() = R.layout.item_of_sellers

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]
        val number = position+1

        val refreshNumView = holder.get<RefreshNumView>(R.id.refresh_num_view)
        val btnDel = holder.get<ImageView>(R.id.btn_delete)

        with(bean) {
            holder.setText(R.id.tv_product_number, number.toString())
                    .setText(R.id.tv_product_name, name)
                    .setText(R.id.tv_product_spec, spec)
                    .setText(R.id.tv_product_batch, batchCode)
                    .setText(R.id.tv_product_valid, outDate)
                    .setText(R.id.tv_product_stock, stock.toString())
                    .setText(R.id.tv_selling_price, price.toString())
        }

        refreshNumView.refreshBean(bean)
        btnDel.setOnClickListener {
            onHolderClick?.onHolderClick(it, bean, position)
        }
    }
}