package com.qcloud.suyuan.ui.record.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.refresh.listener.OnPullUpLoadMoreListener
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshLayout
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.CreditListAdapter
import com.qcloud.suyuan.adapters.OrderInfoAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.CreditInfoBean
import com.qcloud.suyuan.beans.CreditListBean
import com.qcloud.suyuan.beans.RepaymentListBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.ui.record.presenter.impl.CreditRecordPresenterImpl
import com.qcloud.suyuan.ui.record.view.ICreditRecordView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.RepaymentAllDialog
import com.qcloud.suyuan.widgets.dialog.RepaymentDialog
import com.qcloud.suyuan.widgets.dialog.RepaymentHistoryDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
import kotlinx.android.synthetic.main.activity_credit_record.*

/**
 * Description: 赊账记录
 * Author: gaobaiqiang
 * 2018/3/15 上午12:56.
 */
class CreditRecordActivity : BaseActivity<ICreditRecordView, CreditRecordPresenterImpl>(), ICreditRecordView, View.OnClickListener {


    //参数
    private var keyword: String = ""  //搜索
    private var mCurrentCreditId: String = ""//这是当前欠债人的id
    private var mCurrentCreditName: String = ""//这是当前欠债人的名字
    private var mCurrentCreditMoney: Double = 0.00//这是当前欠债人所欠下的债
    private var mCurrentId: String = ""//这是当前欠债单的id
    private var creditMoney: Double = 0.00//该单赊账金额
    private var repayMoney: Double = 0.00//该单已还金额
    private var creditPageNo: Int = 1       //列表页数
    private var orderPageNo: Int = 1        //列表页数
    private var needPay: String = ""

    //其他需要的控件
    private var errtip: TipDialog? = null
    private var repaymentDialog: RepaymentDialog? = null
    private var repaymentAllDialog: RepaymentAllDialog? = null
    private var repaymentHistoryDialog: RepaymentHistoryDialog? = null
    private var creditAdapter: CreditListAdapter? = null
    private var orderAdapter: OrderInfoAdapter? = null
    private var mCreditEmptyView: NoDataView? = null
    private var mOrderEmptyView: NoDataView? = null


    override val layoutId: Int
        get() = R.layout.activity_credit_record

    override fun initPresenter(): CreditRecordPresenterImpl? {
        return CreditRecordPresenterImpl()
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        stopLoadingDialog()
        if (errtip == null) {
            errtip = TipDialog(this)
        }
        errtip?.setTip("${errMsg}")
        if (isShow) {
            errtip?.show()
        }
    }

    override fun initViewAndData() {
        initView()
        initData()
    }


