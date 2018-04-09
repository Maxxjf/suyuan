package com.qcloud.suyuan.ui.goods.widget

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshLayout
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.ValidWarnAdapter
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.base.BaseFragment
import com.qcloud.suyuan.beans.ValidWarnBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.goods.presenter.impl.ValidWarnPresenterImpl
import com.qcloud.suyuan.ui.goods.view.IValidWarnView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.TipDialog
import kotlinx.android.synthetic.main.fragment_valid_warn.*
import timber.log.Timber

/**
 * Description: 有效期告警
 * Author: gaobaiqiang
 * 2018/3/20 下午9:08.
 */
class ValidWarnFragment: BaseFragment<IValidWarnView, ValidWarnPresenterImpl>(), IValidWarnView {
    private var mAdapter: ValidWarnAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var pageNo: Int = 1
    private var mTipDialog: TipDialog? = null

    override val layoutId: Int
        get() = R.layout.fragment_valid_warn

    override fun initPresenter(): ValidWarnPresenterImpl? {
        return ValidWarnPresenterImpl()
    }

    override fun initViewAndData() {
        initRecyclerView()
    }

    override fun beginLoad() {

    }

    private fun initRecyclerView() {
        list_valid_warn?.setLayoutManager(LinearLayoutManager(activity))

        SwipeRefreshUtil.setLoadMore(list_valid_warn, true)
        SwipeRefreshUtil.setColorSchemeColors(list_valid_warn, AppConstants.loadColors)
        list_valid_warn?.onRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                pageNo = 1
                loadData()
            }

        }
        list_valid_warn?.onLoadMoreListener = object : SwipeRefreshLayout.OnLoadMoreListener {
            override fun onLoadMore() {
                pageNo++
                loadData()
            }
        }

        mAdapter = ValidWarnAdapter(activity!!)
        list_valid_warn?.setAdapter(mAdapter!!)
        mAdapter?.onHolderClick = object : CommonRecyclerAdapter.OnHolderClickListener<ValidWarnBean> {
            override fun onHolderClick(view: View, t: ValidWarnBean, position: Int) {
                showTip(t)
            }
        }

        mEmptyView = NoDataView(activity!!)
        list_valid_warn?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        loadData()
    }

    private fun loadData() {
        mPresenter?.loadData(pageNo)
    }

    private fun showTip(bean: ValidWarnBean?) {
        if (mTipDialog == null) {
            mTipDialog = TipDialog(mContext!!)
        }
        if (bean != null) {
            // 出库弹窗
            mTipDialog?.setTip(R.string.tip_out_storage_confirm)
            mTipDialog?.setConfirmBtn(R.string.btn_confirm)
        } else {
            // 提示弹窗
            mTipDialog?.setTip(R.string.tip_out_storage_success)
            mTipDialog?.setConfirmBtn(R.string.btn_i_know)
        }
        mTipDialog?.show()
        mTipDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                if (bean != null) {
                    mPresenter?.outStorage(bean.recordId, bean.surplusNum)
                } else {
                    pageNo = 1
                    loadData()
                }
            }
        }
    }

    override fun replaceList(beans: List<ValidWarnBean>?, isNext: Boolean) {
        if (isInFragment) {
            list_valid_warn?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (mAdapter != null) {
                    mAdapter!!.replaceList(beans)
                }
                list_valid_warn?.isMore(isNext)
                hideEmptyView()
            } else {
                showEmptyView(resources.getString(R.string.tip_no_data))
            }
        }
    }

    override fun addListAtEnd(beans: List<ValidWarnBean>?, isNext: Boolean) {
        if (isInFragment) {
            list_valid_warn?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (mAdapter != null) {
                    mAdapter!!.addListAtEnd(beans)
                }
                list_valid_warn?.isMore(isNext)
            } else {
                QToast.show(activity!!, R.string.toast_no_more_data)
                list_valid_warn?.isMore(false)
            }
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isInFragment) {
            list_valid_warn?.loadedFinish()
            if (isShow) {
                QToast.show(activity!!, errMsg)
            } else {
                Timber.e(errMsg)
            }
        }
    }

    override fun showEmptyView(tip: String) {
        list_valid_warn?.showEmptyView()
    }

    override fun hideEmptyView() {
        list_valid_warn?.hideEmptyView()
    }

    override fun outStorageSuccess() {
        if (isInFragment) {
            showTip(null)
        }
    }
}