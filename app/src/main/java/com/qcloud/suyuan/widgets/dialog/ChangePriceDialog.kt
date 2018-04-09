package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.utils.PriceInputFilter
import kotlinx.android.synthetic.main.dialog_change_price.*


/**
 * Description: 修改销售金额
 * Author: gaobaiqiang
 * 2018/3/24 下午4:32.
 */
class ChangePriceDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {

    var price: Double = 0.00

    override val viewId: Int
        get() = R.layout.dialog_change_price

    init {
        initView()
    }

    private fun initView() {
        val filters = arrayOf<InputFilter>(PriceInputFilter())
        et_price.filters = filters

        et_price.setOnKeyListener { v, action, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_UP) {
                if (action == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtil.hideKeybord(mContext, et_price)
                    if (check()) {
                        dismiss()
                        onBtnClickListener?.onBtnClick(v)
                    }
                }
            }
            false
        }

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
        val priceStr = et_price.text.toString().trim()
        if (StringUtil.isEmpty(priceStr)) {
            QToast.show(mContext, R.string.toast_input_price)
            return false
        }

        price = priceStr.toDouble()
        return true
    }

    fun clearInput() {
        et_price.setText("")
    }
}