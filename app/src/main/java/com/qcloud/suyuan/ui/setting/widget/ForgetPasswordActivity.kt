package com.qcloud.suyuan.ui.setting.widget

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.ui.setting.presenter.impl.IForgetPasswordPresenterImpl
import com.qcloud.suyuan.ui.setting.view.IForgetpasswordView
import com.qcloud.suyuan.widgets.dialog.InputDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_forget_password.*
import org.jetbrains.annotations.NotNull
import java.util.concurrent.TimeUnit

/**
 * 类型：ForgetPasswordActivity
 * Author: iceberg
 * Date: 2018/3/19.
 * 忘记密码页
 */
class ForgetPasswordActivity : BaseActivity<IForgetpasswordView, IForgetPasswordPresenterImpl>(), IForgetpasswordView, View.OnClickListener {


    private var tipDialog: TipDialog? = null
    private var input: InputDialog? = null
    private var mobile: String = ""
    private var password: String = ""
    private var passwordConfrim: String = ""
    private var code: String = ""

    override val layoutId: Int
        get() = R.layout.activity_forget_password

    override fun initPresenter(): IForgetPasswordPresenterImpl? = IForgetPasswordPresenterImpl()

    override fun initViewAndData() {
        et_phone.setOnClickListener(this)
        et_code.setOnClickListener(this)
        et_password.setOnClickListener(this)
        et_password_confrim.setOnClickListener(this)
        btn_get_code.setOnClickListener(this)
        btn_edit_password.setOnClickListener(this)
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (tipDialog == null) {
            tipDialog = TipDialog(this)
        }
        if (isShow) {
            tipDialog?.setTip(errMsg)
            tipDialog?.show()
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.et_phone -> showInput(view as TextView?)
                R.id.et_code -> showInput(view as TextView?)
                R.id.et_password -> showInput(view as TextView?)
                R.id.et_password_confrim -> showInput(view as TextView?)
                R.id.btn_get_code -> getCodeClick();
                R.id.btn_edit_password -> confirmClick();
            }
        }
    }


    private fun getCodeClick() {
        mobile = et_phone.text.toString().trim()
        if (StringUtil.isBlank(mobile)) {
            loadErr(getString(R.string.et_hint_phone))
            et_phone.requestFocus()
            return
        }
        mPresenter?.getCode(mobile)
        startTime()
    }

    private fun confirmClick() {
        if (check()) {
            mPresenter?.forgetPassword(code, mobile, password)
        }
    }

    private fun check(): Boolean {
        mobile = et_phone.text.toString().trim()
        code = et_code.text.toString().trim()
        password = et_password.text.toString().trim()
        passwordConfrim = et_password_confrim.text.toString().trim()
        if (StringUtil.isBlank(mobile)) {
            loadErr(getString(R.string.et_hint_phone))
            et_phone.requestFocus()
            return false
        }
        if (StringUtil.isBlank(code)) {
            loadErr(getString(R.string.et_hint_code))
            et_code.requestFocus()
            return false
        }
        if (StringUtil.isBlank(password)) {
            loadErr(getString(R.string.et_hint_password))
            et_password.requestFocus()
            return false
        }
        if (StringUtil.isBlank(passwordConfrim)) {
            loadErr(getString(R.string.et_hint_2rd_password))
            et_password_confrim.requestFocus()
            return false
        }
        if (!StringUtil.contains(password, passwordConfrim)) {
            loadErr(getString(R.string.toast_password_no_same))
            return false
        }

        return true
    }

    fun showInput(@NotNull view: TextView?) {
        if (input == null) {
            input = InputDialog(this)
        }
        if (view != null) {
            input?.setBindView(view)
            input?.setInputValue(view.text.toString().trim())
        }

        input?.show()
    }

    /**
     * 得到验证码
     */
    override fun getCodeSuccess(){

    }

    override fun forgetPasswordSuccess() {
        val dialog = TipDialog(this)
        //dialog.setTip(R.string.tip_update_pw_suceess)
        dialog.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                finish()
            }
        }
        dialog.setTip(resources.getString(R.string.toast_success))
        dialog.show()
    }

    fun startTime() {
        Observable.interval(1, TimeUnit.SECONDS).take(60).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(Consumer { t ->
                    btn_get_code.setText(String.format(getString(R.string.tag_code_time), 60-t))
                    btn_get_code.setClickable(false)
                }, Consumer { }, Action {
                    btn_get_code.setText(getString(R.string.tag_get_code))
                    btn_get_code.setClickable(true)
                })
    }

    companion object {
        fun openActivity(context: Context) {
            var intent = Intent(context, ForgetPasswordActivity::class.java)
            context.startActivity(intent)
        }
    }
}