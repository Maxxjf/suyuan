package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ScanCodeBean

/**
 * 类型：ReturnGoodsListAdapter
 * Author: iceberg
 * Date: 2018/3/21.
 * 小票信息
 */
class ReturnedReceiptAdapter(mContext: Context) : CommonRecyclerAdapter<ScanCodeBean.InfoListBean>(mContext) {

    override val viewId: Int
        get() = R.layout.adapter_returned_receipt

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: ScanCodeBean.InfoListBean = mList.get(position)
        var tvName = holder.get<TextView>(R.id.tv_name)
        var tvNumber = holder.get<TextView>(R.id.tv_number)
        var tvRule = holder.get<TextView>(R.id.tv_rule)
        var tvPrice = holder.get<TextView>(R.id.tv_price)
        var root    =holder.get<LinearLayout>(R.id.root)
        if (position%2==0){
            root.setBackgroundColor(ApiReplaceUtil.getColor(mContext,R.color.colorModelBgF9))
        }
        if (bean != null) {
            tvNumber?.setText("${bean.goodsNum}")
            tvName?.setText("${bean.goodsName}")
            tvRule?.setText("${bean.specification}")
            tvPrice?.setText("${bean.price}")
        }
    }


}