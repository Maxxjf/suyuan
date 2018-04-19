package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.qclib.widget.customview.RatioImageView
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.widgets.customview.ModifyPriceView
import com.qcloud.suyuan.widgets.customview.ModifyStockView

/**
 * 类说明：修改售价和库存
 * Author: Kuzan
 * Date: 2018/3/29 20:12.
 */
class ModifyPriceStockAdapter(mContext: Context) : CommonRecyclerAdapter<ProductBean>(mContext) {

    var onModifyListener: OnModifyListener? = null

    override val viewId: Int
        get() = R.layout.item_of_modify_price_stock

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]

        val modifyPrice = holder.get<ModifyPriceView>(R.id.modify_price)
        val modifyStock = holder.get<ModifyStockView>(R.id.modify_stock)

        val imgProduct = holder.get<RatioImageView>(R.id.img_product)
        if (position %2 == 0) {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.colorItemBg))
        } else {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.white))
        }
        with(bean) {
            GlideUtil.loadImage(mContext, imgProduct, imageUrl, R.drawable.bmp_product)
            holder.setText(R.id.tv_product_name, if (StringUtil.isNotBlank(name))name else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_product_spec,if (StringUtil.isNotBlank(specification))specification else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_product_manufacture,if (StringUtil.isNotBlank(millName))millName else mContext.getString(R.string.tag_list_null) )
            modifyPrice.initData(this)
            modifyStock.initData(this)
        }
        modifyPrice.onConfirmListener = object : ModifyPriceView.OnConfirmListener {
            override fun onConfirm(bean: ProductBean?) {
                onModifyListener?.onModify(bean, 1)
            }
        }

        modifyStock.onConfirmListener = object : ModifyStockView.OnConfirmListener {
            override fun onConfirm(bean: ProductBean?) {
                onModifyListener?.onModify(bean, 2)
            }
        }
    }

    interface OnModifyListener {
        fun onModify(bean: ProductBean?, type: Int)
    }
}