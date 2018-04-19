package com.qcloud.suyuan.adapters

import android.annotation.SuppressLint
import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.CreditListBean

/**
 * 类型： CreditListAdapter
 * Author: iceberg
 * Date: 2018/3/24
 * 赊账列表
 */
class CreditListAdapter(mContext: Context) : CommonRecyclerAdapter<CreditListBean.ListBean>(mContext) {
    private var itemSelected = -1

    override val viewId: Int
        get() = R.layout.adapter_credit_list

    fun setItemSelete(itemSelected: Int) {
        this.itemSelected = itemSelected
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: CreditListBean.ListBean = mList[position]

        if (position == itemSelected) {
            holder.mConvertView.setBackgroundResource(R.color.colorLine)
        } else {
            if (position % 2 != 0) {
                holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
            } else {
                holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
            }
        }
        with(bean) {
            holder.setText(R.id.tv_idcard, if (StringUtil.isNotBlank(idCard)) idCard else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_name, if (StringUtil.isNotBlank(name)) name else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_phone, if (StringUtil.isNotBlank(mobile)) mobile else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_money, if (StringUtil.isNotBlank("$sumRepayment")) "$sumRepayment" else mContext.getString(R.string.tag_list_null))
        }
    }


}