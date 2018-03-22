package com.qcloud.suyuan.adapters

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.GoodsBean

/**
 * 类型：ReturnGoodsListAdapter
 * Author: iceberg
 * Date: 2018/3/21.
 * 退货列表
 */
class ReturnGoodsListAdapter(context: Context) : CommonRecyclerAdapter<GoodsBean>(context) {

    override val viewId: Int
        get() = R.layout.adapter_returned_goods_list

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: GoodsBean = mList.get(position)
        var tvGoodsId = holder.get<TextView>(R.id.tv_number)
        var tvName = holder.get<TextView>(R.id.tv_name)
        var tvRule = holder.get<TextView>(R.id.tv_rule)
        var tvDate = holder.get<TextView>(R.id.tv_date)
        //var ivDelete = holder.get<RelativeLayout>(R.id.iv_delete)
        var root    =holder.get<LinearLayout>(R.id.root)
        if (position%2==0){
            root.setBackgroundColor(ApiReplaceUtil.getColor(mContext,R.color.colorModelBgF9))
        }
        if (bean != null) {
            tvGoodsId?.setText(bean.id)
            tvName?.setText(bean.name)
            tvRule?.setText(bean.rule)
            tvDate?.setText(bean.date)
        }
//        ivDelete?.setOnClickListener(View.OnClickListener {
//            remove(position)
//            notifyDataSetChanged()
//        })
    }


}