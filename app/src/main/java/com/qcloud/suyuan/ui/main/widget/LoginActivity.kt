package com.qcloud.suyuan.ui.main.widget

import android.content.Context
import android.content.Intent
import android.view.View
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.SharedUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.constant.ShareConstants
import com.qcloud.suyuan.ui.main.presenter.impl.LoginPresenterImpl
import com.qcloud.suyuan.ui.main.view.ILoginView
import com.qcloud.suyuan.ui.shop.widget.CartActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 类型：LoginActivity
 * Author: iceberg
 * Date: 2018/3/15.
 * 登录页
 */
class LoginActivity : BaseActivity<ILoginView, LoginPresenterImpl>(), ILoginView {


    private var account: String = ""
    private var password: String = ""

    override val layoutId: Int
        get() = R.layout.activity_login

    override fun initPresenter(): LoginPresenterImpl? = LoginPresenterImpl()

    override val translucentStatusBar: Boolean
        get() = true

    override fun initViewAndData() {
        et_account.setText(SharedUtil.getString(ShareConstants.account))
        cb_rember_password.setOnCheckedChangeListener { button, isCheck ->
            {
                if (isCheck) {
                    account = et_account.text.toString().trim()
                    loadErr("${account}", true)
                    SharedUtil.writeString(ShareConstants.account, account)
                } else {
                    SharedUtil.writeString(ShareConstants.account, "")
                }
            }
        }
        btn_login.setOnClickListener(View.OnClickListener { view: View? ->
            if (view != null) {
                mPresenter?.onBtnClick(view.id)
            }
        })
    }


    override fun loginBtnClick() {
        if (check()) {
            startLoadingDialog()
            mPresenter?.login(account, password)
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        stopLoadingDialog()
        if (isShow) {
            QToast.show(mContext, errMsg)
        }
    }

    override fun loginSuccess() {
        stopLoadingDialog()
        CartActivity.openActivity(this)
    }

    private fun check(): Boolean {
        account = et_account.text.toString().trim()
        password = et_password.text.toString().trim()
        if (StringUtil.isBlank(account)) {
            QToast.show(this, R.string.et_hint_account, 1000)
            et_account.requestFocus()
            return false
        }
        if (StringUtil.isBlank(password)) {
            QToast.show(this, R.string.et_hint_password, 1000)
            et_password.requestFocus()
            return false
        }

        return true
    }

    companion object {
        fun openActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}