package com.qcloud.suyuan.ui.main.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.ui.main.presenter.ILoginPresenter
import com.qcloud.suyuan.ui.main.view.ILoginView
import timber.log.Timber

/**
 * 类型：LoginPresenterImpl
 * Author: iceberg
 * Date: 2018/3/15.
 * 登录页
 */
class LoginPresenterImpl: BasePresenter<ILoginView>(),ILoginPresenter {
    override fun login(account: String, password: String) {
        Timber.e("账号：${account},密码：${password}")
       if (account.contains("1")){
           //模拟
           mView?.loginSuccess()
       }else{
           mView?.loadErr("登录错误")
       }
    }

    override fun onBtnClick(viewId: Int) {
        when(viewId){
            R.id.btn_login -> mView?.loginBtnClick()
        }
    }
}