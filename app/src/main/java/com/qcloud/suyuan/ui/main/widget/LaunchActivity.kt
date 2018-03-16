package com.qcloud.suyuan.ui.main.widget

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
        startTimer()
    }

    private fun startTimer() {
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { toLogin() }
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