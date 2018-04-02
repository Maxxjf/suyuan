package com.qcloud.suyuan.ui.search.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.View
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.search.presenter.impl.SearchProductPresenterImpl
import com.qcloud.suyuan.ui.search.view.ISearchProductView
import kotlinx.android.synthetic.main.activity_search_product.*
import kotlinx.android.synthetic.main.card_search_product_introduce.*
import kotlinx.android.synthetic.main.card_search_product_product_info.*
import kotlinx.android.synthetic.main.layout_product_info.*

/**
 * Description: 搜索产品条形码
 * Author: gaobaiqiang
 * 2018/4/1 下午11:16.
 */
class SearchProductActivity: BaseActivity<ISearchProductView, SearchProductPresenterImpl>(), ISearchProductView {
    override val layoutId: Int
        get() = R.layout.activity_search_product

    override fun initPresenter(): SearchProductPresenterImpl? {
        return SearchProductPresenterImpl()
    }

    override fun initViewAndData() {
        showEmptyView(getString(R.string.tip_scan_batch_code))
    }

    private fun refreshData() {
        GlideUtil.loadImage(this, img_product, "", R.drawable.bmp_product)
        tv_bar_code.text = ""
        tv_product_name.text = ""
        tv_product_spec.text = ""
        tv_product_classify.text = ""
        tv_product_toxicity.text = ""
        tv_pesticides_registration.text = ""
        tv_production_license_code.text = ""
        tv_product_standard_code.text = ""
        tv_product_unit.text = ""
        tv_product_manufacturer.text = ""

        tv_product_introduce.text = ""
    }

    override fun showEmptyView(tip: String) {
        if (isRunning) {
            layout_empty.visibility = View.VISIBLE
            layout_empty.noData(tip)
            layout_info.visibility = View.GONE
        }
    }

    override fun hideEmptyView() {
        if (isRunning) {
            layout_empty.visibility = View.GONE
            layout_info.visibility = View.VISIBLE
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SearchProductActivity::class.java))
        }
    }
}