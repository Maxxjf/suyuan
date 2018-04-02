package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.support.annotation.IdRes
import android.view.View
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_operation_tip.*

/**
 * 类说明：操作提示框
 * Author: Kuzan
 * Date: 2018/4/2 14:40.
 */
class OperationTipDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {

    override val viewId: Int
        get() = R.layout.dialog_operation_tip

    init {
        initView()
    }

    private fun initView() {
        btn_cancel.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_cancel, R.id.btn_ok -> {
                dismiss()
                onBtnClickListener?.onBtnClick(v)
            }
        }
    }

    fun setTip(tip: String) {
        tv_tip.text = tip
    }

    fun setTip(@IdRes tipRes: Int) {
        tv_tip.setText(tipRes)
    }

    fun setCancelBtn(tip: String) {
        btn_cancel.text = tip
    }

    fun setCancelBtn(@IdRes tipRes: Int) {
        btn_cancel.setText(tipRes)
    }

    fun setConfirmBtn(tip: String) {
        btn_ok.text = tip
    }

    fun setConfirmBtn(@IdRes tipRes: Int) {
        btn_ok.setText(tipRes)
    }
}