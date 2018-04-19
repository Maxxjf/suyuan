package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.StringUtil
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
        if (position%2==0){
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
        } else {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
        }
        with(bean){
            holder.setText(R.id.tv_suyuan_no,if (StringUtil.isNotBlank("$traceabilityCode"))"$traceabilityCode" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_name,if (StringUtil.isNotBlank("$name"))"$name" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_rule,if (StringUtil.isNotBlank("$specification"))"$specification" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_handle_member,if (StringUtil.isNotBlank("$operaName"))"$operaName" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_time,if (StringUtil.isNotBlank("$createTime"))"$createTime" else mContext.getString(R.string.tag_list_null))
        }
    }


}