package com.qcloud.suyuan.ui.main.widget

import com.qcloud.qclib.utils.StringUtil
import com.qcloud.qclib.utils.SystemBarUtil
import com.qcloud.qclib.utils.TokenUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.main.presenter.impl.LaunchPresenterImpl
import com.qcloud.suyuan.ui.main.view.ILaunchView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Description: 启动页
 * Author: gaobaiqiang
 * 2018/3/14 上午9:40.
 */
class LaunchActivity: BaseActivity<ILaunchView, LaunchPresenterImpl>(), ILaunchView {
    override val layoutId: Int
        get() = R.layout.activity_launch

    override fun initPresenter(): LaunchPresenterImpl? {
        return LaunchPresenterImpl()
    }

    override fun initViewAndData() {
        SystemBarUtil.hideNavBar(this)
        startTimer()
    }

    override val translucentStatusBar: Boolean
        get() = true

    private fun startTimer() {
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (StringUtil.isBlank(TokenUtil.getToken())) {
                        toLogin()
                    } else {
                        toMain()
                    }
                }
    }

    private fun toMain() {
        MainActivity.openActivity(this)
        finish()
    }
    private fun toLogin(){
        LoginActivity.openActivity(this)
        finish()
    }
}