package com.qcloud.suyuan.widgets.customview

import android.content.Context
import android.util.AttributeSet
import com.qcloud.qclib.base.BaseLinearLayout
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.widgets.dialog.InputDialog
import kotlinx.android.synthetic.main.layout_modify_price.view.*

/**
 * 类说明：修改售价
 * Author: Kuzan
 * Date: 2018/3/29 20:14.
 */
class ModifyPriceView  @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : BaseLinearLayout(context, attrs, defStyleAttr) {

    private var inputDialog: InputDialog? = null
    private var currBean: ProductBean? = null

    var onConfirmListener: OnConfirmListener? = null

    override val viewId: Int
        get() = R.layout.layout_modify_price

    override fun initViewAndData() {
        et_value.setHint(R.string.hint_input_sell_price)
        et_value.setOnClickListener {
            if (inputDialog == null) {
                initInputDialog()
            }
            inputDialog?.setInputValue(et_value.text.toString().trim())
            inputDialog?.show()
        }
    }

    private fun initInputDialog() {
        inputDialog = InputDialog(mContext)
        inputDialog?.setBindView(et_value)
        inputDialog?.setMaxLength(7)
        inputDialog?.onFinishInputListener = object : InputDialog.OnFinishInputListener {
            override fun onFinishInput(message: String?) {
                if (check(message)) {
                    onConfirmListener?.onConfirm(currBean)
                }
            }
        }
    }

    private fun check(priceStr: String?): Boolean {
        if (StringUtil.isBlank(priceStr)) {
            QToast.show(mContext, R.string.toast_input_price)
            return false
        }
        if (!StringUtil.isMoneyStr(priceStr)) {
            QToast.show(mContext, R.string.toast_input_price)
            return false
        }
        val price = priceStr!!.toDouble()
        if (price <= 0) {
            QToast.show(mContext, R.string.toast_input_price)
            return false
        }
        currBean?.retailPrice = price
        return true
    }

    fun initData(bean: ProductBean) {
        currBean = bean
        et_value.text = bean.retailPriceStr
    }

    interface OnConfirmListener {
        fun onConfirm(bean: ProductBean?)
    }
}