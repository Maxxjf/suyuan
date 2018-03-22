package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshLayout
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.SellersAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.SellersBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.goods.presenter.impl.SellersPresenterImpl
import com.qcloud.suyuan.ui.goods.view.ISellersView
import com.qcloud.suyuan.widgets.customview.NoDataView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.card_sellers_product_list.*
import java.util.concurrent.TimeUnit

/**
 * Description: 卖货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:23.
 */
class SellersActivity: BaseActivity<ISellersView, SellersPresenterImpl>(), ISellersView {
    private var mAdapter: SellersAdapter? = null
    private var mEmptyView: NoDataView? = null

    override val layoutId: Int
        get() = R.layout.activity_sellers

    override fun initPresenter(): SellersPresenterImpl? {
        return SellersPresenterImpl()
    }

    override fun initViewAndData() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        list_product?.setLayoutManager(LinearLayoutManager(this))

        SwipeRefreshUtil.setColorSchemeColors(list_product, AppConstants.loadColors)
        list_product?.onRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                Observable.timer(2, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { list_product?.loadedFinish() }
            }
        }

        mAdapter = SellersAdapter(this)
        list_product?.setAdapter(mAdapter!!)

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        mPresenter?.loadData()
    }

    override fun replaceList(beans: List<SellersBean>?) {
        if (isRunning) {
            if (beans != null && beans.isNotEmpty()) {
                mAdapter?.replaceList(beans)
            }
        }
    }

    override fun addBeanAtEnd(bean: SellersBean) {
        if (isRunning) {
            mAdapter?.addBeanAtEnd(bean)
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
            context.startActivity(Intent(context, SellersActivity::class.java))
        }
    }
}