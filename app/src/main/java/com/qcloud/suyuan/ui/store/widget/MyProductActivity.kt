package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.widget.AdapterView
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshLayout
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.StoreProductAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.beans.ProductClassifyBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.store.presenter.impl.MyProductPresenterImpl
import com.qcloud.suyuan.ui.store.view.IMyProductView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.pop.DropDownPop
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_my_product.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * 类说明：我的产品
 * Author: Kuzan
 * Date: 2018/3/30 11:14.
 */
class MyProductActivity: BaseActivity<IMyProductView, MyProductPresenterImpl>(), IMyProductView {
    private var mAdapter: StoreProductAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var mPurchaseUsePop: DropDownPop? = null

    private var pageNo = 1
    private var classifyId: String? = null
    private var keyword: String? = null

    override val layoutId: Int
        get() = R.layout.activity_my_product

    override fun initPresenter(): MyProductPresenterImpl? {
        return MyProductPresenterImpl()
    }

    override fun initViewAndData() {
        initRecyclerView()
        initEditView()
        mPresenter?.loadClassify()
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

        mAdapter = StoreProductAdapter(this)
        list_product?.setAdapter(mAdapter!!)
        mAdapter?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val id = mAdapter?.mList?.get(position)?.id ?: "-1"
            StockDetailsActivity.openActivity(this@MyProductActivity, id)
        }

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        loadData()
    }

    private fun initEditView() {
        et_search.setOnKeyListener { _, action, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_UP) {
                if (action == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtil.hideKeybord(this, et_search)
                    val inputValue = et_search.text.toString().trim()
                    if (StringUtil.isNotBlank(inputValue)) {
                        reSetEditText()
                        keyword = inputValue
                        classifyId = null
                        pageNo = 1
                        loadData()
                    } else {
                        QToast.show(this, R.string.toast_no_input_value)
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
                classifyId = null
                pageNo = 1
                loadData()
            } else {
                QToast.show(this, R.string.toast_no_input_value)
            }
        }
    }

    private fun loadData() {
        mPresenter?.loadData(pageNo, classifyId, keyword)
    }

    /**
     * 获取扫码数据
     * */
    private fun reSetEditText() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    et_search.setText("")
                    et_search.requestFocus()
                    tv_purchase_use.text = ""
                }
    }

    /**
     * 初始化下拉弹窗
     * */
    private fun initClassify(list: List<ProductClassifyBean>) {
        btn_purchase_use.post {
            val width = btn_purchase_use.width
            mPurchaseUsePop = DropDownPop(this, list, width)

            mPurchaseUsePop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: Any?) {
                    val bean: ProductClassifyBean = value as ProductClassifyBean
                    classifyId = bean.id
                    keyword = null
                    tv_purchase_use.text = bean.name
                    pageNo = 1
                    loadData()
                }
            }
        }

        btn_purchase_use.setOnClickListener {
            Timber.e("click $list")
            mPurchaseUsePop?.showAsDropDown(btn_purchase_use)
        }
    }

    override fun replaceClassifyList(list: List<ProductClassifyBean>?) {
        if (isRunning) {
            if (list != null && list.isNotEmpty()) {
                initClassify(list)
            }
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
                showEmptyView(resources.getString(R.string.tip_no_data))
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

    override fun showEmptyView(tip: String) {
        list_product?.showEmptyView()
    }

    override fun hideEmptyView() {
        list_product?.hideEmptyView()
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, MyProductActivity::class.java))
        }
    }
}