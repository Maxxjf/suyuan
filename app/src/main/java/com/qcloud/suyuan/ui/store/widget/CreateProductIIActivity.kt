package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.store.presenter.impl.CreateProductIIPresenterImpl
import com.qcloud.suyuan.ui.store.view.ICreateProductIIView

/**
 * Description: 创建产品第二步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:59.
 */
class CreateProductIIActivity: BaseActivity<ICreateProductIIView, CreateProductIIPresenterImpl>(), ICreateProductIIView {
    override val layoutId: Int
        get() = 0

    override fun initPresenter(): CreateProductIIPresenterImpl? {
        return CreateProductIIPresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            val intent = Intent(context, CreateProductIIActivity::class.java)
            context.startActivity(intent)
        }
    }
}