package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.store.presenter.impl.StoreInfoPresenterImpl
import com.qcloud.suyuan.ui.store.view.IStoreInfoView

/**
 * Description: 门店信息
 * Author: gaobaiqiang
 * 2018/3/20 上午9:06.
 */
class StoreInfoActivity: BaseActivity<IStoreInfoView, StoreInfoPresenterImpl>(), IStoreInfoView {
    override val layoutId: Int
        get() = R.layout.activity_store_info

    override fun initPresenter(): StoreInfoPresenterImpl? {
        return StoreInfoPresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, StoreInfoActivity::class.java))
        }
    }
}