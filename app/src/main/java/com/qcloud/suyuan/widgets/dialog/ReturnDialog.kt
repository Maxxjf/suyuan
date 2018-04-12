package com.qcloud.suyuan.widgets.dialog

import android.app.Dialog
import android.content.Context
import android.support.annotation.NonNull
import android.support.annotation.StyleRes
import android.view.Gravity
import android.view.View
import com.qcloud.suyuan.R
import kotlinx.android.synthetic.main.dialog_return_good.*


/**
 * Description: 退货确认弹框
 * Author: iceberg
 * 2018/3/23
 */
class ReturnDialog @JvmOverloads constructor(
        @NonNull private val mContext: Context,
        @StyleRes themeResId: Int = R.style.InputDialog) : Dialog(mContext, themeResId), View.OnClickListener {

    var onConfirmClickListener: OnConfirmClickListener? = null

    init {
        setContentView(R.layout.dialog_return)
        initDialog()
        initView()
    }

    private fun initDialog() {
        val lp = window!!.attributes
        lp.gravity = Gravity.CENTER
        window!!.attributes = lp
        setCancelable(true)
    }

    private fun initView() {
        btn_close.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
        //设置Input的类型两种都要

//        et_return_money.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
//设置字符过滤
//        et_return_money.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
//            if (source == "." && dest.toString().isEmpty()) {
//                return@InputFilter "0."
//            }
//            if (dest.toString().contains(".")) {
//                val index = dest.toString().indexOf(".")
//                val length = dest.toString().substring(index).length
//                if (length == 3) {
//                    return@InputFilter ""
//                }
//            }
//            null
//        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> dismiss()
            R.id.btn_confirm -> {
                dismiss()
                onConfirmClickListener?.onConfirmClick()
            }
        }
    }

    fun setNumber(number: Int) {
        tv_return_number.text = "${number}"
    }

    fun getMoney(): String {
        return et_return_money.text.toString()
    }


    interface OnConfirmClickListener {
        fun onConfirmClick()
    }
}