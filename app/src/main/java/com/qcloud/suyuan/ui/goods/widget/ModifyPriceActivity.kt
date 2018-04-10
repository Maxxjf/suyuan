package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshLayout
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.ModifyPriceStockAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.goods.presenter.impl.ModifyPricePresenterImpl
import com.qcloud.suyuan.ui.goods.view.IModifyPriceView
import com.qcloud.suyuan.widgets.customview.NoDataView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_modify_price.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Description: 修改售价
 * Author: gaobaiqiang
 * 2018/3/15 上午12:38.
 */
class ModifyPriceActivity: BaseActivity<IModifyPriceView, ModifyPricePresenterImpl>(), IModifyPriceView {
    private var mAdapter: ModifyPriceStockAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var pageNo = 1
    private var classifyId: String? = null
    private var keyword: String? = null

    override val layoutId: Int
        get() = R.layout.activity_modify_price

    override fun initPresenter(): ModifyPricePresenterImpl? {
        return ModifyPricePresenterImpl()
    }

    override fun initViewAndData() {
        initRecyclerView()
        initEditView()
    }

    private fun initRecyclerView() {
        list_product?.setLayoutManager(LinearLayoutManager(this))

        SwipeRefreshUtil.setLoadMore(list_product, true)
        SwipeRefreshUtil.setColorSchemeColors(list_product, AppConstants.loadColors)
        list_product?.onRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                pageNo = 1
                loadData()
            }
        }
        list_product?.onLoadMoreListener = object : SwipeRefreshLayout.OnLoadMoreListener {
            override fun onLoadMore() {
                pageNo++
                loadData()
            }
        }

        mAdapter = ModifyPriceStockAdapter(this)
        list_product?.setAdapter(mAdapter!!)
        mAdapter?.onModifyListener = object : ModifyPriceStockAdapter.OnModifyListener {
            override fun onModify(bean: ProductBean?, type: Int) {
                if (bean != null) {
                    if (type == 1) {
                        mPresenter?.modifyPrice(bean.id ?: "0", bean.retailPrice)
                    } else {
                        mPresenter?.modifyWarnLine(bean.id ?: "0", bean.cordon)
                    }
                }
            }
        }

        mEmptyView = NoDataView(this)
        mEmptyView?.noData(R.string.tip_no_this_product)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        loadData()
    }

    /**
     * 初始化搜索输入框
     * */
    private fun initEditView() {
        et_search.setOnKeyListener { _, action, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_UP) {
                if (action == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtil.hideKeybord(this, et_search)
                    val inputValue = et_search.text.toString().trim()
                    if (StringUtil.isNotBlank(inputValue)) {
                        reSetEditText()
                        keyword = inputValue
                        pageNo = 1
                        loadData()
                    } else {
                        QToast.show(this, R.string.hint_input_product_name)
                    }
                }
            }
            false
        }
        btn_search.setOnClickListener {
            KeyBoardUtil.hideKeybord(this, et_search)
            val inputValue = et_search.text.toString().trim()
            if (StringUtil.isNotBlank(inputValue)) {
                reSetEditText()
                keyword = inputValue
                pageNo = 1
                loadData()
            } else {
                QToast.show(this, R.string.hint_input_product_name)
            }
        }
    }

    private fun loadData() {
        mPresenter?.loadData(pageNo, classifyId, keyword)
    }

    private fun reSetEditText() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    et_search.setText("")
                    et_search.requestFocus()
                }
    }

    override fun replaceList(beans: List<ProductBean>?, isNext: Boolean) {
        if (isRunning) {
            list_product?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (mAdapter != null) {
                    mAdapter!!.replaceList(beans)
                }
                list_product?.isMore(isNext)
                hideEmptyView()
            } else {
                showEmptyView()
            }
        }
    }

    override fun addListAtEnd(beans: List<ProductBean>?, isNext: Boolean) {
        if (isRunning) {
            list_product?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (mAdapter != null) {
                    mAdapter!!.addListAtEnd(beans)
                }
                list_product?.isMore(isNext)
            } else {
                QToast.show(this, R.string.toast_no_more_data)
                list_product?.isMore(false)
            }
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isRunning) {
            list_product?.loadedFinish()
            if (isShow) {
                QToast.show(this, errMsg)
            } else {
                Timber.e(errMsg)
            }
        }
    }

    override fun showEmptyView() {
        list_product?.showEmptyView()
    }

    override fun hideEmptyView() {
        list_product?.hideEmptyView()
    }

    override fun modifyPriceSuccess() {
        QToast.success(this, R.string.toast_modify_price_success)
    }

    override fun modifyStockWarnSuccess() {
        QToast.success(this, R.string.toast_modify_stock_warn_success)
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, ModifyPriceActivity::class.java))
        }
    }
}