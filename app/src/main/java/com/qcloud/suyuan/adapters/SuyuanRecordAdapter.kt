package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.SuyuanRecordBean

/**
 * Description: 溯源记录列表
 * Author: gaobaiqiang
 * 2018/4/3 下午5:44.
 */
class SuyuanRecordAdapter(mContext: Context) : CommonRecyclerAdapter<SuyuanRecordBean>(mContext) {
    override val viewId: Int
        get() = R.layout.item_of_suyuan_record

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]

        if (position%2==0){
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext,R.color.colorModelBgF9))
        }else{
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.white))
        }

        with(bean) {
            holder.setText(R.id.tv_suyuan_number, traceabilityCode)
                    .setText(R.id.tv_product_name, goodsName)
                    .setText(R.id.tv_product_spec, specification)
                    .setText(R.id.tv_purchaser, purchaser)
                    .setText(R.id.tv_store_address, storeAddress)
                    .setText(R.id.tv_sale_time, purchaserTime)
        }
    }
}