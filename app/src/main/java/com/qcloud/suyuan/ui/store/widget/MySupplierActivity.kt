package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.store.presenter.impl.MySupplierPresenterImpl
import com.qcloud.suyuan.ui.store.view.IMySupplierView

/**
 * Description: 我的供应商
 * Author: gaobaiqiang
 * 2018/3/20 上午9:04.
 */
class MySupplierActivity: BaseActivity<IMySupplierView, MySupplierPresenterImpl>(), IMySupplierView {
    override val layoutId: Int
        get() = R.layout.activity_my_supplier

    override fun initPresenter(): MySupplierPresenterImpl? {
        return MySupplierPresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, MySupplierActivity::class.java))
        }
    }
}