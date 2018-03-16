package com.qcloud.suyuan.ui.record.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.record.presenter.impl.ReturnRecordPresenterImpl
import com.qcloud.suyuan.ui.record.view.IReturnRecordView

/**
 * Description: 退货记录
 * Author: gaobaiqiang
 * 2018/3/15 上午1:01.
 */
class ReturnRecordActivity: BaseActivity<IReturnRecordView, ReturnRecordPresenterImpl>(), IReturnRecordView {
    override val layoutId: Int
        get() = R.layout.activity_return_record

    override fun initPresenter(): ReturnRecordPresenterImpl? {
        return ReturnRecordPresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, ReturnRecordActivity::class.java))
        }
    }
}