package com.qcloud.suyuan.widgets.dialog

import android.app.Dialog
import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import com.qcloud.qclib.utils.ScreenUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.RepaymentListAdapter
import com.qcloud.suyuan.beans.RepaymentListBean
import com.qcloud.suyuan.widgets.customview.NoDataView
import kotlinx.android.synthetic.main.dialog_credit_history.*
import org.jetbrains.annotations.NotNull
import timber.log.Timber

/**
 * 类型：还款历史对话框
 * Author: iceberg
 * Date: 2018/4/18  15:11
 */
class RepaymentHistoryDialog @JvmOverloads constructor(@NotNull private val mContext: Context,
                                                       @StringRes themeResId: Int = R.style.InputDialog) : Dialog(mContext, themeResId) {
    private var mAdapter: RepaymentListAdapter? = null

    private var mEmptyView: NoDataView? =null

    init {
        setContentView(R.layout.dialog_credit_history)
        initDialog()
        initView()
    }

    private fun initView() {
        mAdapter = RepaymentListAdapter(mContext)
        rv_repayment_list.setAdapter(mAdapter!!)
        rv_repayment_list.setLayoutManager(LinearLayoutManager(mContext))
        mEmptyView = NoDataView(mContext)
        mEmptyView?.noData(R.string.tip_no_list)
        mEmptyView?.setImageIcon(R.drawable.bmp_list_empty)
        rv_repayment_list.setEmptyView(mEmptyView!!,Gravity.CENTER_HORIZONTAL)
        btn_close.setOnClickListener({dismiss()})
    }

    private fun initDialog() {
        val lp = window!!.attributes
        lp.gravity = Gravity.CENTER
        lp.width = ScreenUtil.getScreenWidth(mContext) * 7 / 16 //设置宽度
        window!!.attributes = lp
        setCancelable(true)
    }

    fun replaceList(beans: List<RepaymentListBean>?, isNext: Boolean) {
        rv_repayment_list?.loadedFinish()
        if (beans != null && beans.isNotEmpty()) {
            mAdapter?.replaceList(beans)
            rv_repayment_list?.isMore(isNext)
            hideEmptyView()
        } else {
            showEmptyView()
        }
    }

    fun showEmptyView() {
        Timber.e("____showEmptyView")
        rv_repayment_list.showEmptyView()
    }

    fun hideEmptyView() {
        rv_repayment_list.hideEmptyView()
    }
}