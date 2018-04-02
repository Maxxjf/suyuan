package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.store.presenter.impl.MyProductDetailsPresenterImpl
import com.qcloud.suyuan.ui.store.view.IMyProductDetailsView
import kotlinx.android.synthetic.main.card_my_product_info.*

/**
 * Description: 我的产品详情
 * Author: gaobaiqiang
 * 2018/4/1 下午5:16.
 */
class MyProductDetailsActivity: BaseActivity<IMyProductDetailsView, MyProductDetailsPresenterImpl>(), IMyProductDetailsView {
    override val layoutId: Int
        get() = R.layout.activity_my_product_details

    override fun initPresenter(): MyProductDetailsPresenterImpl? {
        return MyProductDetailsPresenterImpl()
    }

    override fun initViewAndData() {

    }

    private fun refreshData() {
        tv_product_bar_code.text = ""
        tv_product_name.text = ""
        tv_product_spec.text = ""
        tv_product_classify.text = ""
        tv_product_unit.text = ""
        tv_product_retail_price.text = ""

        tv_pesticides_registration.text = ""
        tv_production_license.text = ""
        tv_product_standard.text = ""
        tv_mobile.text = ""

        tv_factory.text = ""
        tv_factory_address.text = ""
    }

    companion object {
        fun openActivity(@NonNull context: Context, id: String) {
            val intent = Intent(context, MyProductDetailsActivity::class.java)
            intent.putExtra("ID", id)
            context.startActivity(intent)
        }
    }
}