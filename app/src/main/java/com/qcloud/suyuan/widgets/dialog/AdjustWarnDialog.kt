package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_adjust_warn.*

/**
 * Description: 调整警告线
 * Author: gaobaiqiang
 * 2018/3/24 下午4:32.
 */
class AdjustWarnDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {

    var warnLine: Int = 0

    override val viewId: Int
        get() = R.layout.dialog_adjust_warn

    init {
        initView()
    }

    private fun initView() {
        et_warn.setOnKeyListener { v, action, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_UP) {
                if (action == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtil.hideKeybord(mContext, et_warn)
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
        val warnLineStr = et_warn.text.toString().trim()
        if (StringUtil.isEmpty(warnLineStr)) {
            QToast.show(mContext, R.string.toast_input_warn_line)
            return false
        }

        warnLine = warnLineStr.toInt()
        return true
    }

    fun clearInput() {
        et_warn.setText("")
    }
}