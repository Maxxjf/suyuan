package com.qcloud.suyuan.adapters

import android.content.Context
import android.view.View
import android.widget.Button
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.SaleProductBean
import com.qcloud.suyuan.widgets.customview.RefreshNumView

/**
 * Description: 卖货(销售产品)
 * Author: gaobaiqiang
 * 2018/3/21 下午9:33.
 */
class SellersAdapter(context: Context): CommonRecyclerAdapter<SaleProductBean>(context) {

    var onRefreshNumClickListener: OnRefreshNumClickListener? = null

    override val viewId: Int
        get() = R.layout.item_of_sellers

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]
        val index = position+1

        val refreshNumView = holder.get<RefreshNumView>(R.id.refresh_num_view)
        val btnDel = holder.get<Button>(R.id.btn_delete)
        if (position %2 == 0) {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.colorItemBg))
        } else {
            holder.mConvertView.setBackgroundColor(ApiReplaceUtil.getColor(mContext, R.color.white))
        }

        with(bean) {
            holder.setText(R.id.tv_product_number,  if (StringUtil.isNotBlank(index.toString()))index.toString() else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_product_name,  if (StringUtil.isNotBlank(name))name else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_product_spec,  if (StringUtil.isNotBlank(specification))specification else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_product_batch, if (StringUtil.isNotBlank(batchNum))batchNum else mContext.getString(R.string.tag_list_null) )
                    .setVisible(R.id.tv_product_valid, if (active) View.GONE else View.VISIBLE)
                    .setText(R.id.tv_product_stock,  if (StringUtil.isNotBlank(stockStr))stockStr else mContext.getString(R.string.tag_list_null))
                    .setText(R.id.tv_selling_price, if (StringUtil.isNotBlank(priceStr))priceStr else mContext.getString(R.string.tag_list_null) )
        }

        refreshNumView.refreshBean(bean)
        refreshNumView.onRefreshNumClickListener = object : OnRefreshNumClickListener {
            override fun onRefreshNum(number: Int, bean: SaleProductBean) {
                onRefreshNumClickListener?.onRefreshNum(number, bean)
            }
        }
        btnDel.setOnClickListener {
            onHolderClick?.onHolderClick(it, bean, position)
        }
    }

    /**
     * 刷新item
     * */
    fun refreshBean(bean: SaleProductBean) {
        val index: Int = ergodicList(bean.recordId ?: "-1")
        if (index != -1) {
            val newBean = mList[index]
            if (newBean.number < newBean.stock) {
                newBean.number += bean.number
            }
            replaceBean(index, newBean)
        } else {
            addBeanAtEnd(bean)
        }
    }

    private fun ergodicList(recordId: String): Int {
        var index: Int = -1
        for (i in mList.indices) {
            if (mList[i].recordId == recordId) {
                index = i
                break
            }
        }
        return index
    }

    interface OnRefreshNumClickListener {
        fun onRefreshNum(number: Int, bean: SaleProductBean)
    }
}