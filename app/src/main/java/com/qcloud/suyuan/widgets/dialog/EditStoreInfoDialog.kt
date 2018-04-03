package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.View
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.StoreBean
import kotlinx.android.synthetic.main.dialog_edit_store_info.*

/**
 * Description: 修改密码
 * Author: iceberg
 * 2018/4/2
 */
class EditStoreInfoDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {
    private var mCurrentStore: StoreBean? = null

    override val viewId: Int
        get() = R.layout.dialog_edit_store_info

    init {
        initView()
    }

    private fun initView() {
        btn_close.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> dismiss()
            R.id.btn_confirm -> {
                if (check()) {
                    dismiss()
                    onBtnClickListener?.onBtnClick(v)
                }
            }
        }
    }


    private fun check(): Boolean {
        val address = et_address.text.toString().trim()
        val person = et_person.text.toString().trim()
        val phone = et_phone.text.toString().trim()
        if (StringUtil.isBlank(address)) {
            QToast.show(mContext, R.string.hint_input_store_address)
            return false
        }
        if (StringUtil.isBlank(person)) {
            QToast.show(mContext, R.string.hint_input_store_person)
            return false
        }
        if (StringUtil.isBlank(phone)) {
            QToast.show(mContext, R.string.et_hint_phone)
            return false
        }
        return true
    }

    fun replaceInfo(bean: StoreBean){
        mCurrentStore=bean
        tv_number.text= "${bean.store!!.storeCode}"
        tv_name.text= "${bean.store!!.name}"
        et_address.setText("${bean.store!!.address}")
        et_person.setText("${ bean.store!!.shopkeeperName}")
        et_phone.setText( "${bean.store!!.phone}")
        tv_area.text= "${bean.store!!.areaName}"
    }

    fun getAddress(): String {
        val address = et_address.text.toString().trim()
        return address
    }

    fun getPerson(): String {
        val person = et_person.text.toString().trim()
        return person
    }

    fun getPhone(): String {
        val phone = et_phone.text.toString().trim()
        return phone
    }

}