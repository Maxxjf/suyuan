package com.qcloud.suyuan.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.GoodsBean
import timber.log.Timber

/**
 * 类型：ReturnGoodsListAdapter
 * Author: iceberg
 * Date: 2018/3/21.
 * 退货列表
 */
class ReturnGoodsListAdapter(mContext: Context) : CommonRecyclerAdapter<GoodsBean>(mContext) {

    override val viewId: Int
        get() = R.layout.adapter_returned_goods_list

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: GoodsBean = mList.get(position)
        Timber.e("实体：${bean}")
        var number = holder.get<TextView>(R.id.tv_number)
        var name = holder.get<TextView>(R.id.tv_name)
        var rule = holder.get<TextView>(R.id.tv_rule)
        var date = holder.get<TextView>(R.id.tv_date)
        var delete = holder.get<ImageView>(R.id.iv_delete)
//        with(bean) {
//            tvNumber.setText(number)
//        }
        if (bean != null) {
            number?.setText(bean.number)
            name?.setText(bean.name)
            rule?.setText(bean.rule)
            date?.setText(bean.date)
        }
        delete?.setOnClickListener(View.OnClickListener {
            remove(position)
            notifyDataSetChanged()
        })
    }


}