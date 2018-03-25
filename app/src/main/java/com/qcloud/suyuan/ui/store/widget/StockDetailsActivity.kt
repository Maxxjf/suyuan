package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.InStorageAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.InStorageRecordBean
import com.qcloud.suyuan.ui.store.presenter.impl.StockDetailsPresenterImpl
import com.qcloud.suyuan.ui.store.view.IStockDetailsView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.AdjustWarnDialog
import com.qcloud.suyuan.widgets.dialog.ChangePriceDialog
import com.qcloud.suyuan.widgets.dialog.ProductDetailsDialog
import kotlinx.android.synthetic.main.card_stock_product_details.*
import kotlinx.android.synthetic.main.card_stock_product_list.*
import timber.log.Timber

/**
 * Description: 库存详情
 * Author: gaobaiqiang
 * 2018/3/24 下午2:35.
 */
class StockDetailsActivity: BaseActivity<IStockDetailsView, StockDetailsPresenterImpl>(), IStockDetailsView, View.OnClickListener {
    private var mAdapter: InStorageAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var productDetailsDialog: ProductDetailsDialog? = null
    private var adjustWarnDialog: AdjustWarnDialog? = null
    private var changePriceDialog: ChangePriceDialog? = null

    override val layoutId: Int
        get() = R.layout.activity_stock_details

    override fun initPresenter(): StockDetailsPresenterImpl? {
        return StockDetailsPresenterImpl()
    }

    override fun initViewAndData() {
        initView()
        initRecyclerView()
    }

    private fun initView() {
        btn_product_details?.setOnClickListener(this)
        btn_product_price?.setOnClickListener(this)
        btn_adjust_warn?.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        list_product?.setLayoutManager(LinearLayoutManager(this))
        PullRefreshUtil.setRefresh(list_product, false, false)

        mAdapter = InStorageAdapter(this)
        list_product.setAdapter(mAdapter!!)
        mAdapter?.onHolderClick = object : CommonRecyclerAdapter.OnHolderClickListener<InStorageRecordBean> {
            override fun onHolderClick(view: View, t: InStorageRecordBean, position: Int) {
                QToast.show(this@StockDetailsActivity, "打印${t.batchNum}")
            }
        }

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        mPresenter?.loadData()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            mPresenter?.onBtnClick(v.id)
        }
    }

    override fun onProductDetailsClick() {
        if (productDetailsDialog == null) {
            productDetailsDialog = ProductDetailsDialog(this)
        }
        productDetailsDialog?.show()
    }

    override fun onProductPriceClick() {
        if (changePriceDialog == null) {
            changePriceDialog = ChangePriceDialog(this)
        }
        changePriceDialog?.show()
    }

    override fun onAdjustWarnClick() {
        if (adjustWarnDialog == null) {
            adjustWarnDialog = AdjustWarnDialog(this)
        }
        adjustWarnDialog?.show()
    }

    override fun replaceList(beans: List<InStorageRecordBean>?) {
        if (isRunning) {
            if (beans != null && beans.isNotEmpty()) {
                mAdapter?.replaceList(beans)
            } else {
                list_product?.showEmptyView()
            }
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isRunning) {
            if (isShow) {
                QToast.show(this, errMsg)
            } else {
                Timber.e(errMsg)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        productDetailsDialog?.let {
            if (productDetailsDialog != null && productDetailsDialog!!.isShowing) {
                productDetailsDialog?.dismiss()
            }
        }

        adjustWarnDialog?.let {
            if (adjustWarnDialog != null && adjustWarnDialog!!.isShowing) {
                adjustWarnDialog?.dismiss()
            }
        }

        changePriceDialog?.let {
            if (changePriceDialog != null && changePriceDialog!!.isShowing) {
                changePriceDialog?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            val intent = Intent(context, StockDetailsActivity::class.java)
            context.startActivity(intent)
        }
    }
}