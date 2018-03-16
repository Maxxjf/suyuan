package com.qcloud.suyuan.ui.main.widget

import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.main.presenter.impl.MainPresenterImpl
import com.qcloud.suyuan.ui.main.view.IMainView

/**
 * 类说明：主页
 * Author: Kuzan
 * Date: 2018/3/12 15:29.
 */
class MainActivity: BaseActivity<IMainView, MainPresenterImpl>(), IMainView {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initPresenter(): MainPresenterImpl? {
        return MainPresenterImpl()
    }

    override fun initViewAndData() {

    }
}