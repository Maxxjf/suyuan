package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.IDBean

/**
 * Description: 身份证号
 * Author: gaobaiqiang
 * 2018/3/29 上午11:36.
 */
class IDCardAdapter(context: Context): CommonRecyclerAdapter<IDBean>(context) {

    override val viewId: Int
        get() = R.layout.item_of_drop_down

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]
        holder.setText(R.id.tv_value, if (StringUtil.isNotBlank(bean.idCode)) bean.idCode else mContext.getString(R.string.tag_list_null))
    }
}