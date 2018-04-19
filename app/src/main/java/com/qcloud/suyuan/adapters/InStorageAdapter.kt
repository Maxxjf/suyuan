package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.Button
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.InStorageRecordBean

/**
 * Description: 入库列表
 * Author: gaobaiqiang
 * 2018/3/24 下午3:40.
 */
class InStorageAdapter(mContext: Context) : CommonRecyclerAdapter<InStorageRecordBean>(mContext) {
    private var moneyStr: String = "¥"

    init {
        moneyStr = mContext.resources.getString(R.string.money_str)
    }

    override val viewId: Int
        get() = R.layout.item_of_in_storage

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        val bean = mList[position]

        val btnPrint = holder.get<Button>(R.id.btn_print)

        if (position %2 == 0) {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.colorItemBg))
        } else {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.white))
        }

        with(bean) {
            holder.setText(R.id.tv_batch_number, if (StringUtil.isNotBlank(batchNum))batchNum else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_time, if (StringUtil.isNotBlank(createDate)) createDate else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_number,if (StringUtil.isNotBlank(goodsNumStr)) goodsNumStr else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_stock, if (StringUtil.isNotBlank(surplusNumStr))surplusNumStr else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_product_valid, if (StringUtil.isNotBlank(stopDate)) stopDate else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_price,if (StringUtil.isNotBlank(String.format(moneyStr, price))) String.format(moneyStr, price) else mContext.getString(R.string.tag_list_null) )
                    .setText(R.id.tv_operator, if (StringUtil.isNotBlank(operaName)) operaName else mContext.getString(R.string.tag_list_null))
        }

        btnPrint.setOnClickListener {
            onHolderClick?.onHolderClick(btnPrint, bean, position)
        }
    }
}