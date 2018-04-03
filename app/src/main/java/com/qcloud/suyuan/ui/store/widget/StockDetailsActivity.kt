package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.InStorageAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.InStorageRecordBean
import com.qcloud.suyuan.beans.PrintBean
import com.qcloud.suyuan.beans.ProductDetailsBean
import com.qcloud.suyuan.ui.store.presenter.impl.StockDetailsPresenterImpl
import com.qcloud.suyuan.ui.store.view.IStockDetailsView
import com.qcloud.suyuan.utils.PrintHelper
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

    private var currId: String = "-1"
    private var currBean: ProductDetailsBean? = null

    override val layoutId: Int
        get() = R.layout.activity_stock_details

    override fun initPresenter(): StockDetailsPresenterImpl? {
        return StockDetailsPresenterImpl()
    }

    override fun initViewAndData() {
        currId = intent.getStringExtra("ID")
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
                val printBean = PrintBean()
                printBean.type = 1
                printBean.barCode = t.batchNum
                PrintHelper.instance.printData(printBean)
            }
        }

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        mPresenter?.loadData(currId)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            mPresenter?.onBtnClick(v.id)
        }
    }

    override fun onProductDetailsClick() {
        if (currBean == null) {
            QToast.show(this, R.string.toast_get_product_details_failure)
            return
        }
        if (productDetailsDialog == null) {
            productDetailsDialog = ProductDetailsDialog(this)
        }
        if (currBean != null) {
            productDetailsDialog?.refreshData(currBean!!)
        }
        productDetailsDialog?.show()
    }

    override fun onProductPriceClick() {
        if (currBean == null) {
            QToast.show(this, R.string.toast_get_product_details_failure)
            return
        }
        if (changePriceDialog == null) {
            changePriceDialog = ChangePriceDialog(this)
        }
        changePriceDialog?.show()
        changePriceDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                val price: Double = changePriceDialog?.price ?: 0.00
                mPresenter?.editPrice(currId, price)
            }
        }
    }

    override fun onAdjustWarnClick() {
        if (currBean == null) {
            QToast.show(this, R.string.toast_get_product_details_failure)
            return
        }
        if (adjustWarnDialog == null) {
            adjustWarnDialog = AdjustWarnDialog(this)
        }
        adjustWarnDialog?.show()
        adjustWarnDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                val warnLine: Int = adjustWarnDialog?.warnLine ?: 0
                mPresenter?.editWarnLine(currId, warnLine)
            }
        }
    }

    override fun refreshData(bean: ProductDetailsBean?) {
        if (isRunning) {
            if (bean != null) {
                currBean = bean
                refreshInfo(bean)
                replaceList(bean.warehouseList)
            } else {
                loadErr("加载出错了")
            }
        }
    }

    override fun refreshInfo(bean: ProductDetailsBean) {
        if (isRunning) {
            val infoBean = bean.info
            val goodsBean = bean.goods
            if (infoBean != null) {
                with(infoBean) {
                    // 图片
                    GlideUtil.loadImage(this@StockDetailsActivity, img_product, image, R.drawable.bmp_product)
                    // 名称
                    tv_product_name.text = name
                    // 规格
                    tv_product_spec.text = specification
                    // 单位
                    tv_product_unit.text = unit
                }

            }
            if (goodsBean != null) {
                // 条形码
                tv_product_bar_code?.text = goodsBean.barCode
                // 分类
                tv_product_classify.text = goodsBean.classifyName
            }
            // 库存
            val totalNumberStr = resources.getString(R.string.tag_total_stock_number)
            tv_total_stock.text = String.format(totalNumberStr, bean.totalAmount)
        }
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

    override fun editWarnLineSuccess() {
        if (isRunning) {
            QToast.success(this, R.string.toast_edit_warn_line_success)
        }
    }

    override fun editPriceSuccess() {
        if (isRunning) {
            QToast.success(this, R.string.toast_edit_price_success)
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
        fun openActivity(@NonNull context: Context, id: String) {
            val intent = Intent(context, StockDetailsActivity::class.java)
            intent.putExtra("ID", id)
            context.startActivity(intent)
        }
    }
}