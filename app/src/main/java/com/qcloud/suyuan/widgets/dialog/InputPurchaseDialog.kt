package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.View
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_input_purchase_info.*

/**
 * Description: 录入购买者信息
 * Author: gaobaiqiang
 * 2018/3/23 下午5:18.
 */
class InputPurchaseDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {
    override val viewId: Int
        get() = R.layout.dialog_input_purchase_info

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

}