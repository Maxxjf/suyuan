package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
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
        val bean: CreditListBean.ListBean = mList[position]

        if (position%2 != 0){
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
        } else {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
        }
        with(bean) {
            holder.setText(R.id.tv_idcard, idCard)
                    .setText(R.id.tv_name, name)
                    .setText(R.id.tv_phone, mobile)
                    .setText(R.id.tv_money, "$sumRepayment")
        }
    }


}