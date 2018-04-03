package com.qcloud.suyuan.ui.record.widget

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
import com.qcloud.suyuan.adapters.SuyuanRecordAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.SuyuanRecordBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.record.presenter.impl.SuyuanRecordPresenterImpl
import com.qcloud.suyuan.ui.record.view.ISuyuanRecordView
import com.qcloud.suyuan.ui.search.widget.SearchSuyuanActivity
import com.qcloud.suyuan.widgets.customview.NoDataView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_suyuan_record.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Description: 溯源记录
 * Author: gaobaiqiang
 * 2018/3/20 上午8:54.
 */
class SuyuanRecordActivity: BaseActivity<ISuyuanRecordView, SuyuanRecordPresenterImpl>(), ISuyuanRecordView {
    private var mAdapter: SuyuanRecordAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var pageNo: Int = 1

    private var keyword = ""

    override val layoutId: Int
        get() = R.layout.activity_suyuan_record

    override fun initPresenter(): SuyuanRecordPresenterImpl? {
        return SuyuanRecordPresenterImpl()
    }

    override fun initViewAndData() {
        initRecyclerView()
        initEditView()
    }

    private fun initRecyclerView() {
        list_record?.setLayoutManager(LinearLayoutManager(this))

        SwipeRefreshUtil.setLoadMore(list_record, true)
        SwipeRefreshUtil.setColorSchemeColors(list_record, AppConstants.loadColors)
        list_record?.onRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                pageNo = 1
                loadData()
            }

        }
        list_record?.onLoadMoreListener = object : SwipeRefreshLayout.OnLoadMoreListener {
            override fun onLoadMore() {
                pageNo++
                loadData()
            }
        }

        mAdapter = SuyuanRecordAdapter(this)
        list_record?.setAdapter(mAdapter!!)
        mAdapter?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val bean = mAdapter!!.mList[position]
            SearchSuyuanActivity.openActivity(this, bean.traceabilityCode)
        }

        mEmptyView = NoDataView(this)
        list_record?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

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
                    keyword = et_search.text.toString().trim()
                    if (StringUtil.isNotBlank(keyword)) {
                        loadData()
                        reSetEditText()
                    } else {
                        QToast.show(this, R.string.toast_no_input_value)
                    }
                }
            }
            false
        }
        btn_search.setOnClickListener {
            KeyBoardUtil.hideKeybord(this, et_search)
            keyword = et_search.text.toString().trim()
            if (StringUtil.isNotBlank(keyword)) {
                loadData()
                reSetEditText()
            } else {
                QToast.show(this, R.string.toast_no_input_value)
            }
        }
    }

    private fun reSetEditText() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    et_search.setText("")
                    et_search.requestFocus()
                }
    }

    private fun loadData() {
        mPresenter?.loadData(keyword, pageNo)
    }

    override fun replaceList(beans: List<SuyuanRecordBean>?, isNext: Boolean) {
        if (isRunning) {
            list_record?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (mAdapter != null) {
                    mAdapter!!.replaceList(beans)
                }
                list_record?.isMore(isNext)
                hideEmptyView()
            } else {
                showEmptyView(resources.getString(R.string.tip_no_data))
            }
        }
    }

    override fun addListAtEnd(beans: List<SuyuanRecordBean>?, isNext: Boolean) {
        if (isRunning) {
            list_record?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (mAdapter != null) {
                    mAdapter!!.addListAtEnd(beans)
                }
                list_record?.isMore(isNext)
            } else {
                QToast.show(this, R.string.toast_no_more_data)
                list_record?.isMore(false)
            }
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isRunning) {
            list_record?.loadedFinish()
            if (isShow) {
                QToast.show(this, errMsg)
            } else {
                Timber.e(errMsg)
            }
        }
    }

    override fun showEmptyView(tip: String) {
        list_record?.showEmptyView()
    }

    override fun hideEmptyView() {
        list_record?.hideEmptyView()
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SuyuanRecordActivity::class.java))
        }
    }
}