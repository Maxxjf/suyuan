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
import com.qcloud.suyuan.beans.BarCodeDetailsBean
import com.qcloud.suyuan.ui.search.presenter.impl.SearchProductPresenterImpl
import com.qcloud.suyuan.ui.search.view.ISearchProductView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search_product.*
import kotlinx.android.synthetic.main.card_search_product_introduce.*
import kotlinx.android.synthetic.main.card_search_product_product_info.*
import kotlinx.android.synthetic.main.layout_product_info.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Description: 搜索产品条形码(搜索入库批次码)
 * Author: gaobaiqiang
 * 2018/4/1 下午11:16.
 */
class SearchProductActivity: BaseActivity<ISearchProductView, SearchProductPresenterImpl>(), ISearchProductView {
    private var keyword: String? = null

    override val layoutId: Int
        get() = R.layout.activity_search_product

    override fun initPresenter(): SearchProductPresenterImpl? {
        return SearchProductPresenterImpl()
    }

    override fun initViewAndData() {
        initEmptyView()
        showEmptyView(getString(R.string.tip_scan_batch_code))
        initEditView()
    }

    private fun initEmptyView() {
        layout_empty.setImageIcon(R.drawable.bmp_search_empty)
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
        mPresenter?.loadData(keyword!!)
    }

    private fun reSetEditText() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    et_search.setText("")
                    et_search.requestFocus()
                }
    }

    override fun replaceData(bean: BarCodeDetailsBean) {
        if (isRunning) {
            hideEmptyView()
            with(bean) {
                GlideUtil.loadImage(this@SearchProductActivity, img_product, imageUrl, R.drawable.bmp_product)
                tv_bar_code.text = barCode
                tv_product_name.text = name
                tv_product_spec.text = specification
                tv_product_classify.text = classifyName
                tv_product_toxicity.text = toxicity
                tv_pesticides_registration.text = registerCard
                tv_production_license_code.text = licenseCard
                tv_product_standard_code.text = standardCard
                tv_product_unit.text = unit
                tv_product_manufacturer.text = millName
                tv_product_manufacturer_address.text = millAddress
                tv_product_introduce.text = content
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

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isRunning) {
            if (isShow) {
                QToast.show(this, errMsg)
            } else {
                Timber.e(errMsg)
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SearchProductActivity::class.java))
        }
    }
}