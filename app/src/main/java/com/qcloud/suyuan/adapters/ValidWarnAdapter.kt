package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.Button
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.qclib.widget.customview.RatioImageView
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ValidWarnBean

/**
 * Description: 有效期告警
 * Author: gaobaiqiang
 * 2018/3/21 下午5:10.
 */
class ValidWarnAdapter(context: Context): CommonRecyclerAdapter<ValidWarnBean>(context) {
    override val viewId: Int
        get() = R.layout.item_of_valid_warn

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]

        val imgProduct = holder.get<RatioImageView>(R.id.img_product)
        val btnOutStorage = holder.get<Button>(R.id.btn_out_storage)

        if (position%2==0){
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext,R.color.colorItemBg))
        }else{
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.white))
        }

        with(bean) {
            GlideUtil.loadImage(mContext, imgProduct, imageUrl, R.drawable.bmp_product)
            holder.setText(R.id.tv_product_bar_code, barCode)
                    .setText(R.id.tv_product_name, name)
                    .setText(R.id.tv_product_spec, specification)
                    .setText(R.id.tv_product_manufacture, millName)
                    .setText(R.id.tv_product_stock, surplusNumStr)
                    .setText(R.id.tv_product_valid, stopDate)
                    .setText(R.id.tv_product_batch, batchNum)
        }

        btnOutStorage.setOnClickListener {
            onHolderClick?.onHolderClick(it, bean, position)
        }
    }
}