package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.View
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_settlement.*

/**
 * Description: 结算弹窗
 * Author: gaobaiqiang
 * 2018/3/23 下午2:21.
 */
class SettlementDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener  {

    override val viewId: Int
        get() = R.layout.dialog_settlement

    init {
        initView()
    }

    private fun initView() {
        btn_close.setOnClickListener(this)
        btn_credit.setOnClickListener(this)
        btn_cash.setOnClickListener(this)
        btn_alipay.setOnClickListener(this)
        btn_wechat.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> dismiss()
            R.id.btn_credit, R.id.btn_cash, R.id.btn_alipay, R.id.btn_wechat -> {
                dismiss()
                onBtnClickListener?.onBtnClick(v)
            }
        }
    }

    fun refreshSettlementData() {

    }
}