package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.qclib.widget.customview.RatioImageView
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ProductBean

/**
 * Description: 产品入库
 * Author: gaobaiqiang
 * 2018/3/23 下午7:37.
 */
class PutProductAdapter(mContext: Context) : CommonRecyclerAdapter<ProductBean>(mContext) {
    override val viewId: Int
        get() = R.layout.item_of_put_product

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]

        val imgProduct = holder.get<RatioImageView>(R.id.img_product)
        if (position %2 == 0) {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.colorModelBgF2))
        } else {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.white))
        }
        with(bean) {
            GlideUtil.loadImage(mContext, imgProduct, imageUrl, R.drawable.bmp_product)
            holder.setText(R.id.tv_product_bar_code, barCode)
                    .setText(R.id.tv_product_name, name)
                    .setText(R.id.tv_product_manufacture, millName)
                    .setText(R.id.tv_product_spec, specification)
                    .setText(R.id.tv_product_stock, amountStr)
        }
    }
}