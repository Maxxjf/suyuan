package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.View
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.qclib.utils.ValidateUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.IDBean
import com.qcloud.suyuan.widgets.pop.DropDownPop
import kotlinx.android.synthetic.main.dialog_input_purchase_info.*

/**
 * Description: 录入购买者信息
 * Author: gaobaiqiang
 * 2018/3/23 下午5:18.
 */
class InputPurchaseDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {
    var mobile: String? = null  // 购买者电话
    var purpose: String? = null // 购买用途
    var remark: String? = null  // 备注
    private var mPurchaseUsePop: DropDownPop? = null

    override val viewId: Int
        get() = R.layout.dialog_input_purchase_info

    init {
        initView()
    }

    private fun initView() {
        initDropDown()
        btn_close.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
    }

    /**
     * 初始化下拉弹窗
     * */
    private fun initDropDown() {
        val purchase = mContext.resources.getStringArray(R.array.purchase)
        val list: MutableList<String> = ArrayList()
        list.addAll(purchase)
        tv_purchase_use.text = list[0]
        btn_purchase_use.post {
            val width = btn_purchase_use.width
            mPurchaseUsePop = DropDownPop(mContext, list, width)

            mPurchaseUsePop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: Any?) {
                    if (value != null) {
                        tv_purchase_use.text = value.toString()
                    }
                }
            }
        }

        btn_purchase_use.setOnClickListener {
            mPurchaseUsePop?.showAsDropDown(btn_purchase_use)
        }
    }

    /**
     * 刷新用户信息
     * */
    fun refreshPurchase(bean: IDBean) {
        tv_user_name.text = bean.name
        tv_user_id.text = ValidateUtil.setIdCodeToPassword(bean.idCode)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> onMyDimission()
            R.id.btn_confirm -> {
                if (check()) {
                    onMyDimission()
                    onBtnClickListener?.onBtnClick(v)
                }
            }
        }
    }

    private fun check(): Boolean {
        val idCode = tv_user_id.text.toString().trim()
        val name = tv_user_name.text.toString().trim()
        mobile = et_mobile.text.toString().trim()
        purpose = tv_purchase_use.text.toString().trim()
        remark = et_other_instructions.text.toString()

        if (StringUtil.isBlank(idCode) || StringUtil.isBlank(name)) {
            QToast.show(mContext, R.string.toast_scan_idcard)
            return false
        }
        if (StringUtil.isBlank(mobile)) {
            QToast.show(mContext, R.string.hint_input_mobile)
            return false
        }

        return true
    }

    fun onMyDimission() {
        dismiss()
        mPurchaseUsePop.let {
            if (mPurchaseUsePop != null && mPurchaseUsePop!!.isShowing) {
                mPurchaseUsePop?.dismiss()
            }
        }
    }

    fun clearData() {
        tv_user_id.text = ""
        tv_user_name.text = ""
        et_mobile.setText("")
        initDropDown()
        et_other_instructions.setText("")
    }
}