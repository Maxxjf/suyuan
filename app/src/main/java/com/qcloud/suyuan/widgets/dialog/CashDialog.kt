package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.View
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_input_purchase_info.*

/**
 * Description: 现金结算
 * Author: gaobaiqiang
 * 2018/3/23 下午3:41.
 */
class CashDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {

    override val viewId: Int
        get() = R.layout.dialog_cash

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

    fun refreshData() {

    }
}