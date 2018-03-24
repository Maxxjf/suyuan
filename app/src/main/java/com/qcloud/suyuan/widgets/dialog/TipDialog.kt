package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.support.annotation.IdRes
import android.view.View
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_tip.*

/**
 * Description: 消息弹窗
 * Author: gaobaiqiang
 * 2018/3/20 下午5:50.
 */
class TipDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {

    override val viewId: Int
        get() = R.layout.dialog_tip

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

    fun setConfirmBtn(tip: String) {
        btn_confirm.text = tip
    }

    fun setConfirmBtn(@IdRes tipRes: Int) {
        btn_confirm.setText(tipRes)
    }
}