    private fun initView() {
        creditAdapter = CreditListAdapter(this)
        orderAdapter = OrderInfoAdapter(this)
        rv_credit_info_list.setLayoutManager(LinearLayoutManager(this))
        rv_credit_info_list.setAdapter(orderAdapter!!)
        rv_sale_info_list.setLayoutManager(LinearLayoutManager(this))
        rv_sale_info_list.setAdapter(creditAdapter!!)
        PullRefreshUtil.setRefresh(rv_credit_info_list, false, true)
        SwipeRefreshUtil.setLoadMore(rv_sale_info_list, true)
        SwipeRefreshUtil.setColorSchemeColors(rv_sale_info_list, AppConstants.loadColors)
        rv_credit_info_list?.onPullUpLoadMoreListener = object : OnPullUpLoadMoreListener {
            override fun onLoadMore() {
                orderPageNo++
                getCreditInfo()
            }
        }

        rv_sale_info_list?.onRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                creditPageNo = 1
                getCreditList()
            }

        }
        rv_sale_info_list?.onLoadMoreListener = object : SwipeRefreshLayout.OnLoadMoreListener {
            override fun onLoadMore() {
                creditPageNo++
                getCreditList()
            }
        }
        creditAdapter?.onItemClickListener = AdapterView.OnItemClickListener({ adapterView, view, i, l ->
            creditAdapter!!.setItemSelete(i)
            creditAdapter!!.notifyDataSetChanged()
            mCurrentCreditId = creditAdapter!!.mList[i].purchaserId!!
            mCurrentCreditName = creditAdapter!!.mList[i].name!!
            mCurrentCreditMoney = creditAdapter!!.mList[i].sumRepayment!!
            getCreditInfo()
        })
        orderAdapter?.onHolderClick = object : CommonRecyclerAdapter.OnHolderClickListener<CreditInfoBean> {
            override fun onHolderClick(view: View, t: CreditInfoBean, position: Int) {
                mCurrentId = t.id!!
                creditMoney = t.shouldRepayment
                repayMoney = t.alreadyRepayment
                showRepaymentDialog()
            }
        }
        mCreditEmptyView = NoDataView(this)
        mCreditEmptyView?.noData(R.string.tip_no_list)
        mCreditEmptyView?.setImageIcon(R.drawable.bmp_list_empty)
        mOrderEmptyView = NoDataView(this)
        mOrderEmptyView?.noData(R.string.tip_no_list)
        mOrderEmptyView?.setImageIcon(R.drawable.bmp_list_empty)
        rv_credit_info_list.setEmptyView(mCreditEmptyView!!, Gravity.CENTER_HORIZONTAL)
        rv_sale_info_list.setEmptyView(mOrderEmptyView!!, Gravity.CENTER_HORIZONTAL)
        et_search.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_UP) {
                if ((i == KeyEvent.KEYCODE_ENTER)) {
                    KeyBoardUtil.hideKeybord(this, et_search)
                    getCreditList()
                }
            }
            false
        }
        tv_credit_history.setOnClickListener(this)
        tv_all_repayment.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_credit_history -> {
                stopLoadingDialog()
                mPresenter?.repaymentHistory(mCurrentCreditId)
            }
            tv_all_repayment -> showRepaymentAllDialog()
        }
    }

    //展示还款历史对话框
    override fun showRepaymentHistoryDialog(beans: List<RepaymentListBean>, isNext: Boolean) {
        if (repaymentHistoryDialog == null) {
            repaymentHistoryDialog = RepaymentHistoryDialog(this)
        }
        repaymentHistoryDialog?.replaceList(beans!!, isNext)
        repaymentHistoryDialog?.show()
    }

    private fun showRepaymentDialog() {
        if (repaymentDialog == null) {
            repaymentDialog = RepaymentDialog(this)
            repaymentDialog!!.onConfirmClickListener = object : RepaymentDialog.OnConfirmClickListener {
                override fun onConfirmClick() {
                    repayment()
                }

                override fun onRepaymentClick() {
                    repaymentDialog!!.setMoney("${creditMoney - repayMoney}")
                }
            }
        }
        repaymentDialog!!.setMoney("")
        repaymentDialog!!.show()
    }

    //    还清该人所欠下的款
    private fun showRepaymentAllDialog() {
        if (repaymentAllDialog == null) {
            repaymentAllDialog = RepaymentAllDialog(this)
            repaymentAllDialog!!.onConfirmClickListener = object : RepaymentAllDialog.OnConfirmClickListener {
                override fun onConfirmClick() {
                    mPresenter?.repaymentAll(mCurrentCreditMoney.toString(), mCurrentCreditId)
                }
            }
        }
        repaymentAllDialog!!.setMoney("$mCurrentCreditMoney")
        repaymentAllDialog!!.show()
    }

    /*还款成功*/
    override fun repaymentSuccess() {
        if (StringUtil.isNotBlank(needPay) && StringUtil.isMoneyStr(needPay)) {
            loadErr(String.format(getString(R.string.toast_repayment_success), mCurrentCreditName, mCurrentCreditMoney - needPay.toDouble()))
        }
        getCreditList()
        getCreditInfo()
    }

    /**还款**/
    override fun repayment() {
        needPay = repaymentDialog?.getMoney()!!
        mPresenter?.repayment(mCurrentId, needPay!!.toDouble())
    }

    private fun initData() {
        getCreditList()
    }

    override fun getCreditList() {
        keyword = et_search.text.toString()
        mPresenter?.getCreditList(keyword, creditPageNo, AppConstants.PAGE_SIZE)
//        orderAdapter?.removeAll()
        startLoadingDialog()
    }

    override fun getCreditInfo() {
        mPresenter?.getCreditInfo(mCurrentCreditId, creditPageNo, AppConstants.PAGE_SIZE)
    }

    /**
     * 展示总赊账金额
     */
    override fun showCreditMoney(money: String) {
        tv_credit_money.setText("${money}")
    }

    override fun replaceCreditList(beans: List<CreditListBean.ListBean>?, isNext: Boolean) {
        stopLoadingDialog()
        if (isRunning) {
            rv_sale_info_list?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (creditAdapter != null && beans.isNotEmpty()) {
                    creditAdapter!!.replaceList(beans)
                    mCurrentCreditId = beans[0].purchaserId!!
                    mCurrentCreditName = beans[0].name!!
                    mCurrentCreditMoney = beans[0].sumRepayment!!
                    getCreditInfo()
                }
                rv_sale_info_list?.isMore(isNext)
                hideEmptyView()
            } else {
                showEmptyView(resources.getString(R.string.tip_no_data))
            }
        }
    }

    override fun replaceCreditInfo(beans: List<CreditInfoBean>?, isNext: Boolean) {
        if (isRunning) {
            rv_credit_info_list?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (orderAdapter != null) {
                    orderAdapter!!.replaceList(beans)
                }
                rv_credit_info_list?.isMore(isNext)
                hideEmptyView()
            } else {
                showEmptyView(resources.getString(R.string.tip_no_data))
            }
        }
    }

    override fun addCreditListAtEnd(bean: CreditListBean.ListBean?, isNext: Boolean) {
        if (isRunning) {
            rv_sale_info_list?.loadedFinish()
            if (bean != null) {
                creditAdapter?.addBeanAtEnd(bean)
            } else {
                loadErr(resources.getString(R.string.tip_no_data), false)
                rv_sale_info_list?.isMore(false)
            }
        }
    }

    override fun addCreditInfoAtEnd(bean: CreditInfoBean?, isNext: Boolean) {
        if (isRunning) {
            rv_credit_info_list?.loadedFinish()
            if (bean != null) {
                orderAdapter?.addBeanAtEnd(bean)
            } else {
                loadErr(resources.getString(R.string.tip_no_data), false)
                rv_credit_info_list?.isMore(false)
            }
        }
    }

    override fun showEmptyView(tip: String) {
        rv_sale_info_list.showEmptyView()
        rv_credit_info_list.showEmptyView()
    }

    override fun hideEmptyView() {
        rv_sale_info_list.hideEmptyView()
        rv_credit_info_list.hideEmptyView()
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, CreditRecordActivity::class.java))
        }
    }
}