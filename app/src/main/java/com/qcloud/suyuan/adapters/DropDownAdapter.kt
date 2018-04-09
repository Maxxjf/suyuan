package com.qcloud.suyuan.adapters

import android.content.Context
import android.text.Html
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.suyuan.R

/**
 * Description: 下拉适配器
 * Author: gaobaiqiang
 * 2018/3/22 下午11:15.
 */
class DropDownAdapter(mContext: Context) : CommonRecyclerAdapter<String>(mContext) {
    override val viewId: Int
        get() = R.layout.item_of_drop_down

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.setText(R.id.tv_value, ApiReplaceUtil.fromHtml(mList[position]).toString())
    }
}