package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.ReturnGoodsListAdapter
import com.qcloud.suyuan.adapters.ReturnedReceiptAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.ScanCodeBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.goods.presenter.impl.IReturnedPersenterImpl
import com.qcloud.suyuan.ui.goods.view.IReturnedView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.ReturnDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_returned.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * 类型：ReturnedActivity
 * Author: iceberg
 * Date: 2018/3/19.
 * 退货
 */
class ReturnedActivity : BaseActivity<IReturnedView, IReturnedPersenterImpl>(), IReturnedView, View.OnClickListener {


    private var errtip: TipDialog? = null
    private var returnDialog: ReturnDialog? = null
    private var goodsAdapter: ReturnGoodsListAdapter? = null
    private var receiptAdapter: ReturnedReceiptAdapter? = null
    private var mEmptyView: NoDataView? = null
    override val layoutId: Int
        get() = R.layout.activity_returned

    override fun initPresenter(): IReturnedPersenterImpl? = IReturnedPersenterImpl()

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
        goodsAdapter = ReturnGoodsListAdapter(this)
        receiptAdapter = ReturnedReceiptAdapter(this)
        rv_return_goods_list.setLayoutManager(LinearLayoutManager(this))
        rv_return_goods_list.setAdapter(goodsAdapter!!)
        rv_receipt.setLayoutManager(LinearLayoutManager(this))
        rv_receipt.setAdapter(receiptAdapter!!)
        SwipeRefreshUtil.setLoadMore(rv_return_goods_list, true)
        SwipeRefreshUtil.setColorSchemeColors(rv_return_goods_list, AppConstants.loadColors)
        SwipeRefreshUtil.setLoadMore(rv_receipt, true)
        SwipeRefreshUtil.setColorSchemeColors(rv_receipt, AppConstants.loadColors)

        mEmptyView = NoDataView(this)
        rv_return_goods_list.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)
//        rv_receipt.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        et_search.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action==KeyEvent.ACTION_UP){
                if ((i == KeyEvent.KEYCODE_ENTER)) {
                    //et_search.requestFocus()
                    //et_search.isFocusable = false
                    KeyBoardUtil.hideKeybord(this, et_search)
                    getScanData()
                    Timber.e("keyEvent = $i, enter = ${KeyEvent.KEYCODE_ENTER}")
                }
            }
            false
        }
        btn_returned_goods.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_returned_goods -> showReturnDialog()
//                R.id.btn_confirm -> getScanData()
            }
        }
    }



    private fun getScanData() {
        var code = et_search.text.toString().trim()
        if (StringUtil.isBlank(code)) {
            loadErr(getString(R.string.hint_suyuan_code_search))
            return
        }
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    et_search.setText("")
                    et_search.requestFocus()
                    mPresenter?.loadData(code)
                }

    }

    /**退货确认*/
    private fun showReturnDialog() {
        if (returnDialog == null) {
            returnDialog = ReturnDialog(this)
        }
        returnDialog?.setNumber(goodsAdapter?.itemCount!!)
        returnDialog?.onConfirmClickListener = object : ReturnDialog.OnConfirmClickListener {
            override fun onConfirmClick() {
                ReturnGoodsConfirm()
            }

        }
        returnDialog?.show()
    }

    private fun ReturnGoodsConfirm() {
        var money: String = returnDialog!!.getMoney()
//        if (!StringUtil.isMoneyStr(money)) {
//            loadErr(getString(R.string.hint_input_money))
//            return
//        }
        var list = goodsAdapter!!.mList
        if (list == null || list.isEmpty()) {
            loadErr(getString(R.string.toast_list_empty))
            return
        }
        mPresenter?.salesReturn(money, list)
    }

    override fun replaceList(beans: List<ScanCodeBean.InfoListBean>?, isNext: Boolean) {
        if (isRunning) {
            rv_receipt?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (receiptAdapter != null) {
                    receiptAdapter!!.replaceList(beans)
                }
                rv_receipt?.isMore(isNext)
                hideEmptyView()
            } else {
                showEmptyView(resources.getString(R.string.tip_no_data))
            }
        }
    }

    override fun addListAtEnd(bean: ScanCodeBean.MerchandiseBean?, isNext: Boolean) {
        if (isRunning) {
            rv_return_goods_list?.loadedFinish()
            if (bean != null) {
                goodsAdapter?.addBeanAtEnd(bean)
            } else {
                loadErr(resources.getString(R.string.tip_no_data))
                rv_return_goods_list?.isMore(false)
            }
        }
    }

    override fun showEmptyView(tip: String) {
        rv_return_goods_list.showEmptyView()
        rv_receipt.showEmptyView()
    }

    override fun hideEmptyView() {
        rv_return_goods_list.hideEmptyView()
        rv_receipt.hideEmptyView()
    }

    companion object {
        fun openActivity(context: Context) {
            val intent = Intent(context, ReturnedActivity::class.java)
            context.startActivity(intent)
        }
    }
}