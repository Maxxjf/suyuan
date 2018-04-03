package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.qclib.toast.QToast
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.ProductDetailsBean
import com.qcloud.suyuan.ui.store.presenter.impl.MyProductDetailsPresenterImpl
import com.qcloud.suyuan.ui.store.view.IMyProductDetailsView
import kotlinx.android.synthetic.main.card_my_product_info.*
import kotlinx.android.synthetic.main.card_my_product_introduce.*
import timber.log.Timber

/**
 * Description: 我的产品详情
 * Author: gaobaiqiang
 * 2018/4/1 下午5:16.
 */
class MyProductDetailsActivity: BaseActivity<IMyProductDetailsView, MyProductDetailsPresenterImpl>(), IMyProductDetailsView {
    private var currId: String? = null
    private var currBean: ProductDetailsBean? = null

    override val layoutId: Int
        get() = R.layout.activity_my_product_details

    override fun initPresenter(): MyProductDetailsPresenterImpl? {
        return MyProductDetailsPresenterImpl()
    }

    override fun initViewAndData() {
        currId = intent.getStringExtra("ID")

        btn_create_product.setOnClickListener {
            CreateProductIActivity.openActivity(this, currId)
        }
        mPresenter?.loadData(currId ?: "")
    }

    override fun refreshData(bean: ProductDetailsBean) {
        if (isRunning) {
            currBean = bean
            setProductData(bean)
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isRunning) {
            if (isShow) {
                QToast.show(this, errMsg)
            } else {
                Timber.e(errMsg)
            }
        }
    }

    private fun setProductData(bean: ProductDetailsBean) {
        val goodsBean = bean.goods
        if (goodsBean != null) {
            with(goodsBean) {
                tv_product_bar_code.text = barCode
                tv_product_classify.text = classifyName
            }
        }
        val infoBean = bean.info
        if (infoBean != null) {
            with(infoBean) {
                tv_product_name.text = name
                tv_product_spec.text = specification
                tv_product_unit.text = unit
                tv_pesticides_registration.text = registerCard
                tv_production_license.text = licenseCard
                tv_product_standard.text = standardCard
                tv_product_introduce.text = details
            }
        }
        val millBean = bean.mill
        if (millBean != null) {
            with(millBean) {
                tv_mobile.text = tel
                tv_factory.text = name
                tv_factory_address.text = address
            }
        }
        val moneyStr = "%1$.2f元"
        tv_product_retail_price.text = String.format(moneyStr, bean.price)
    }

    companion object {
        fun openActivity(@NonNull context: Context, id: String?) {
            val intent = Intent(context, MyProductDetailsActivity::class.java)
            intent.putExtra("ID", id)
            context.startActivity(intent)
        }
    }
}