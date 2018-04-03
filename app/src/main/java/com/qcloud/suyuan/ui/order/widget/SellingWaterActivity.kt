package com.qcloud.suyuan.ui.order.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.SaleInfoAdapter
import com.qcloud.suyuan.adapters.SaleListAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.CodeBean
import com.qcloud.suyuan.beans.SaleInfoBean
import com.qcloud.suyuan.beans.SaleListBean
import com.qcloud.suyuan.ui.order.presenter.impl.SellingWaterPresenterImpl
import com.qcloud.suyuan.ui.order.view.ISellingWaterView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.TipDialog
import kotlinx.android.synthetic.main.activity_selling_water.*

/**
 * Description: 销售流水
 * Author: gaobaiqiang
 * 2018/3/15 上午12:42.
 */
class SellingWaterActivity : BaseActivity<ISellingWaterView, SellingWaterPresenterImpl>(), ISellingWaterView, View.OnClickListener {


    var errtip: TipDialog? = null
    private var mWaterEmptyView: NoDataView? = null
    private var mReceiptEmptyView: NoDataView? = null
    private var saleListAdapter: SaleListAdapter? = null
    private var saleInfoAdapter: SaleInfoAdapter? = null


    override val layoutId: Int
        get() = R.layout.activity_selling_water

    override fun initPresenter(): SellingWaterPresenterImpl? {
        return SellingWaterPresenterImpl()
    }

    override fun initViewAndData() {
        initView()
        loadData()
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {

        if (errtip == null) {
            errtip = TipDialog(this)
        }
        errtip?.setTip("${errMsg}")
        if (isShow) {
            errtip?.show()
        }
    }

    private fun initView() {
        saleListAdapter = SaleListAdapter(this)
        saleInfoAdapter = SaleInfoAdapter(this)
        saleListAdapter?.onItemClickListener = AdapterView.OnItemClickListener({ _, _, i, _ ->
            mPresenter?.getSaleInfo(saleListAdapter!!.mList[i].id!!)
        })
        rv_sale_list.setLayoutManager(LinearLayoutManager(this))
        rv_sale_list.setAdapter(saleListAdapter!!)
        rv_sale_info_list.setLayoutManager(LinearLayoutManager(this))
        rv_sale_info_list.setAdapter(saleInfoAdapter!!)
        PullRefreshUtil.setRefresh(rv_sale_list, false, false)
        PullRefreshUtil.setRefresh(rv_sale_info_list, false, false)

        mWaterEmptyView = NoDataView(this)
        mWaterEmptyView?.setImageIcon(R.drawable.bmp_list_empty)
        mWaterEmptyView?.noData(R.string.tip_no_list)
        mReceiptEmptyView = NoDataView(this)
        mReceiptEmptyView?.setImageIcon(R.drawable.bmp_list_empty)
        mReceiptEmptyView?.noData(R.string.tip_no_list)
        rv_sale_list.setEmptyView(mWaterEmptyView!!, Gravity.CENTER_HORIZONTAL)
        rv_sale_info_list.setEmptyView(mReceiptEmptyView!!, Gravity.CENTER_HORIZONTAL)

        et_search.setOnKeyListener { view: View?, i: Int, keyEvent: KeyEvent? ->
            if (keyEvent != null) {
                if (keyEvent.action == KeyEvent.ACTION_UP) {
                    if ((i == KeyEvent.KEYCODE_ENTER)) {
                        KeyBoardUtil.hideKeybord(this, et_search)
                        loadData()
                    }
                }
            }
            false
        }
    }

    override fun onClick(p0: View?) {

    }

    override fun loadData() {
        var keyword = et_search.text.toString()
        mPresenter?.getSaleList(keyword)
    }

    override fun replaceSaleList(beans: List<SaleListBean>?, isNext: Boolean) {
        if (isRunning) {
            rv_sale_list.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                saleListAdapter?.replaceList(beans)
                hideEmptyView()
            } else {
                showEmptyView(getString(R.string.tip_no_data))
            }
        }
    }

    override fun replaceSaleInfoList(beans: List<SaleInfoBean.ListBean>?, isNext: Boolean) {
        if (isRunning) {
            rv_sale_info_list.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                saleInfoAdapter?.replaceList(beans)
                hideEmptyView()
            } else {
                showEmptyView(getString(R.string.tip_no_data))
            }
        }
    }

    override fun addListAtEnd(bean: CodeBean?, isNext: Boolean) {
        if (isRunning) {
            rv_sale_list.loadedFinish()
            if (bean != null) {
//                saleListAdapter?.addBeanAtEnd(bean)
                hideEmptyView()
            } else {
                loadErr(getString(R.string.tip_no_data))
            }
        }
    }

    override fun showEmptyView(tip: String) {
        rv_sale_list.showEmptyView()
//        rv_sale_info_list.showEmptyView()
    }

    override fun hideEmptyView() {
        rv_sale_list.hideEmptyView()
//        rv_sale_info_list.hideEmptyView()
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SellingWaterActivity::class.java))
        }
    }
}