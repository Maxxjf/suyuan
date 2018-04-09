package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.enums.PayMethod
import kotlinx.android.synthetic.main.dialog_settlement.*

/**
 * Description: 结算弹窗
 * Author: gaobaiqiang
 * 2018/3/23 下午2:21.
 */
class SettlementDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener  {

    // 总额
    private var totalAccount: Double = 0.00
    // 实收金额
    var realPay: Double = 0.00
    var payMethod: Int = 1

    private var moneyStr = "%1$.2f元"

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

        et_discount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable) {

            }

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                val priceStr = p0.toString().trim()
                if (StringUtil.isMoneyStr(priceStr)) {
                    val price = priceStr.toDouble()
                    if (price > totalAccount) {
                        QToast.show(mContext, R.string.toast_discount_less_then_account)
                        et_discount.setText("")
                        realPay = totalAccount
                        tv_real_price.text = String.format(moneyStr, realPay)
                    } else {
                        realPay = totalAccount - price
                        tv_real_price.text = String.format(moneyStr, realPay)
                    }
                }
            }

        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> dismiss()
            R.id.btn_credit -> {
                payMethod = PayMethod.payCredit.key
                dismiss()
                onBtnClickListener?.onBtnClick(v)
            }
            R.id.btn_cash -> {
                payMethod = PayMethod.payByCash.key
                dismiss()
                onBtnClickListener?.onBtnClick(v)
            }
            R.id.btn_alipay -> {
                payMethod = PayMethod.payByAlipay.key
                dismiss()
                onBtnClickListener?.onBtnClick(v)
            }
            R.id.btn_wechat -> {
                payMethod = PayMethod.payByWechat.key
                dismiss()
                onBtnClickListener?.onBtnClick(v)
            }
        }
    }

    fun refreshSettlementData(totalNum: Int, totalAccount: Double) {
        this.totalAccount = totalAccount
        this.realPay = totalAccount
        tv_goods_number.text = totalNum.toString()
        tv_total_account.text = String.format(moneyStr, totalAccount)
        tv_real_price.text = String.format(moneyStr, totalAccount)
    }
}