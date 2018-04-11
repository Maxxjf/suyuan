package com.qcloud.suyuan.adapters

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ScanCodeBean

/**
 * 类型：ReturnGoodsListAdapter
 * Author: iceberg
 * Date: 2018/3/21.
 * 退货列表
 */
class ReturnGoodsListAdapter(context: Context) : CommonRecyclerAdapter<ScanCodeBean.MerchandiseBean>(context) {

    override val viewId: Int
        get() = R.layout.adapter_returned_goods_list

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: ScanCodeBean.MerchandiseBean = mList.get(position)
        var tvGoodsId = holder.get<TextView>(R.id.tv_number)
        var tvName = holder.get<TextView>(R.id.tv_name)
        var tvRule = holder.get<TextView>(R.id.tv_rule)
        var tvDate = holder.get<TextView>(R.id.tv_date)
        var ivDelete = holder.get<ImageView>(R.id.iv_detele)
        if (position%2==0){
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
        } else {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
        }
        if (bean != null) {
            tvGoodsId?.setText(bean.traceabilityCode)
            tvName?.setText(bean.goodsName)
            tvRule?.setText(bean.specification)
            tvDate?.setText(bean.batchNum)
        }
        ivDelete?.setOnClickListener(View.OnClickListener {
            remove(position)
            notifyDataSetChanged()
            onHolderClick?.onHolderClick(ivDelete,bean,position)
        })
    }


}