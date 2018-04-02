package com.qcloud.suyuan.ui.search.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.View
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.search.presenter.impl.SearchSuyuanPresenterImpl
import com.qcloud.suyuan.ui.search.view.ISearchSuyuanView
import kotlinx.android.synthetic.main.activity_search_suyuan.*
import kotlinx.android.synthetic.main.card_search_suyuan_product_info.*
import kotlinx.android.synthetic.main.card_search_suyuan_purchase_info.*
import kotlinx.android.synthetic.main.card_search_suyuan_store_info.*
import kotlinx.android.synthetic.main.layout_product_info.*

/**
 * Description: 搜索溯源码
 * Author: gaobaiqiang
 * 2018/4/1 下午10:40.
 */
class SearchSuyuanActivity: BaseActivity<ISearchSuyuanView, SearchSuyuanPresenterImpl>(), ISearchSuyuanView {
    override val layoutId: Int
        get() = R.layout.activity_search_suyuan

    override fun initPresenter(): SearchSuyuanPresenterImpl? {
        return SearchSuyuanPresenterImpl()
    }

    override fun initViewAndData() {
        showEmptyView(getString(R.string.tip_scan_suyuan_code))
    }

    private fun refreshData() {
        GlideUtil.loadImage(this, img_suyuan_code, "", R.drawable.bmp_product)
        tv_suyuan_code.text = ""
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

        tv_store_number.text = ""
        tv_shopkeeper_name.text = ""
        tv_store_name.text = ""
        tv_shopkeeper_mobile.text = ""
        tv_store_address.text = ""

        //img_purchaser_head.setImageBitmap()
        tv_purchaser_name.text = ""
        tv_purchaser_id.text = ""
        tv_purchaser_mobile.text = ""
        tv_purchase_use.text = ""
        tv_buy_time.text = ""
        tv_other_instructions.text = ""
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
            context.startActivity(Intent(context, SearchSuyuanActivity::class.java))
        }
    }
}