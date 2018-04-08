package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.CreditInfoBean

/**
 * 类型：OrderInfoAdapter
 * Author: iceberg
 * Date: 2018/3/24
 * 账单明细
 */
class OrderInfoAdapter(context: Context) : CommonRecyclerAdapter<CreditInfoBean>(context) {

    override val viewId: Int
        get() = R.layout.adapter_order_info

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: CreditInfoBean = mList.get(position)
        var tvTime = holder.get<TextView>(R.id.tv_time)
        var tvWaterNumber = holder.get<TextView>(R.id.tv_water_number)
        var tvCredit = holder.get<TextView>(R.id.tv_credit)
        var tvPayMoney = holder.get<TextView>(R.id.tv_payed_money)
        var btnPay = holder.get<Button>(R.id.btn_pay)
        var root    =holder.get<LinearLayout>(R.id.root)
        if (position%2==0){
            root.setBackgroundColor(ApiReplaceUtil.getColor(mContext,R.color.colorItemBg))
        }
        if (bean != null) {
            tvTime?.setText("${bean.time}")
            tvWaterNumber?.setText("${bean.serialNumber}")
            tvCredit?.setText("${bean.shouldRepayment}")
            tvPayMoney?.setText("${bean.alreadyRepayment}")
        }
        btnPay.setOnClickListener {
            onHolderClick?.onHolderClick(it,bean,position)
        }

    }


}