package com.qcloud.suyuan.ui.record.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.record.presenter.impl.CreditRecordPresenterImpl
import com.qcloud.suyuan.ui.record.view.ICreditRecordView

/**
 * Description: 赊账记录
 * Author: gaobaiqiang
 * 2018/3/15 上午12:56.
 */
class CreditRecordActivity: BaseActivity<ICreditRecordView, CreditRecordPresenterImpl>(), ICreditRecordView {
    override val layoutId: Int
        get() = R.layout.activity_credit_record

    override fun initPresenter(): CreditRecordPresenterImpl? {
        return CreditRecordPresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, CreditRecordActivity::class.java))
        }
    }
}