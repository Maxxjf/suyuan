package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.RepaymentListBean

/**
 * 类型： RepaymentListAdapter
 * Author: iceberg
 * Date: 2018/3/24
 * 还款历史列表
 */
class RepaymentListAdapter(mContext: Context) : CommonRecyclerAdapter<RepaymentListBean>(mContext) {

    override val viewId: Int
        get() = R.layout.adapter_repayment_list

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: RepaymentListBean = mList[position]

        with(bean) {
            holder.setText(R.id.tv_date, if (StringUtil.isNotBlank(time)) time else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_money, if (StringUtil.isNotBlank("${repayment}")) "${repayment}" else mContext.getString(R.string.tag_list_null))
        }
    }


}