package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.CodeBean

/**
 * 类型：ReturnGoodsListAdapter
 * Author: iceberg
 * Date: 2018/3/22.
 * 退货记录
 */
class ReturnedRecordAdapter(mContext: Context) : CommonRecyclerAdapter<CodeBean>(mContext) {

    override val viewId: Int
        get() = R.layout.adapter_returned_record

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: CodeBean = mList.get(position)
        if (position % 2 == 0) {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
        } else {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
        }
        with(bean) {
            holder.setText(R.id.tv_suyuan_no, traceabilityCode)
            holder.setText(R.id.tv_name, name)
            holder.setText(R.id.tv_rule, specification)
            holder.setText(R.id.tv_handle_member, operaName)
            holder.setText(R.id.tv_time, createTime)
        }
    }


}