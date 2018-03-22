package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.SellersAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.SellersBean
import com.qcloud.suyuan.ui.goods.presenter.impl.SellersPresenterImpl
import com.qcloud.suyuan.ui.goods.view.ISellersView
import com.qcloud.suyuan.widgets.customview.NoDataView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.card_sellers_product_list.*
import timber.log.Timber
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
        initEditView()
    }

    /**
     * 初始化列表
     * */
    private fun initRecyclerView() {
        list_product?.setLayoutManager(LinearLayoutManager(this))

        PullRefreshUtil.setRefresh(list_product, false, false)

        mAdapter = SellersAdapter(this)
        list_product?.setAdapter(mAdapter!!)

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        mPresenter?.loadData()
    }

    /**
     * 初始化搜索输入框
     * */
    private fun initEditView() {
//        et_search.setOnEditorActionListener { _, action, keyEvent ->
//            if (action == EditorInfo.IME_ACTION_SEARCH
//                    || action == EditorInfo.IME_ACTION_DONE) {
//                et_search.requestFocus()
//                //KeyBoardUtil.hideKeybord(this, et_search)
//                Timber.e("acton = $action, search = ${EditorInfo.IME_ACTION_SEARCH}, done = ${EditorInfo.IME_ACTION_DONE}")
//            }
//            false
//        }
        et_search.setOnKeyListener { view, i, keyEvent ->
            if ((i == KeyEvent.KEYCODE_ENTER)) {
                //et_search.requestFocus()
                //et_search.isFocusable = false
                KeyBoardUtil.hideKeybord(this, et_search)
                toGet()
                Timber.e("keyEvent = $i, enter = ${KeyEvent.KEYCODE_ENTER}")
            }
            false
        }
    }

    private fun toGet() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    et_search.setText("")
                    et_search.requestFocus()
//                    et_search.isFocusable = true
//                    et_search.isEnabled = true
                }

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