package com.qcloud.suyuan.adapters

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.StringUtil
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
        var ivDelete = holder.get<ImageView>(R.id.iv_detele)
        if (position%2==0){
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
        } else {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
        }
        with(bean){
            holder.setText(R.id.tv_number,if (StringUtil.isNotBlank("$traceabilityCode"))"$traceabilityCode" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_name,if (StringUtil.isNotBlank("$goodsName"))"$goodsName" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_rule,if (StringUtil.isNotBlank("$specification"))"$specification" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_date,if (StringUtil.isNotBlank("$batchNum"))"$batchNum" else mContext.getString(R.string.tag_list_null))
        }
        ivDelete?.setOnClickListener(View.OnClickListener {
            remove(position)
            notifyDataSetChanged()
            onHolderClick?.onHolderClick(ivDelete,bean,position)
        })
    }


}