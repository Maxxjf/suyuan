package com.qcloud.suyuan.ui.shop.widget

import android.content.Context
import android.content.Intent
import com.qcloud.qclib.toast.QToast
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.shop.presenter.impl.CartPresenterImpl
import com.qcloud.suyuan.ui.shop.view.ICartView

/**
 * 类型：CartActivity
 * Author: iceberg
 * Date: 2018/3/15.
 * 购物车
 */
class CartActivity: BaseActivity<ICartView, CartPresenterImpl>(),ICartView {
    override fun loadErr(errMsg: String, isShow: Boolean) {
       if (isShow){
           QToast.show(mContext,errMsg)
       }
    }

    override val layoutId: Int
        get() = R.layout.activity_cart

    override fun initPresenter(): CartPresenterImpl? = CartPresenterImpl()

    override fun initViewAndData() {

    }
    companion object {
        fun openActivity(context: Context){
            val intent= Intent(context,CartActivity::class.java)
            context.startActivity(intent)
        }
    }

}