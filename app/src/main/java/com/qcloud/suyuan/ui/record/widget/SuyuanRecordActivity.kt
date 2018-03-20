package com.qcloud.suyuan.ui.record.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.record.presenter.impl.SuyuanRecordPresenterImpl
import com.qcloud.suyuan.ui.record.view.ISuyuanRecordView

/**
 * Description: 溯源记录
 * Author: gaobaiqiang
 * 2018/3/20 上午8:54.
 */
class SuyuanRecordActivity: BaseActivity<ISuyuanRecordView, SuyuanRecordPresenterImpl>(), ISuyuanRecordView {
    override val layoutId: Int
        get() = R.layout.activity_suyuan_record

    override fun initPresenter(): SuyuanRecordPresenterImpl? {
        return SuyuanRecordPresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SuyuanRecordActivity::class.java))
        }
    }
}