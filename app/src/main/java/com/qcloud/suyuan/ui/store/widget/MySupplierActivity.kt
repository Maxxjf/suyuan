package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.SupplierAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.SupplierBean
import com.qcloud.suyuan.ui.store.presenter.impl.MySupplierPresenterImpl
import com.qcloud.suyuan.ui.store.view.IMySupplierView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.TipDialog
import kotlinx.android.synthetic.main.activity_my_supplier.*

/**
 * Description: 我的供应商
 * Author: icebreg
 * 2018/3/29
 */
class MySupplierActivity : BaseActivity<IMySupplierView, MySupplierPresenterImpl>(), IMySupplierView, View.OnClickListener {
    private var keyword = ""
    private var mCurrentSupplier:SupplierBean?=null

    private var mErrtip: TipDialog? = null
    private var mAdapter: SupplierAdapter? = null
    private var mEmptyView: NoDataView? = null
    private var mAddFragment: SupplierAddFragment?=null
    private var mDetailFragment: SupplierDetailFragment? = null

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (mErrtip != null) {
            mErrtip = TipDialog(this)
        }
        if (isShow) {
            mErrtip?.setTip(errMsg)
            mErrtip?.show()
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_my_supplier

    override fun initPresenter(): MySupplierPresenterImpl? {
        return MySupplierPresenterImpl()
    }

    override fun initViewAndData() {
        initView()
        loadData()
    }


    private fun initView() {
        mEmptyView = NoDataView(this)
        mAdapter = SupplierAdapter(this)
        PullRefreshUtil.setRefresh(rv_supplier, false, false)
        rv_supplier.setEmptyView(mEmptyView!!, Gravity.CENTER)
        rv_supplier.setAdapter(mAdapter!!)
        rv_supplier.setLayoutManager(LinearLayoutManager(this))
        mAdapter?.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                mCurrentSupplier= mAdapter?.mList!![i]
                showDetailFragment()
            }
        }
        showAddFragment()
        btn_add.setOnClickListener(this)
    }

    fun loadData() {
        keyword = et_search.text.toString().trim()
        mPresenter?.getSupplierList(keyword)
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_add -> showAddFragment()
        }
    }
    //显示新增供应商
    fun showAddFragment() {
        if (mAddFragment == null) {
            mAddFragment = SupplierAddFragment()
        }
        replaceFragment(mAddFragment, R.id.fragment_container, false)
    }
    //显示供应商详情
    fun showDetailFragment() {

        if (mDetailFragment == null) {
            mDetailFragment = SupplierDetailFragment(this!!.mCurrentSupplier!!)
        }else{
            mDetailFragment?.replaceInfo(mCurrentSupplier!!)
        }
        if (mCurrentSupplier!=null){
            replaceFragment(mDetailFragment, R.id.fragment_container, false)
        }
    }

    override fun replaceList(beans: List<SupplierBean>?, isNext: Boolean) {
        if (isRunning) {
            rv_supplier?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (mAdapter != null) {
                    mAdapter!!.replaceList(beans)
                }
                rv_supplier?.isMore(isNext)
                hideEmptyView()
            } else {
                showEmptyView(resources.getString(R.string.tip_no_data))
            }
        }
    }

    override fun addListAtEnd(bean: SupplierBean?, isNext: Boolean) {
        if (isRunning) {
            rv_supplier?.loadedFinish()
            if (bean != null) {
                mAdapter?.addBeanAtEnd(bean)
            } else {
                rv_supplier?.isMore(false)
            }
        }
    }

    override fun showEmptyView(tip: String) {
        rv_supplier.showEmptyView()
    }

    override fun hideEmptyView() {
        rv_supplier.hideEmptyView()
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, MySupplierActivity::class.java))
        }
    }
}