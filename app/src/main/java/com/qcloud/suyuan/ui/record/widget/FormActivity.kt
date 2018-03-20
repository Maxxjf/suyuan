package com.qcloud.suyuan.ui.record.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.record.presenter.impl.FormPresenterImpl
import com.qcloud.suyuan.ui.record.view.IFormView

/**
 * Description: 报表
 * Author: gaobaiqiang
 * 2018/3/20 上午8:59.
 */
class FormActivity: BaseActivity<IFormView, FormPresenterImpl>(), IFormView {
    override val layoutId: Int
        get() = R.layout.activity_form

    override fun initPresenter(): FormPresenterImpl? {
        return FormPresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, FormActivity::class.java))
        }
    }
}