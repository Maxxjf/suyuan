package com.qcloud.suyuan.ui.user.widget

import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.SwipeBaseActivity
import com.qcloud.suyuan.ui.user.presenter.impl.IForgetPasswordPresenterImpl
import com.qcloud.suyuan.ui.user.view.IForgetpasswordView

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
}