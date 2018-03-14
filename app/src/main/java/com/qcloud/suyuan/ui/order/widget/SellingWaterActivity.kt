package com.qcloud.suyuan.ui.order.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.order.presenter.impl.SellingWaterPresenterImpl
import com.qcloud.suyuan.ui.order.view.ISellingWaterView

/**
 * Description: 销售流水
 * Author: gaobaiqiang
 * 2018/3/15 上午12:42.
 */
class SellingWaterActivity: BaseActivity<ISellingWaterView, SellingWaterPresenterImpl>(), ISellingWaterView {
    override val layoutId: Int
        get() = R.layout.activity_selling_water

    override fun initPresenter(): SellingWaterPresenterImpl? {
        return SellingWaterPresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SellingWaterActivity::class.java))
        }
    }
}