package com.qcloud.suyuan.ui.record.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.haibin.calendarview.Calendar
import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshLayout
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.ReturnedRecordAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.CodeBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.record.presenter.impl.ReturnRecordPresenterImpl
import com.qcloud.suyuan.ui.record.view.IReturnRecordView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.DatePickerDialog
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
    private var startDatePicker: DatePickerDialog? = null
    private var endDatePicker: DatePickerDialog? = null
    var pageNo: Int = 1
    var startTime: String = ""
    var endTime: String = ""

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
                R.id.tv_date_from -> showStartPicker()
                R.id.tv_date_to -> showEndPicker()
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
        tv_date_from.setOnClickListener(this)
        tv_date_to.setOnClickListener(this)
    }

    private fun loadData() {
        startTime = tv_date_from.text.toString().trim()
        endTime = tv_date_to.text.toString().trim()
        if (DateUtil.compareTime(startTime, endTime, DateStyleEnum.YYYY_MM_DD.value) == 1) {
            loadErr(resources.getString(R.string.toast_start_bigger_end))
            return
        }
        startLoadingDialog()
        mPresenter?.loadData(startTime, endTime, pageNo)
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        stopLoadingDialog()
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
        stopLoadingDialog()
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
        stopLoadingDialog()
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

    private fun showStartPicker() {
        if (startDatePicker == null) {
            startDatePicker = DatePickerDialog(this)
            startDatePicker?.onDateSelectListener = object : DatePickerDialog.OnDateSelectedListener {
                @SuppressLint("SetTextI18n")
                override fun dateSelected(calendar: Calendar?) {
                    if (calendar != null) {
                        tv_date_from.text = "${calendar.year}-${calendar.month}-${calendar.day}"
                        loadData()
                    }
                }
            }
        }
        startDatePicker?.show()
    }

    private fun showEndPicker() {
        if (endDatePicker == null) {
            endDatePicker = DatePickerDialog(this)
            endDatePicker?.onDateSelectListener = object : DatePickerDialog.OnDateSelectedListener {
                @SuppressLint("SetTextI18n")
                override fun dateSelected(calendar: Calendar?) {
                    if (calendar != null) {
                        tv_date_to.text = "${calendar.year}-${calendar.month}-${calendar.day}"
                        loadData()
                    }
                }
            }
        }
        endDatePicker?.show()
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