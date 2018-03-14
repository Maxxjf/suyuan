package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.goods.presenter.impl.ModifyPricePresenterImpl
import com.qcloud.suyuan.ui.goods.view.IModifyPriceView

/**
 * Description: 修改售价
 * Author: gaobaiqiang
 * 2018/3/15 上午12:38.
 */
class ModifyPriceActivity: BaseActivity<IModifyPriceView, ModifyPricePresenterImpl>(), IModifyPriceView {
    override val layoutId: Int
        get() = R.layout.activity_modify_price

    override fun initPresenter(): ModifyPricePresenterImpl? {
        return ModifyPricePresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivty(@NonNull context: Context) {
            context.startActivity(Intent(context, ModifyPriceActivity::class.java))
        }
    }
}