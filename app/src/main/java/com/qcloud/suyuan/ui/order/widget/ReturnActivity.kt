package com.qcloud.suyuan.ui.order.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.order.presenter.impl.ReturnPresenterImpl
import com.qcloud.suyuan.ui.order.view.IReturnView

/**
 * Description:  退货
 * Author: gaobaiqiang
 * 2018/3/20 上午8:50.
 */
class ReturnActivity: BaseActivity<IReturnView, ReturnPresenterImpl>(), IReturnView {
    override val layoutId: Int
        get() = R.layout.activity_return

    override fun initPresenter(): ReturnPresenterImpl? {
        return ReturnPresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, ReturnActivity::class.java))
        }
    }
}