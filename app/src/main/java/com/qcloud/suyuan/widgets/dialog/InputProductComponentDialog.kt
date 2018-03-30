package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.View
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_input_product_component.*

/**
 * Description: 输入产品成分弹窗
 * Author: gaobaiqiang
 * 2018/3/30 下午5:29.
 */
class InputProductComponentDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {
    override val viewId: Int
        get() = R.layout.dialog_input_product_component

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

    fun initData(attrValue: String?) {

    }

    fun setTitle(title: String?) {
        tv_title.text = title
    }
}