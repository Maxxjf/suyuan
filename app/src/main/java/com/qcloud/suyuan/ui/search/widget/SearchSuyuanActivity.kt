package com.qcloud.suyuan.ui.search.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.text.InputType
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.BaseUrlUtil
import com.qcloud.qclib.utils.BitmapUtil
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.SuyuanDetailsBean
import com.qcloud.suyuan.ui.search.presenter.impl.SearchSuyuanPresenterImpl
import com.qcloud.suyuan.ui.search.view.ISearchSuyuanView
import com.qcloud.suyuan.utils.BarCodeUtil
import com.qcloud.suyuan.utils.PasswordKeyListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search_suyuan.*
import kotlinx.android.synthetic.main.card_search_suyuan_product_info.*
import kotlinx.android.synthetic.main.card_search_suyuan_purchase_info.*
import kotlinx.android.synthetic.main.card_search_suyuan_store_info.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Description: 搜索溯源码
 * Author: gaobaiqiang
 * 2018/4/1 下午10:40.
 */
class SearchSuyuanActivity: BaseActivity<ISearchSuyuanView, SearchSuyuanPresenterImpl>(), ISearchSuyuanView {
    private var keyword: String? = null

    override val layoutId: Int
        get() = R.layout.activity_search_suyuan

    override fun initPresenter(): SearchSuyuanPresenterImpl? {
        return SearchSuyuanPresenterImpl()
    }

    override fun initViewAndData() {
        initEmptyView()
        keyword = intent.getStringExtra("SUYUAN_NUMBER")
        if (StringUtil.isNotBlank(keyword)) {
            toolbar.setTitle(R.string.title_suyuan_record)
            mPresenter?.loadData(keyword!!)
        } else {
            toolbar.setTitle(R.string.title_search_suyuan)
            showEmptyView(getString(R.string.tip_scan_suyuan_code))
        }
        initEditView()
    }

    private fun initEmptyView() {
        layout_empty.setImageIcon(R.drawable.bmp_search_empty)
    }

    /**
     * 初始化搜索输入框
     * */
    private fun initEditView() {
        et_search.keyListener = PasswordKeyListener()
        et_search.setOnKeyListener { _, action, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_UP) {
                if (action == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtil.hideKeybord(this, et_search)
                    keyword = et_search.text.toString().trim()
                    if (StringUtil.isNotBlank(keyword) && keyword!!.startsWith("http")) {
                        keyword = BarCodeUtil.disposeQrCode2Suyuan(keyword!!)
                        loadData()
                        reSetEditText()
                    } else {
                        QToast.show(this, R.string.tip_scan_suyuan_code)
                    }
                }
            }
            false
        }
    }

    private fun loadData() {
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

    override fun replaceData(bean: SuyuanDetailsBean) {
        if (isRunning) {
            val infoBean = bean.traceabilityInfo
            if (infoBean != null) {
                hideEmptyView()

                img_suyuan_code.post {
                    val width = img_suyuan_code.width
                    val url = BaseUrlUtil.getBaseUrl() + infoBean.codeUrl
                    Timber.e("url = $url")
                    val bitmap = BarCodeUtil.createQrCode(url, width, width)
                    if (bitmap != null) {
                        img_suyuan_code.setImageBitmap(bitmap)
                    }
                }

                with(infoBean) {
                    tv_suyuan_code.text = traceabilityCode
                    tv_bar_code.text = barCode
                    tv_product_name.text = goodsName
                    tv_product_spec.text = specification
                    tv_product_classify.text = classifyName
                    tv_product_toxicity.text = virulence
                    tv_pesticides_registration.text = registerCard
                    tv_production_license_code.text = licenseCard
                    tv_product_standard_code.text = standardCard
                    tv_product_unit.visibility = View.GONE
                    tv_product_manufacturer.text = millName
                    tv_product_manufacturer_address.text = millAddress

                    tv_store_number.text = storeCode
                    tv_shopkeeper_name.text = shopkeeperName
                    tv_store_name.text = storeName
                    tv_shopkeeper_mobile.text = storePhone
                    tv_store_address.text = storeAddress

                    if (StringUtil.isNotBlank(purchaserImage)) {
                        val bitmap = BitmapUtil.base64ToBitmap(purchaserImage!!)
                        img_purchaser_head.setImageBitmap(bitmap)
                    }
                    tv_purchaser_name.text = purchaser
                    tv_purchaser_id.text = purchaserCard
                    tv_purchaser_mobile.text = purchaserPhone
                    tv_purchase_use.text = purchaserUse
                    tv_buy_time.text = purchaserTime
                    tv_other_instructions.text = remark
                }
            } else {
                showEmptyView(getString(R.string.tip_scan_suyuan_code))
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        fun openActivity(@NonNull context: Context, suyuanNumber: String? = null) {
            val intent = Intent(context, SearchSuyuanActivity::class.java)
            intent.putExtra("SUYUAN_NUMBER", suyuanNumber)
            context.startActivity(intent)
        }
    }
}