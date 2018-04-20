package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.Button
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.CreditInfoBean

/**
 * 类型：OrderInfoAdapter
 * Author: iceberg
 * Date: 2018/3/24
 * 账单明细
 */
class OrderInfoAdapter(context: Context) : CommonRecyclerAdapter<CreditInfoBean>(context) {

    var moneyStr: String = mContext.resources.getString(R.string.money_str)

    override val viewId: Int
        get() = R.layout.adapter_order_info

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: CreditInfoBean = mList.get(position)
        var btnPay = holder.get<Button>(R.id.btn_pay)
        if (position % 2 == 0) {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
        } else {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
        }
        with(bean) {
            holder.setText(R.id.tv_time, time)
            holder.setText(R.id.tv_water_number, serialNumber)
            holder.setText(R.id.tv_credit, String.format(moneyStr, shouldRepayment))
            holder.setText(R.id.tv_payed_money, String.format(moneyStr, alreadyRepayment))
        }
        btnPay.setOnClickListener {
            onHolderClick?.onHolderClick(it, bean, position)
        }

    }


}