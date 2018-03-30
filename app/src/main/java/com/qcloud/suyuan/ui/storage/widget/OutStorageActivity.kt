package com.qcloud.suyuan.ui.storage.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.storage.presenter.impl.OutStoragePresenterImpl
import com.qcloud.suyuan.ui.storage.view.IOutStorageView

/**
 * Description: 撤消入库
 * Author: gaobaiqiang
 * 2018/3/15 上午12:46.
 */
class OutStorageActivity: BaseActivity<IOutStorageView, OutStoragePresenterImpl>(), IOutStorageView {
    override val layoutId: Int
        get() = R.layout.activity_out_storage

    override fun initPresenter(): OutStoragePresenterImpl? {
        return OutStoragePresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, OutStorageActivity::class.java))
        }
    }
}