package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.store.presenter.impl.StoreProductPresenterImpl
import com.qcloud.suyuan.ui.store.view.IStoreProductView

/**
 * Description: 门店产品
 * Author: gaobaiqiang
 * 2018/3/15 上午12:51.
 */
class StoreProductActivity: BaseActivity<IStoreProductView, StoreProductPresenterImpl>(), IStoreProductView {
    override val layoutId: Int
        get() = R.layout.activity_store_product

    override fun initPresenter(): StoreProductPresenterImpl? {
        return StoreProductPresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, StoreProductActivity::class.java))
        }
    }
}