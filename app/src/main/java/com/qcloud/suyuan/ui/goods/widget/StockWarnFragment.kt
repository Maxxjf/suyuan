package com.qcloud.suyuan.ui.goods.widget

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshLayout
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.StockWarnAdapter
import com.qcloud.suyuan.base.BaseFragment
import com.qcloud.suyuan.beans.StockWarnBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.goods.presenter.impl.StockWarnPresenterImpl
import com.qcloud.suyuan.ui.goods.view.IStockWarnView
import com.qcloud.suyuan.widgets.customview.NoDataView
import kotlinx.android.synthetic.main.fragment_stock_warn.*
import timber.log.Timber

/**
 * Description: 库存警告
 * Author: gaobaiqiang
 * 2018/3/20 下午9:01.
 */
class StockWarnFragment: BaseFragment<IStockWarnView, StockWarnPresenterImpl>(), IStockWarnView {

    private var mAdapter: StockWarnAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var pageNo = 1

    override val layoutId: Int
        get() = R.layout.fragment_stock_warn

    override fun initPresenter(): StockWarnPresenterImpl? {
        return StockWarnPresenterImpl()
    }

    override fun initViewAndData() {
        initRecyclerView()
    }

    override fun beginLoad() {

    }

    private fun initRecyclerView() {
        list_stock_warn?.setLayoutManager(LinearLayoutManager(activity))

        SwipeRefreshUtil.setLoadMore(list_stock_warn, true)
        SwipeRefreshUtil.setColorSchemeColors(list_stock_warn, AppConstants.loadColors)
        list_stock_warn?.onRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                pageNo = 1
                loadData()
            }

        }
        list_stock_warn?.onLoadMoreListener = object : SwipeRefreshLayout.OnLoadMoreListener {
            override fun onLoadMore() {
                pageNo++
                loadData()
            }
        }

        mAdapter = StockWarnAdapter(activity!!)
        list_stock_warn?.setAdapter(mAdapter!!)

        mEmptyView = NoDataView(activity!!)
        list_stock_warn?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        loadData()
    }

    private fun loadData() {
        mPresenter?.loadData(pageNo)
    }

    override fun replaceList(beans: List<StockWarnBean>?, isNext: Boolean) {
        if (isInFragment) {
            list_stock_warn?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (mAdapter != null) {
                    mAdapter!!.replaceList(beans)
                }
                list_stock_warn?.isMore(isNext)
                hideEmptyView()
            } else {
                showEmptyView(resources.getString(R.string.tip_no_data))
            }
        }
    }

    override fun addListAtEnd(beans: List<StockWarnBean>?, isNext: Boolean) {
        if (isInFragment) {
            list_stock_warn?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (mAdapter != null) {
                    mAdapter!!.addListAtEnd(beans)
                }
                list_stock_warn?.isMore(isNext)
            } else {
                QToast.show(activity!!, R.string.toast_no_more_data)
                list_stock_warn?.isMore(false)
            }
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isInFragment) {
            list_stock_warn?.loadedFinish()
            if (isShow) {
                QToast.show(activity!!, errMsg)
            } else {
                Timber.e(errMsg)
            }
        }
    }

    override fun showEmptyView(tip: String) {
        list_stock_warn?.showEmptyView()
    }

    override fun hideEmptyView() {
        list_stock_warn?.hideEmptyView()
    }
}