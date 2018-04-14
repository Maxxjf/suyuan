package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.View
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.enums.PayMethod
import kotlinx.android.synthetic.main.dialog_pay_confirm.*

/**
 * Description: 支付确认弹窗
 * Author: gaobaiqiang
 * 2018/4/14 上午9:32.
 */
class PayConfirmDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {
    private var moneyStr = "%1$.2f"

    override val viewId: Int
        get() = R.layout.dialog_pay_confirm

    init {
        initView()
    }

    private fun initView() {
        btn_close.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
        btn_select_pay_method.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_close -> dismiss()
                R.id.btn_select_pay_method, R.id.btn_confirm -> {
                    dismiss()
                    onBtnClickListener?.onBtnClick(v)
                }
            }
        }
    }

    fun refreshPrice(payMethod: Int, totalAccount: Double) {
        tv_pay_method.text = PayMethod.getName(payMethod)
        if (payMethod == PayMethod.payCredit.key) {
            tv_pay_tag.setText(R.string.tag_dialog_credit)
        } else {
            tv_pay_tag.setText(R.string.tag_dialog_real_cash)
        }
        tv_real_price.text = String.format(moneyStr, totalAccount)
    }
}