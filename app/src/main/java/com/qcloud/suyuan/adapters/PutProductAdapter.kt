package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.qclib.widget.customview.RatioImageView
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.PurchaseProductBean

/**
 * Description: 产品入库
 * Author: gaobaiqiang
 * 2018/3/23 下午7:37.
 */
class PutProductAdapter(mContext: Context) : CommonRecyclerAdapter<PurchaseProductBean>(mContext) {
    override val viewId: Int
        get() = R.layout.item_of_put_product

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]

        val imgProduct = holder.get<RatioImageView>(R.id.img_product)
        if (position %2 == 0){
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
        } else {
            holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
        }
        with(bean) {
            GlideUtil.loadImage(mContext, imgProduct, image, R.drawable.bmp_product)
            holder.setText(R.id.tv_product_bar_code,if (StringUtil.isNotBlank(code))code else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_name,if (StringUtil.isNotBlank(name))name else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_manufacture,if (StringUtil.isNotBlank(millName))millName else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_spec,if (StringUtil.isNotBlank(specification))specification else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_stock,if (StringUtil.isNotBlank(stockStr))stockStr else mContext.getString(R.string.tag_list_null) )
        }
    }
}