package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.SaleListBean

/**
 * 类型： 销售列表
 * Author: iceberg
 * Date: 2018/3/21.
 *
 */
class SaleListAdapter(context: Context) : CommonRecyclerAdapter<SaleListBean>(context) {

    override val viewId: Int
        get() = R.layout.adapter_sale_list

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: SaleListBean = mList.get(position)
        var tvNumber = holder.get<TextView>(R.id.tv_number)
        var tvTime = holder.get<TextView>(R.id.tv_time)
        var tvMoney = holder.get<TextView>(R.id.tv_money)
        var tvPerson = holder.get<TextView>(R.id.tv_person)
        if (position%2==0){
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
        } else {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
        }
        if (bean != null) {
            tvNumber?.setText("${bean.serialNumber}")
            tvTime?.setText("${bean.createDate}")
            tvMoney?.setText("${bean.realPay}")
            tvPerson?.setText("${bean.purchaserNmae}")
        }
    }


}