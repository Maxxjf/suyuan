package com.qcloud.suyuan.adapters

import android.content.Context
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.SupplierBean

/**
 * 类型：我的供应商
 * Author: iceberg
 * Date: 2018/3/29  11:14
 */
class SupplierAdapter(context: Context) : CommonRecyclerAdapter<SupplierBean>(context) {
    override val viewId: Int
        get() = R.layout.adapter_supplier

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean = mList[position]
        with(bean) {
            holder.setText(R.id.tv_name, name)
            holder.setText(R.id.tv_person, principal)
            holder.setText(R.id.tv_phone, phone)
        }
    }

}