package com.qcloud.suyuan.ui.search.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.BatchDetailsBean
import com.qcloud.suyuan.ui.search.presenter.impl.SearchBatchPresenterImpl
import com.qcloud.suyuan.ui.search.view.ISearchBatchView
import com.qcloud.suyuan.utils.BarCodeUtil
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

    override fun replaceData(bean: BatchDetailsBean) {
        if (isRunning) {
            hideEmptyView()
            val merchandise = bean.merchandise
            if (merchandise != null) {
                img_batch_code.post {
                    val width = img_batch_code.width
                    val height = resources.getDimension(R.dimen.barHeight)

                    val barCode = BarCodeUtil.createBarCode(merchandise.barCode ?: "", width, height.toInt())
                    if (barCode != null) {
                        img_batch_code.setImageBitmap(barCode)
                    }
                }

                with(merchandise) {
                    tv_batch_code.text = barCode
                    tv_bar_code.text = ""
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
                }
            }

            val record = bean.record
            if (record != null) {
                with(record) {
                    tv_in_storage_time.text = createDate
                    tv_in_storage_number.text = goodsNumStr
                    tv_product_valid.text = validDate
                    tv_in_storage_price.text = priceStr
                    tv_batch_stock.text = surplusNumStr
                }
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