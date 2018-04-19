package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.ImageView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.SaleListBean
import com.qcloud.suyuan.enums.PayMethod

/**
 * 类型： 销售列表
 * Author: iceberg
 * Date: 2018/3/21.
 *
 */
class SaleListAdapter(context: Context) : CommonRecyclerAdapter<SaleListBean>(context) {

    private var selectItem: Int = -1
    override val viewId: Int
        get() = R.layout.adapter_sale_list

    fun setSelectItem(selectItem: Int) {
        this.selectItem = selectItem
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean: SaleListBean = mList.get(position)
        var ivFlag = holder.get<ImageView>(R.id.iv_flag)
        if (position == selectItem) {
            holder.mConvertView.setBackgroundResource(R.color.colorLine)
        } else {
            if (position % 2 == 0) {
                holder.mConvertView.setBackgroundResource(R.drawable.bg_item_dark_ripple)
            } else {
                holder.mConvertView.setBackgroundResource(R.drawable.bg_item_light_ripple)
            }
        }
        if (bean.payMethod == PayMethod.payCredit.key) {
            GlideUtil.loadImage(mContext, ivFlag, R.drawable.icon_flag_credit)
        } else if (bean.isRefund) {
            GlideUtil.loadImage(mContext, ivFlag, R.drawable.icon_flag_return)
        } else {
            ivFlag.setImageBitmap(null)
        }
        with(bean) {
            holder.setText(R.id.tv_number, if (StringUtil.isNotBlank("$serialNumber"))"$serialNumber" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_time, if (StringUtil.isNotBlank("$createDate"))"$createDate" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_money, if (StringUtil.isNotBlank("$realPay"))"$realPay" else mContext.getString(R.string.tag_list_null))
            holder.setText(R.id.tv_person, if (StringUtil.isNotBlank("$purchaserNmae"))"$purchaserNmae" else mContext.getString(R.string.tag_list_null))
        }

    }


}