package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.Button
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.StockBean

/**
 * Description: 入库列表
 * Author: gaobaiqiang
 * 2018/3/24 下午3:40.
 */
class InStorageAdapter(mContext: Context) : CommonRecyclerAdapter<StockBean>(mContext) {
    override val viewId: Int
        get() = R.layout.item_of_in_storage

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]

        val btnPrint = holder.get<Button>(R.id.btn_print)

        if (position %2 == 0) {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.colorModelBgF2))
        } else {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.white))
        }

        with(bean) {
            holder.setText(R.id.tv_batch_number, batch)
                    .setText(R.id.tv_time, time)
                    .setText(R.id.tv_number, number)
                    .setText(R.id.tv_stock, stock)
                    .setText(R.id.tv_product_valid, valid)
                    .setText(R.id.tv_price, price)
                    .setText(R.id.tv_operator, operator)
        }

        btnPrint.setOnClickListener {
            onHolderClick?.onHolderClick(btnPrint, bean, position)
        }
    }
}