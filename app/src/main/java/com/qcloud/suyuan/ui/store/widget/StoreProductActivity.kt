package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshLayout
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.StoreProductAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.StoreProductBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.store.presenter.impl.StoreProductPresenterImpl
import com.qcloud.suyuan.ui.store.view.IStoreProductView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.pop.DropDownPop
import kotlinx.android.synthetic.main.activity_store_product.*
import timber.log.Timber

/**
 * Description: 门店产品
 * Author: gaobaiqiang
 * 2018/3/15 上午12:51.
 */
class StoreProductActivity: BaseActivity<IStoreProductView, StoreProductPresenterImpl>(), IStoreProductView {
    private var mAdapter: StoreProductAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var mPurchaseUsePop: DropDownPop? = null

    private var pageNo = 1

    override val layoutId: Int
        get() = R.layout.activity_store_product

    override fun initPresenter(): StoreProductPresenterImpl? {
        return StoreProductPresenterImpl()
    }

    override fun initViewAndData() {
        initRecyclerView()
        initDropDown()
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
        mAdapter?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            StockDetailsActivity.openActivity(this@StoreProductActivity)
        }

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        loadData()
    }

    private fun loadData() {
        mPresenter?.loadData(pageNo)
    }

    /**
     * 初始化下拉弹窗
     * */
    private fun initDropDown() {
        val list: MutableList<String> = ArrayList()
        list.add("病虫防治1")
        list.add("病虫防治2")
        list.add("病虫防治3")
        list.add("病虫防治4")
        list.add("病虫防治5")
        btn_purchase_use.post {
            val width = btn_purchase_use.width
            mPurchaseUsePop = DropDownPop(this, list, width)

            mPurchaseUsePop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: String) {
                    tv_purchase_use.text = value
                }
            }
        }

        btn_purchase_use.setOnClickListener {
            mPurchaseUsePop?.showAsDropDown(btn_purchase_use)
        }
    }

    override fun replaceList(beans: List<StoreProductBean>?, isNext: Boolean) {
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

    override fun addListAtEnd(beans: List<StoreProductBean>?, isNext: Boolean) {
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
            context.startActivity(Intent(context, StoreProductActivity::class.java))
        }
    }
}