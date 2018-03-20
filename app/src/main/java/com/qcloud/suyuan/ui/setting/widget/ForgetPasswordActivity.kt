package com.qcloud.suyuan.ui.setting.widget

import android.content.Context
import android.content.Intent
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.SwipeBaseActivity
import com.qcloud.suyuan.ui.setting.presenter.impl.IForgetPasswordPresenterImpl
import com.qcloud.suyuan.ui.setting.view.IForgetpasswordView

/**
 * 类型：ForgetPasswordActivity
 * Author: iceberg
 * Date: 2018/3/19.
 * 忘记密码页
 */
class ForgetPasswordActivity: SwipeBaseActivity<IForgetpasswordView, IForgetPasswordPresenterImpl>(),IForgetpasswordView {
    override val layoutId: Int
        get() = R.layout.activity_forget_password

    override fun initPresenter(): IForgetPasswordPresenterImpl? =IForgetPasswordPresenterImpl()

    override fun initViewAndData() {

    }
    companion object {
        fun openActivity(context: Context){
            var intent=Intent(context,ForgetPasswordActivity::class.java)
            context.startActivity(intent)
        }
    }
}