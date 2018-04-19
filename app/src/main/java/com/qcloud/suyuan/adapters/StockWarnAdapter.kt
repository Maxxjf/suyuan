package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.qclib.widget.customview.RatioImageView
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.StockWarnBean

/**
 * Description: 库存告警
 * Author: gaobaiqiang
 * 2018/3/21 下午4:42.
 */
class StockWarnAdapter(context: Context) : CommonRecyclerAdapter<StockWarnBean>(context) {
    override val viewId: Int
        get() = R.layout.item_of_stock_warn

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]

        val imgProduct = holder.get<RatioImageView>(R.id.img_product)
        val tvStock = holder.get<TextView>(R.id.tv_product_stock)

        if (position % 2 == 0) {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.colorItemBg))
        } else {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.white))
        }
        if (bean.amount <= bean.cordon) {
            tvStock.setTextColor(ApiReplaceUtil.getColor(mContext, R.color.colorRed))
        } else {
            tvStock.setTextColor(ApiReplaceUtil.getColor(mContext, R.color.colorTitle))
        }
        with(bean) {
            GlideUtil.loadImage(mContext, imgProduct, imageUrl, R.drawable.bmp_product)
            holder.setText(R.id.tv_product_bar_code,if (StringUtil.isNotBlank(barCode))barCode else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_name,if (StringUtil.isNotBlank(name))name else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_spec, if (StringUtil.isNotBlank(specification))specification else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_product_manufacture,if (StringUtil.isNotBlank(millName))millName else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_stock,if (StringUtil.isNotBlank(amountStr))amountStr else mContext.getString(R.string.tag_list_null) )
        }
    }
}