package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.CreditListBean

/**
 * 类型： CreditListAdapter
 * Author: iceberg
 * Date: 2018/3/24
 * 赊账列表
 */
class CreditListAdapter(mContext: Context) : CommonRecyclerAdapter<CreditListBean.ListBean>(mContext) {

    override val viewId: Int
        get() = R.layout.adapter_credit_list

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: CreditListBean.ListBean = mList.get(position)
        var tvName = holder.get<TextView>(R.id.tv_name)
        var tvIdcard = holder.get<TextView>(R.id.tv_idcard)
        var tvPhone = holder.get<TextView>(R.id.tv_phone)
        var tvMoney = holder.get<TextView>(R.id.tv_money)
        var root    =holder.get<LinearLayout>(R.id.root)
        if (position%2==0){
            root.setBackgroundColor(ApiReplaceUtil.getColor(mContext,R.color.colorModelBgF9))
        }else{
            root.setBackgroundColor(ApiReplaceUtil.getColor(mContext,R.color.white))
        }
        if (bean != null) {
            tvIdcard?.setText("${bean.idCard}")
            tvName?.setText("${bean.name}")
            tvPhone?.setText("${bean.mobile}")
            tvMoney?.setText("${bean.sumRepayment}")
        }
    }


}