package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.store.presenter.impl.CreateProductIPresenterImpl
import com.qcloud.suyuan.ui.store.view.ICreateProductIView

/**
 * Description: 创建产品第一步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:56.
 */
class CreateProductIActivity: BaseActivity<ICreateProductIView, CreateProductIPresenterImpl>(), ICreateProductIView {
    override val layoutId: Int
        get() = 0

    override fun initPresenter(): CreateProductIPresenterImpl? {
        return CreateProductIPresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, CreateProductIActivity::class.java))
        }
    }
}