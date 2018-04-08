package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
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
        var tvName = holder.get<TextView>(R.id.tv_name)
        var tvNumber = holder.get<TextView>(R.id.tv_number)
        var tvRule = holder.get<TextView>(R.id.tv_rule)
        var tvPrice = holder.get<TextView>(R.id.tv_price)

        if (position%2==0){
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext,R.color.colorItemBg))
        }else{
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.white))
        }
        if (bean != null) {
            tvName?.setText("${bean.goodsName}")
            tvNumber?.setText("${bean.goodsNum}")
            tvRule?.setText("${bean.specification}")
            tvPrice?.setText("${bean.price}")
        }
    }


}