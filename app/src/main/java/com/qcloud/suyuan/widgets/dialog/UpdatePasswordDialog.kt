package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.View
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_input_password.*

/**
 * Description: 修改密码
 * Author: iceberg
 * 2018/4/2
 */
class UpdatePasswordDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {

    override val viewId: Int
        get() = R.layout.dialog_input_password

    init {
        initView()
    }

    private fun initView() {

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
        val pw = et_password.text.toString().trim()
        val newPw = et_new_password.text.toString().trim()
        val newPw2 = et_new_password_2rd.text.toString().trim()

        if (StringUtil.isBlank(pw)) {
            QToast.show(mContext, R.string.et_hint_original_password)
            return false
        }
        if (StringUtil.isBlank(newPw)) {
            QToast.show(mContext, R.string.et_hint_new_password)
            return false
        }
        if (StringUtil.isBlank(newPw2)) {
            QToast.show(mContext, R.string.et_hint_2rd_password)
            return false
        }
        return true
    }

    fun getPassword(): String {
        val pw = et_password.text.toString().trim()
        return pw
    }

    fun getNewPassword(): String {
        val newPw = et_new_password.text.toString().trim()
        return newPw
    }

    fun getNewPassword2rd(): String {
        val newPw2 = et_new_password_2rd.text.toString().trim()
        return newPw2
    }

}