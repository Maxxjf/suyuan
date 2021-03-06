package com.qcloud.suyuan.widgets.dialog

import android.app.Dialog
import android.content.Context
import android.support.annotation.NonNull
import android.support.annotation.StyleRes
import android.view.Gravity
import android.view.View
import com.qcloud.suyuan.R
import kotlinx.android.synthetic.main.dialog_repayment_all.*

/**
 * 类型：RepaymentAllDialog
 * Author: iceberg
 * Date: 2018/3/26.
 * 全部还款的弹框
 */
class RepaymentAllDialog @JvmOverloads constructor(@NonNull private val mContext: Context,
                                                   @StyleRes themeResId: Int = R.style.InputDialog) :
        Dialog(mContext, themeResId), View.OnClickListener {
    var onConfirmClickListener: OnConfirmClickListener? = null

    init {
        setContentView(R.layout.dialog_repayment_all)
        initDialog()
        initView()
    }

    private fun initView() {
        btn_close.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
    }

    private fun initDialog() {
        val lp = window!!.attributes
        lp.gravity = Gravity.CENTER
        window!!.attributes = lp
        setCancelable(true)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_close -> dismiss()
            R.id.btn_confirm -> {
                dismiss()
                onConfirmClickListener?.onConfirmClick()
            }
        }
    }

    fun setMoney(moneyStr: String) {
        et_repayment_money.setText(moneyStr)
//        et_repayment_money.setSelection(moneyStr.length)
    }

    fun getMoney(): String {
        return et_repayment_money.text.toString().trim();
    }

    interface OnConfirmClickListener {
        fun onConfirmClick()//点击确认
    }
}



