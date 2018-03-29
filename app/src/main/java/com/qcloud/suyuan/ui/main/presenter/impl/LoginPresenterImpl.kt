package com.qcloud.suyuan.ui.main.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.utils.TokenUtil
import com.qcloud.suyuan.beans.LoginReturnBean
import com.qcloud.suyuan.model.impl.UserModelImpl
import com.qcloud.suyuan.ui.main.presenter.ILoginPresenter
import com.qcloud.suyuan.ui.main.view.ILoginView
import com.qcloud.suyuan.utils.UserInfoUtil

/**
 * 类型：LoginPresenterImpl
 * Author: iceberg
 * Date: 2018/3/15.
 * 登录页
 */
class LoginPresenterImpl : BasePresenter<ILoginView>(), ILoginPresenter {
    private var mUserModel: UserModelImpl = UserModelImpl()

    override fun login(account: String, password: String) {
        mUserModel.login(account, password, object : DataCallback<LoginReturnBean> {

            override fun onSuccess(bean: LoginReturnBean?, message: String?) {
                if (bean != null && bean.loginState == 1) {
                    bean.let {
                        TokenUtil.saveToken(it.token)
                        UserInfoUtil.saveUser(it.user)
                        UserInfoUtil.loadStore()
                    }
                    mView?.loginSuccess()
                } else {
                    mView?.loadErr(message ?: "登录失败")
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }

        })
    }

}