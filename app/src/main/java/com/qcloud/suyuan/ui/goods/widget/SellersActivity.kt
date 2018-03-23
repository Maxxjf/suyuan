package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.widget.customview.wheelview.DatePicker
import com.qcloud.qclib.widget.customview.wheelview.DateTimePicker
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.SellersAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.SellersBean
import com.qcloud.suyuan.ui.goods.presenter.impl.SellersPresenterImpl
import com.qcloud.suyuan.ui.goods.view.ISellersView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.CashDialog
import com.qcloud.suyuan.widgets.dialog.InputPurchaseDialog
import com.qcloud.suyuan.widgets.dialog.SettlementDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
import com.qcloud.suyuan.widgets.pop.DropDownPop
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.card_sellers_product_list.*
import kotlinx.android.synthetic.main.card_sellers_purchaser_info.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Description: 卖货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:23.
 */
class SellersActivity: BaseActivity<ISellersView, SellersPresenterImpl>(), ISellersView, View.OnClickListener {
    private var mAdapter: SellersAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var mPurchaseUsePop: DropDownPop? = null

    private var tipDialog: TipDialog? = null
    private var settlementDialog: SettlementDialog? = null
    private var cashDialog: CashDialog? = null
    private var inputPurchaseDialog: InputPurchaseDialog? = null

    override val layoutId: Int
        get() = R.layout.activity_sellers

    override fun initPresenter(): SellersPresenterImpl? {
        return SellersPresenterImpl()
    }

    override fun initViewAndData() {
        initView()
        initRecyclerView()
        initEditView()
        initDropDown()
    }

    private fun initView() {
        btn_settlement.setOnClickListener(this)
        btn_input_purchase_info.setOnClickListener(this)
    }

    /**
     * 初始化列表
     * */
    private fun initRecyclerView() {
        list_product?.setLayoutManager(LinearLayoutManager(this))

        PullRefreshUtil.setRefresh(list_product, false, false)

        mAdapter = SellersAdapter(this)
        list_product?.setAdapter(mAdapter!!)

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        mPresenter?.loadData()
    }

    /**
     * 初始化搜索输入框
     * */
    private fun initEditView() {
//        et_search.setOnEditorActionListener { _, action, keyEvent ->
//            if (action == EditorInfo.IME_ACTION_SEARCH
//                    || action == EditorInfo.IME_ACTION_DONE) {
//                et_search.requestFocus()
//                //KeyBoardUtil.hideKeybord(this, et_search)
//                Timber.e("acton = $action, search = ${EditorInfo.IME_ACTION_SEARCH}, done = ${EditorInfo.IME_ACTION_DONE}")
//            }
//            false
//        }
        et_search.setOnKeyListener { view, i, keyEvent ->
            if ((i == KeyEvent.KEYCODE_ENTER)) {
                //et_search.requestFocus()
                //et_search.isFocusable = false
                KeyBoardUtil.hideKeybord(this, et_search)
                toGet()
                Timber.e("keyEvent = $i, enter = ${KeyEvent.KEYCODE_ENTER}")
            }
            false
        }
    }

    /**
     * 初始化下拉弹窗
     * */
    private fun initDropDown() {
        val list: MutableList<String> = ArrayList()
        list.add("病虫防治1")
        list.add("病虫防治2")
        list.add("病虫防治3")
        list.add("病虫防治4")
        list.add("病虫防治5")
        btn_purchase_use.post {
            val width = btn_purchase_use.width
            mPurchaseUsePop = DropDownPop(this, list, width)

            mPurchaseUsePop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: String) {
                    tv_purchase_use.text = value
                }
            }
        }

        btn_purchase_use.setOnClickListener {
            mPurchaseUsePop?.showAsDropDown(btn_purchase_use)
        }
    }

    private fun toGet() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    et_search.setText("")
                    et_search.requestFocus()
//                    et_search.isFocusable = true
//                    et_search.isEnabled = true
                }

    }

    override fun onClick(v: View) {
        mPresenter?.onBtnClick(v.id)
    }

    override fun onSettlementClick() {
        if (tipDialog == null) {
            tipDialog = TipDialog(this)
        }
        tipDialog?.setTip(R.string.tip_buy_highly_toxic)
        tipDialog?.setConfirmBtn(R.string.btn_i_know)
        tipDialog?.show()
        tipDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                showSettlementDialog()
            }
        }
    }

    private fun showSettlementDialog() {
        if (settlementDialog == null) {
            settlementDialog = SettlementDialog(this)
        }
        settlementDialog?.show()
        settlementDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                when (view.id) {
                    R.id.btn_cash -> showCashDialog()
                }
            }
        }
    }

    private fun showCashDialog() {
        if (cashDialog == null) {
            cashDialog = CashDialog(this)
        }
        cashDialog?.show()
        cashDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                QToast.info(this@SellersActivity, R.string.tip_printing_suyuan_code, false)
            }
        }
    }

    override fun onInputPurchaserClick() {
        if (inputPurchaseDialog == null) {
            inputPurchaseDialog = InputPurchaseDialog(this)
        }
        inputPurchaseDialog?.show()
    }

    override fun replaceList(beans: List<SellersBean>?) {
        if (isRunning) {
            if (beans != null && beans.isNotEmpty()) {
                mAdapter?.replaceList(beans)
            }
        }
    }

    override fun addBeanAtEnd(bean: SellersBean) {
        if (isRunning) {
            mAdapter?.addBeanAtEnd(bean)
        }
    }

    override fun showEmptyView(tip: String) {
        list_product?.showEmptyView()
    }

    override fun hideEmptyView() {
       list_product?.hideEmptyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPurchaseUsePop.let {
            if (mPurchaseUsePop != null && mPurchaseUsePop!!.isShowing) {
                mPurchaseUsePop?.dismiss()
            }
        }
        tipDialog.let {
            if (tipDialog != null && tipDialog!!.isShowing) {
                tipDialog?.dismiss()
            }
        }
        settlementDialog.let {
            if (settlementDialog != null && settlementDialog!!.isShowing) {
                settlementDialog?.dismiss()
            }
        }
        cashDialog.let {
            if (cashDialog != null && cashDialog!!.isShowing) {
                cashDialog?.dismiss()
            }
        }
        inputPurchaseDialog.let {
            if (inputPurchaseDialog != null && inputPurchaseDialog!!.isShowing) {
                inputPurchaseDialog?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, SellersActivity::class.java))
        }
    }
}