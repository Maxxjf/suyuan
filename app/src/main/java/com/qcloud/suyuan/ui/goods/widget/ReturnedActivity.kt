package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.ReturnGoodsListAdapter
import com.qcloud.suyuan.adapters.ReturnedReceiptAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.ScanCodeBean
import com.qcloud.suyuan.ui.goods.presenter.impl.IReturnedPersenterImpl
import com.qcloud.suyuan.ui.goods.view.IReturnedView
import com.qcloud.suyuan.utils.BarCodeUtil
import com.qcloud.suyuan.utils.PasswordKeyListener
import com.qcloud.suyuan.utils.UserInfoUtil
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.ReturnDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_returned.*
import java.util.concurrent.TimeUnit

/**
 * 类型：ReturnedActivity
 * Author: iceberg
 * Date: 2018/3/19.
 * 退货
 */
class ReturnedActivity : BaseActivity<IReturnedView, IReturnedPersenterImpl>(), IReturnedView, View.OnClickListener {


    private var saleId = ""//销售单id (首次为空,从第二次开始传)

    private var errtip: TipDialog? = null
    private var returnDialog: ReturnDialog? = null
    private var goodsAdapter: ReturnGoodsListAdapter? = null
    private var receiptAdapter: ReturnedReceiptAdapter? = null
    private var mGoodsEmptyView: NoDataView? = null
    private var mReceiptEmptyView: NoDataView? = null
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
        rv_credit_info_list.setLayoutManager(LinearLayoutManager(this))
        rv_credit_info_list.setAdapter(goodsAdapter!!)
        rv_sale_info_list.setLayoutManager(LinearLayoutManager(this))
        rv_sale_info_list.setAdapter(receiptAdapter!!)
        PullRefreshUtil.setRefresh(rv_credit_info_list, false, false)
        PullRefreshUtil.setRefresh(rv_sale_info_list, false, false)
        goodsAdapter?.onHolderClick = object : CommonRecyclerAdapter.OnHolderClickListener<ScanCodeBean.MerchandiseBean> {
            override fun onHolderClick(view: View, t: ScanCodeBean.MerchandiseBean, position: Int) {
                goodsHasDelete(t)
            }
        }
        btn_returned_goods.setOnClickListener(this)

        mGoodsEmptyView = NoDataView(this)
        mGoodsEmptyView?.setImageIcon(R.drawable.bmp_list_empty)
        mGoodsEmptyView?.noData(R.string.tip_no_list)
        mReceiptEmptyView = NoDataView(this)
        mReceiptEmptyView?.setImageIcon(R.drawable.bmp_list_empty)
        mReceiptEmptyView?.noData(R.string.tip_no_list)
        rv_credit_info_list.setEmptyView(mGoodsEmptyView!!, Gravity.CENTER_HORIZONTAL)
        rv_sale_info_list.setEmptyView(mReceiptEmptyView!!, Gravity.CENTER_HORIZONTAL)

