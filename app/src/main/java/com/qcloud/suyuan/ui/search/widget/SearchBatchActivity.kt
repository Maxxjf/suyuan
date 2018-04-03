package com.qcloud.suyuan.ui.search.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.ui.search.presenter.impl.SearchBatchPresenterImpl
import com.qcloud.suyuan.ui.search.view.ISearchBatchView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search_batch.*
import kotlinx.android.synthetic.main.card_search_batch_in_storage_info.*
import kotlinx.android.synthetic.main.card_search_batch_product_info.*
import kotlinx.android.synthetic.main.layout_product_info.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Description: 搜索批次条形码(搜索产品)
 * Author: gaobaiqiang
 * 2018/4/1 下午11:03.
 */
class SearchBatchActivity: BaseActivity<ISearchBatchView, SearchBatchPresenterImpl>(), ISearchBatchView {
    private var keyword: String? = null

    override val layoutId: Int
        get() = R.layout.activity_search_batch

    override fun initPresenter(): SearchBatchPresenterImpl? {
        return SearchBatchPresenterImpl()
    }

    override fun initViewAndData() {
        showEmptyView(getString(R.string.tip_scan_batch_code))
        initEditView()
    }

    /**
     * 初始化搜索输入框
     * */
    private fun initEditView() {
        et_search.setOnKeyListener { _, action, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_UP) {
                if (action == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtil.hideKeybord(this, et_search)
                    keyword = et_search.text.toString().trim()
                    if (StringUtil.isNotBlank(keyword)) {
                        loadData()
                    } else {
                        QToast.show(this, R.string.toast_no_input_value)
                    }
                }
            }
            false
        }
    }

    private fun loadData() {
        QToast.show(this, keyword)
        reSetEditText()
        //mPresenter?.loadProduct(keyword!!)
    }

    private fun reSetEditText() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    et_search.setText("")
                    et_search.requestFocus()
                }
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

    override fun replaceData(bean: ProductBean) {
        if (isRunning) {
            with(bean) {
                GlideUtil.loadImage(this@SearchBatchActivity, img_batch_code, image, R.drawable.bmp_product)
                tv_batch_code.text = barCode
                tv_bar_code.text = ""
                tv_product_name.text = name
                tv_product_spec.text = specification
                tv_product_classify.text = classifyName
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