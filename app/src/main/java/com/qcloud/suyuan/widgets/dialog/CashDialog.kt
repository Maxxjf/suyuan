package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_cash.*

/**
 * Description: 现金结算
 * Author: gaobaiqiang
 * 2018/3/23 下午3:41.
 */
class CashDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {

    private var totalAccount: Double = 0.00
    var realPay: Double = 0.0
    var giveMoney: Double = 0.0
    private var moneyStr = "%1$.2f元"

    override val viewId: Int
        get() = R.layout.dialog_cash

    init {
        initView()
    }

    private fun initView() {
        btn_close.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)

        et_discount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable) {

            }

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                val priceStr = p0.toString().trim()
                if (StringUtil.isMoneyStr(priceStr)) {
                    realPay = priceStr.toDouble()
                    giveMoney = realPay - totalAccount
                    if (giveMoney > 0) {
                        tv_give_change.text = String.format(moneyStr, giveMoney)
                    }
                }
            }

        })
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

    fun refreshData(realPay: Double) {
        this.totalAccount = realPay
        this.realPay = realPay
        tv_real_price.text = String.format(moneyStr, realPay)
    }
}