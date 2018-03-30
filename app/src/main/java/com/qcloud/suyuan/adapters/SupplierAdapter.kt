package com.qcloud.suyuan.adapters

import android.content.Context
import android.widget.TextView
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.SupplierBean

/**
 * 类型：
 * Author: iceberg
 * Date: 2018/3/29  11:14
 */
class SupplierAdapter(context: Context):CommonRecyclerAdapter<SupplierBean>(context){
    override val viewId: Int
        get() = R.layout.adapter_supplier

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val bean=mList[position]

        var tvName=holder.get<TextView>(R.id.tv_name)
        var tvPerson=holder.get<TextView>(R.id.tv_person)
        var tvPhone=holder.get<TextView>(R.id.tv_phone)
        if (bean!=null){
            tvName.text="${bean.name}"
            tvPerson.text="${bean.principal}"
            tvPhone.text="${bean.phone}"
        }
    }

}