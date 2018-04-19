package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.SaleInfoBean

/**
 * 类型： 销售详情
 * Author: iceberg
 * Date: 2018/3/21.
 *
 */
class SaleInfoAdapter(context: Context) : CommonRecyclerAdapter<SaleInfoBean.ListBean>(context) {

    override val viewId: Int
        get() = R.layout.adapter_sale_info

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: SaleInfoBean.ListBean = mList.get(position)

        if (position%2!=0){
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
        } else {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
        }
        with(bean){
            holder.setText(R.id.tv_name,if (StringUtil.isNotBlank("$goodsName"))"$goodsName" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_number,if (StringUtil.isNotBlank("$goodsNum"))"$goodsNum" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_rule,if (StringUtil.isNotBlank("$specification"))"$specification" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_price,if (StringUtil.isNotBlank("$price"))"$price" else mContext.getString(R.string.tag_list_null))
        }
    }


}