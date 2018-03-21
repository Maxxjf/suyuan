package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.GoodsBean

/**
 * 类型：ReturnGoodsListAdapter
 * Author: iceberg
 * Date: 2018/3/21.
 * 小票信息
 */
class ReturnedReceiptAdapter(mContext: Context) : CommonRecyclerAdapter<GoodsBean>(mContext) {

    override val viewId: Int
        get() = R.layout.adapter_returned_receipt

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: GoodsBean = mList.get(position)
        var name = holder.get<TextView>(R.id.tv_name)
        var number = holder.get<TextView>(R.id.tv_number)
        var rule = holder.get<TextView>(R.id.tv_rule)
        var date = holder.get<TextView>(R.id.tv_price)
        if (bean != null) {
            number?.setText(bean.number)
            name?.setText(bean.name)
            rule?.setText(bean.rule)
            date?.setText(bean.date)
        }
    }


}