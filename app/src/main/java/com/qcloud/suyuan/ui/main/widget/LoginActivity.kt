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
import com.qcloud.suyuan.constant.ShareConstants
import com.qcloud.suyuan.ui.main.presenter.impl.LoginPresenterImpl
import com.qcloud.suyuan.ui.main.view.ILoginView
import com.qcloud.suyuan.ui.setting.widget.ForgetPasswordActivity
import com.qcloud.suyuan.widgets.dialog.InputDialog
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.annotations.NotNull
import timber.log.Timber

/**
 * 类型：LoginActivity
 * Author: iceberg
 * Date: 2018/3/15.
 * 登录页
 */
class LoginActivity : BaseActivity<ILoginView, LoginPresenterImpl>(), ILoginView, View.OnClickListener {


    private var account: String = "" //账号
    private var password: String = "" //密码
    private var input: InputDialog? =null
    override val layoutId: Int
        get() = R.layout.activity_login

    override fun initPresenter(): LoginPresenterImpl? = LoginPresenterImpl()

    override val translucentStatusBar: Boolean
        get() = true

    override fun initViewAndData() {
        initData()
        initListener();

    }

    private fun initData() {
        et_account.setText(SharedUtil.getString(ShareConstants.account))
    }

    private fun initListener() {
        cb_rember_password.setOnCheckedChangeListener { button, isCheck ->
            {
                Timber.e("isCheck:${isCheck}")
                if (isCheck) {
                    account = et_account.text.toString().trim()
                    loadErr("${account}", true)
                    SharedUtil.writeString(ShareConstants.account, account)
                } else {
                    SharedUtil.writeString(ShareConstants.account, "")
                }
            }
        }
        btn_forget_password.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        et_account.setOnClickListener(this)
        et_password.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        if (view != null) {
            when(view.id){
                R.id.et_account ->showInput(view as TextView?)
                R.id.et_password ->showInput(view as TextView?)
                R.id.btn_forget_password ->forgetBtnClick()
                R.id.btn_login ->loginBtnClick()
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
        stopLoadingDialog()
        MainActivity.openActivity(this)
    }

    private fun check(): Boolean {
        account = et_account.text.toString().trim()
        password = et_password.text.toString().trim()
        if (StringUtil.isBlank(account)) {

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
        if (input==null){
            input = InputDialog(this)
        }
        if (view != null) {
            input?.setBindView(view)
            input?.setInputValue(view.text.toString().trim())
        }

        input?.show()
    }

    companion object {
        fun openActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}