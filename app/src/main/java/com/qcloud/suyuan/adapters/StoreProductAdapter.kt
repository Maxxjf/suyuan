package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ProductBean

/**
 * Description: 门店产品(库存管理列表)
 * Author: gaobaiqiang
 * 2018/3/24 下午1:49.
 */
class StoreProductAdapter(mContext: Context) : CommonRecyclerAdapter<ProductBean>(mContext) {
    override val viewId: Int
        get() = R.layout.item_of_store_product

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
                    .setText(R.id.tv_product_total_stock,if (StringUtil.isNotBlank(amountStr))amountStr else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_type, if (StringUtil.isNotBlank(platformName))platformName else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_product_price,if (StringUtil.isNotBlank(retailPriceStr))retailPriceStr else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_last_in_time,if (StringUtil.isNotBlank(createDate))createDate else mContext.getString(R.string.tag_list_null) )
        }
    }
}