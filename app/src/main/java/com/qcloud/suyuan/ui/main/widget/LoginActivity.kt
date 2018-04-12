package com.qcloud.suyuan.ui.main.widget

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.SharedUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.main.presenter.impl.LoginPresenterImpl
import com.qcloud.suyuan.ui.main.view.ILoginView
import com.qcloud.suyuan.ui.setting.widget.ForgetPasswordActivity
import com.qcloud.suyuan.widgets.dialog.InputDialog
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.annotations.NotNull

/**
 * 类型：LoginActivity
 * Author: iceberg
 * Date: 2018/3/15.
 * 登录页
 */
class LoginActivity : BaseActivity<ILoginView, LoginPresenterImpl>(), ILoginView, View.OnClickListener {

    private var account: String = "" //账号
    private var password: String = "" //密码
    private var inputDialog: InputDialog? = null
    override val layoutId: Int
        get() = R.layout.activity_login

    override fun initPresenter(): LoginPresenterImpl? = LoginPresenterImpl()

    override val translucentStatusBar: Boolean
        get() = true

    override fun initViewAndData() {
        initData()
        initListener()
    }

    private fun initData() {
        cb_rember_password.isChecked = SharedUtil.getBoolean(AppConstants.isCheck)
        if (cb_rember_password.isChecked){
            et_account.text = SharedUtil.getString(AppConstants.account)
            et_password.text = SharedUtil.getString(AppConstants.password)
        }
    }

    private fun initListener() {
        btn_forget_password.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        et_account.setOnClickListener(this)
        et_password.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.et_account -> showInput(view as TextView?)
                R.id.et_password -> showInput(view as TextView?)
                R.id.btn_forget_password -> forgetBtnClick()
                R.id.btn_login -> loginBtnClick()
            }
        }
    }

    override fun loginBtnClick() {
        if (check()) {
            startLoadingDialog()
            mPresenter?.login(account, password)
        }
    }

    override fun forgetBtnClick() {
        ForgetPasswordActivity.openActivity(this)
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        stopLoadingDialog()
        if (isShow) {
            QToast.show(mContext, errMsg)
        }
    }

    override fun loginSuccess() {
        if (cb_rember_password.isChecked) {
            SharedUtil.writeString(AppConstants.account, account)
            SharedUtil.writeString(AppConstants.password, password)
        } else {
//            SharedUtil.writeString(AppConstants.account, "")
            SharedUtil.writeString(AppConstants.password, "")
        }
        SharedUtil.writeBoolean(AppConstants.isCheck, cb_rember_password.isChecked)

        stopLoadingDialog()
        MainActivity.openActivity(this)
        finish()
    }

    private fun check(): Boolean {
        account = et_account.text.toString().trim()
        password = et_password.text.toString().trim()
        if (StringUtil.isBlank(account)) {
            QToast.show(this, R.string.et_hint_account)
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

    override fun showInput(@NotNull view: TextView?) {
        if (inputDialog == null) {
            inputDialog = InputDialog(this)
        }
        if (view != null) {
            inputDialog?.setBindView(view)
            inputDialog?.setInputValue(view.text.toString().trim())
        }

        inputDialog?.show()
    }

    companion object {
        fun openActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}