        et_search.keyListener = PasswordKeyListener()
        et_search.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_UP) {
                if ((i == KeyEvent.KEYCODE_ENTER)) {
                    KeyBoardUtil.hideKeybord(this, et_search)
                    getScanData()
                }
            }
            false
        }
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

    }

    /**
     * 商品被删除之后
     */
    private fun goodsHasDelete(bean: ScanCodeBean.MerchandiseBean) {
        tv_goods_number.text = "${goodsAdapter!!.mList.size}"
        //假如列表的数据为0时，把销售单ID清空
        if (goodsAdapter!!.mList.size == 0) {
            saleId = ""
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_returned_goods -> showReturnDialog()
//                R.id.btn_confirm -> getScanData()
            }
        }
    }

    /**
     * 扫码成功
     */
    override fun loadDataSuccess(bean: ScanCodeBean) {
        for (item in goodsAdapter?.mList!!) {
            if (StringUtil.isEquals(item.traceabilityId, bean.merchandise!!.traceabilityId)) {
                loadErr(getString(R.string.toast_goods_in_list))
                return
            }
        }
        saleId = bean.saleSerial!!.id!!
        replaceList(bean.infoList, false)
        addListAtEnd(bean.merchandise, false)
        showSaleInfo(bean.saleSerial!!)
    }

    /**
     * 展示销售的详情
     */
    override fun showSaleInfo(bean: ScanCodeBean.SaleSerialBean) {
        layout_info.visibility = View.VISIBLE
        tv_amount.text = "${bean.amount}"
        tv_discount.text = "${bean.discount}"
        tv_real_pay.text = "${bean.realPay}"
        tv_serial_number.text = "${bean.serialNumber}"
        tv_time.text = "${bean.createDate}"
        var store = UserInfoUtil.getStore()
        tv_shop_name.text = "${store?.name}"
        tv_money_getter.text = "${store?.shopkeeperName}"
    }


    private fun getScanData() {
        var code = et_search.text.toString().trim()
        if (StringUtil.isNotBlank(code) && code!!.startsWith("http")) {
            code = BarCodeUtil.disposeQrCode2Suyuan(code!!)
            mPresenter?.loadData(code, saleId)
        } else if (StringUtil.isNotBlank(code)) {
            mPresenter?.loadData(code, saleId)
        } else {
            QToast.show(this, R.string.tip_scan_suyuan_code)
        }
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    et_search.setText("")
                    et_search.requestFocus()
                }

    }

    /**退货确认*/
    private fun showReturnDialog() {
        if (returnDialog == null) {
            returnDialog = ReturnDialog(this)
        }
        if (goodsAdapter == null || goodsAdapter?.itemCount == 0) {
            loadErr(getString(R.string.toast_please_add_goods))
            return
        }
        returnDialog?.setNumber(goodsAdapter?.itemCount!!)
        returnDialog?.onConfirmClickListener = object : ReturnDialog.OnConfirmClickListener {
            override fun onConfirmClick() {
                ReturnGoodsConfirm()
            }

        }
        returnDialog?.show()
    }

    /**
     * 退货成功之后
     */
    override fun returnSuccess() {
        layout_info.visibility = View.INVISIBLE
        saleId = ""
        goodsAdapter!!.removeAll()
        receiptAdapter!!.removeAll()
        tv_goods_number.text = ""
    }

    private fun ReturnGoodsConfirm() {
        var money: String = returnDialog!!.getMoney()
        if (!StringUtil.isMoneyStr(money)) {
            loadErr(getString(R.string.hint_input_money))
            return
        }
        var list = goodsAdapter!!.mList
        if (list == null || list.isEmpty()) {
            loadErr(getString(R.string.toast_list_empty))
            return
        }
        mPresenter?.salesReturn(money, list)
    }

    override fun replaceList(beans: List<ScanCodeBean.InfoListBean>?, isNext: Boolean) {
        if (isRunning) {
            rv_sale_info_list?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (receiptAdapter != null) {
                    receiptAdapter!!.replaceList(beans)
                }
                rv_sale_info_list?.isMore(isNext)
                hideEmptyView()
            } else {
                showEmptyView(resources.getString(R.string.tip_no_data))
            }
        }
    }

    override fun addListAtEnd(bean: ScanCodeBean.MerchandiseBean?, isNext: Boolean) {
        if (isRunning) {
            rv_credit_info_list?.loadedFinish()
            if (bean != null) {
                goodsAdapter?.addBeanAtEnd(bean)
                tv_goods_number.text = "${goodsAdapter!!.mList.size}"
            } else {
                loadErr(resources.getString(R.string.tip_no_data))
                rv_credit_info_list?.isMore(false)
            }
        }
    }

    override fun showEmptyView(tip: String) {
        rv_credit_info_list.showEmptyView()
        rv_sale_info_list.showEmptyView()
    }

    override fun hideEmptyView() {
        rv_credit_info_list.hideEmptyView()
        rv_sale_info_list.hideEmptyView()
    }

    companion object {
        fun openActivity(context: Context) {
            val intent = Intent(context, ReturnedActivity::class.java)
            context.startActivity(intent)
        }
    }
}