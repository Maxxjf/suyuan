package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ProductBean

/**
 * 类说明：我的产品
 * Author: Kuzan
 * Date: 2018/3/30 11:13.
 */
class MyProductAdapter(mContext: Context) : CommonRecyclerAdapter<ProductBean>(mContext) {
    override val viewId: Int
        get() = R.layout.item_of_my_product

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]

        if (position %2 == 0){
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
        } else {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
        }
        with(bean) {
            holder.setText(R.id.tv_product_bar_code,if (StringUtil.isNotBlank(barCode))barCode else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_name,if (StringUtil.isNotBlank(name))name else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_spec,if (StringUtil.isNotBlank(specification))specification else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_manufacture,if (StringUtil.isNotBlank(millName))millName else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_operator,if (StringUtil.isNotBlank(operaName))operaName else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_last_in_time,if (StringUtil.isNotBlank(createDate))createDate else mContext.getString(R.string.tag_list_null) )
        }
    }
}