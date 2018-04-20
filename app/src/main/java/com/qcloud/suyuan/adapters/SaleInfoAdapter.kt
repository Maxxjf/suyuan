package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.SaleInfoBean

/**
 * 类型： 销售详情
 * Author: iceberg
 * Date: 2018/3/21.
 *
 */
class SaleInfoAdapter(context: Context) : CommonRecyclerAdapter<SaleInfoBean.ListBean>(context) {
    var moneyStr: String = mContext.resources.getString(R.string.money_str)
    override val viewId: Int
        get() = R.layout.adapter_sale_info

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: SaleInfoBean.ListBean = mList.get(position)

        if (position % 2 != 0) {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
        } else {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
        }
        with(bean) {
            holder.setText(R.id.tv_name, goodsName)
            holder.setText(R.id.tv_number, "$goodsNum")
            holder.setText(R.id.tv_rule, specification)
            holder.setText(R.id.tv_price, String.format(moneyStr, price))
        }
    }


}