package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
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
        var tvNumber = holder.get<TextView>(R.id.tv_suyuan_no)
        var tvName = holder.get<TextView>(R.id.tv_name)
        var tvRule = holder.get<TextView>(R.id.tv_rule)
        var tvHandleMember = holder.get<TextView>(R.id.tv_handle_member)
        var tvTime = holder.get<TextView>(R.id.tv_time)
        if (position%2==0){
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext,R.color.colorModelBgF9))
        }else{
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.white))
        }
        if (bean != null) {
            tvNumber?.setText(bean.traceabilityCode)
            tvName?.setText(bean.name)
            tvRule?.setText(bean.specification)
            tvHandleMember?.setText(bean.operaName)
            tvTime.setText(bean.createTime)
        }
    }


}