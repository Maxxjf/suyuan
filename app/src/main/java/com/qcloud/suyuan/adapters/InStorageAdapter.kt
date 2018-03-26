package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.Button
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.InStorageRecordBean

/**
 * Description: 入库列表
 * Author: gaobaiqiang
 * 2018/3/24 下午3:40.
 */
class InStorageAdapter(mContext: Context) : CommonRecyclerAdapter<InStorageRecordBean>(mContext) {
    private var moneyStr: String = "¥"

    init {
        moneyStr = mContext.resources.getString(R.string.money_str)
    }

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
            holder.setText(R.id.tv_batch_number, batchNum)
                    .setText(R.id.tv_time, createDate)
                    .setText(R.id.tv_number, goodsNumStr)
                    .setText(R.id.tv_stock, surplusNumStr)
                    .setText(R.id.tv_product_valid, stopDate)
                    .setText(R.id.tv_price, String.format(moneyStr, price))
                    .setText(R.id.tv_operator, operaName)
        }

        btnPrint.setOnClickListener {
            onHolderClick?.onHolderClick(btnPrint, bean, position)
        }
    }
}