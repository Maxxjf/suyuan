package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.RepaymentListBean

/**
 * 类型： RepaymentListAdapter
 * Author: iceberg
 * Date: 2018/3/24
 * 还款历史列表
 */
class RepaymentListAdapter(mContext: Context) : CommonRecyclerAdapter<RepaymentListBean>(mContext) {

    var moneyStr: String = mContext.resources.getString(R.string.money_str)
    override val viewId: Int
        get() = R.layout.adapter_repayment_list

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: RepaymentListBean = mList[position]

        with(bean) {
            holder.setText(R.id.tv_date,  time )
                    .setText(R.id.tv_money,  String.format(moneyStr,repayment))
        }
    }


}