package com.qcloud.suyuan.ui.order.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.ReturnGoodsListAdapter
import com.qcloud.suyuan.adapters.ReturnedReceiptAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.CodeBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.order.presenter.impl.SellingWaterPresenterImpl
import com.qcloud.suyuan.ui.order.view.ISellingWaterView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.TipDialog
import kotlinx.android.synthetic.main.activity_selling_water.*
import timber.log.Timber

/**
 * Description: 销售流水
 * Author: gaobaiqiang
 * 2018/3/15 上午12:42.
 */
class SellingWaterActivity : BaseActivity<ISellingWaterView, SellingWaterPresenterImpl>(), ISellingWaterView, View.OnClickListener {


    var errtip: TipDialog? = null
    private var mEmptyView: NoDataView? = null
    private var waterAdapter: ReturnGoodsListAdapter? = null
    private var receiptAdapter: ReturnedReceiptAdapter? = null


    override val layoutId: Int
        get() = R.layout.activity_selling_water

    override fun initPresenter(): SellingWaterPresenterImpl? {
        return SellingWaterPresenterImpl()
    }

    override fun initViewAndData() {
        initView()
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
        rv_selling_water_list.setLayoutManager(LinearLayoutManager(this))
        rv_selling_water_list.setAdapter(waterAdapter!!)
        rv_receipt.setLayoutManager(LinearLayoutManager(this))
        rv_receipt.setAdapter(receiptAdapter!!)
        SwipeRefreshUtil.setLoadMore(rv_selling_water_list, false)
        SwipeRefreshUtil.setColorSchemeColors(rv_selling_water_list, AppConstants.loadColors)
        SwipeRefreshUtil.setLoadMore(rv_receipt, false)
        SwipeRefreshUtil.setColorSchemeColors(rv_receipt, AppConstants.loadColors)

        mEmptyView = NoDataView(this)
        rv_selling_water_list.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)
        rv_receipt.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        waterAdapter = ReturnGoodsListAdapter(this)
        receiptAdapter = ReturnedReceiptAdapter(this)
        et_search.setOnKeyListener{view: View?, i: Int, keyEvent: KeyEvent? ->
            if ((i == KeyEvent.KEYCODE_ENTER)) {
                KeyBoardUtil.hideKeybord(this, et_search)
//                getDate()
                Timber.e("keyEvent = $i, enter = ${KeyEvent.KEYCODE_ENTER}")
            }
            false
        }
    }

    override fun onClick(p0: View?) {

    }

    override fun replaceList(beans: List<CodeBean>?, isNext: Boolean) {
        if (isRunning){
            rv_receipt.loadedFinish()
            if (beans!=null && beans.isNotEmpty()){
//                receiptAdapter?.replaceList(beans)
                hideEmptyView()
            }else{
                showEmptyView(getString(R.string.tip_no_data))
            }
        }
    }

    override fun addListAtEnd(bean: CodeBean?, isNext: Boolean) {
        if (isRunning){
            rv_selling_water_list.loadedFinish()
            if (bean!=null){
//                waterAdapter?.addBeanAtEnd(bean)
                hideEmptyView()
            }else{
                loadErr(getString(R.string.tip_no_data))
            }
        }
    }

    override fun showEmptyView(tip: String) {
        rv_selling_water_list.showEmptyView()
        rv_receipt.showEmptyView()
    }

    override fun hideEmptyView() {
         rv_selling_water_list.hideEmptyView()
         rv_receipt.hideEmptyView()
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SellingWaterActivity::class.java))
        }
    }
}