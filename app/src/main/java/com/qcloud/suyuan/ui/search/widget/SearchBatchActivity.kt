package com.qcloud.suyuan.ui.search.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.View
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.search.presenter.impl.SearchBatchPresenterImpl
import com.qcloud.suyuan.ui.search.view.ISearchBatchView
import kotlinx.android.synthetic.main.activity_search_batch.*
import kotlinx.android.synthetic.main.card_search_batch_in_storage_info.*
import kotlinx.android.synthetic.main.card_search_batch_product_info.*
import kotlinx.android.synthetic.main.layout_product_info.*

/**
 * Description: 搜索批次条形码
 * Author: gaobaiqiang
 * 2018/4/1 下午11:03.
 */
class SearchBatchActivity: BaseActivity<ISearchBatchView, SearchBatchPresenterImpl>(), ISearchBatchView {
    override val layoutId: Int
        get() = R.layout.activity_search_batch

    override fun initPresenter(): SearchBatchPresenterImpl? {
        return SearchBatchPresenterImpl()
    }

    override fun initViewAndData() {
        showEmptyView(getString(R.string.tip_scan_batch_code))
    }

    private fun refreshData() {
        GlideUtil.loadImage(this, img_batch_code, "", R.drawable.bmp_product)
        tv_batch_code.text = ""
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

        tv_in_storage_time.text = ""
        tv_in_storage_number.text = ""
        tv_product_valid.text = ""
        tv_in_storage_price.text = ""
        tv_batch_stock.text = ""
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
            context.startActivity(Intent(context, SearchBatchActivity::class.java))
        }
    }
}