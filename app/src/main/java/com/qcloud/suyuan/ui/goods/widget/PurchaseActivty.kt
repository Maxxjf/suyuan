package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.goods.presenter.impl.PurchasePresenterImpl
import com.qcloud.suyuan.ui.goods.view.IPurchaseView

/**
 * Description: 进货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:33.
 */
class PurchaseActivty: BaseActivity<IPurchaseView, PurchasePresenterImpl>(), IPurchaseView {
    override val layoutId: Int
        get() = R.layout.activity_purchase

    override fun initPresenter(): PurchasePresenterImpl? {
        return PurchasePresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivty(@NonNull context: Context) {
            context.startActivity(Intent(context, PurchaseActivty::class.java))
        }
    }
}