package com.qcloud.suyuan.ui.record.widget

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshLayout
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.ReturnedRecordAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.CodeBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.record.presenter.impl.ReturnRecordPresenterImpl
import com.qcloud.suyuan.ui.record.view.IReturnRecordView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.customview.DatePick
import kotlinx.android.synthetic.main.activity_return_record.*
import timber.log.Timber

/**
 * Description: 退货记录
 * Author: gaobaiqiang
 * 2018/3/15 上午1:01.
 */
class ReturnRecordActivity : BaseActivity<IReturnRecordView, ReturnRecordPresenterImpl>(), IReturnRecordView, View.OnClickListener {


    private var madapter: ReturnedRecordAdapter? = null
    private var mEmptyView: NoDataView? = null
    private var datePicker: DatePickerDialog? = null

    var pageNo: Int = 1
    var startTime: String = "2018-01-01"
    var endTime: String = "2018-03-22"

    override val layoutId: Int
        get() = R.layout.activity_return_record

    override fun initPresenter(): ReturnRecordPresenterImpl? {
        return ReturnRecordPresenterImpl()
    }

    override fun initViewAndData() {
        initView()
        loadData()
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {

            }
        }
    }


    private fun initView() {
        rv_return_goods_record?.setLayoutManager(LinearLayoutManager(this))
        SwipeRefreshUtil.setLoadMore(rv_return_goods_record, true)
        SwipeRefreshUtil.setColorSchemeColors(rv_return_goods_record, AppConstants.loadColors)

        rv_return_goods_record?.onRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                pageNo = 1
                loadData()
            }
        }
        rv_return_goods_record?.onLoadMoreListener = object : SwipeRefreshLayout.OnLoadMoreListener {
            override fun onLoadMore() {
                pageNo++
                loadData()
            }
        }
        madapter = ReturnedRecordAdapter(this)
        rv_return_goods_record?.setAdapter(madapter!!)
        mEmptyView = NoDataView(this)
        rv_return_goods_record.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)
        tv_date_from.setOnDateChangeListener(object : DatePick.OnDateChangeListener{
            override fun onDateChange(year: Int, mouth: Int, day: Int) {
                loadData()
            }
        })
        tv_date_to.setOnDateChangeListener(object : DatePick.OnDateChangeListener{
            override fun onDateChange(year: Int, mouth: Int, day: Int) {
                loadData()
            }
        })
    }

    private fun loadData() {
        startTime = tv_date_from.text.toString().trim()
        endTime = tv_date_to.text.toString().trim()
        mPresenter?.loadData(startTime, endTime, pageNo)
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isRunning) {
            rv_return_goods_record?.loadedFinish()
            if (isShow) {
                QToast.show(this, errMsg)
            } else {
                Timber.e(errMsg)
            }
        }
    }

    override fun replaceList(beans: List<CodeBean>?, isNext: Boolean) {
        if (isRunning) {
            rv_return_goods_record?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (madapter != null) {
                    madapter!!.replaceList(beans)
                }
                rv_return_goods_record?.isMore(isNext)
                hideEmptyView()
            } else {
                showEmptyView(resources.getString(R.string.tip_no_data))
            }
        }
    }

    override fun addListAtEnd(beans: List<CodeBean>?, isNext: Boolean) {
        if (isRunning) {
            rv_return_goods_record?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                madapter?.addListAtEnd(beans)
                rv_return_goods_record.isMore(isNext)
            } else {
                loadErr(resources.getString(R.string.toast_no_more_data))
                rv_return_goods_record?.isMore(false)
            }
        }
    }

    override fun showEmptyView(tip: String) {
        rv_return_goods_record?.showEmptyView()
    }

    override fun hideEmptyView() {
        rv_return_goods_record?.hideEmptyView()
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, ReturnRecordActivity::class.java))
        }
    }
}