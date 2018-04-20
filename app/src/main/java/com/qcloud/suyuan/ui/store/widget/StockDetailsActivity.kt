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
import com.qcloud.suyuan.beans.InStorageRecordBean
import com.qcloud.suyuan.beans.PrintBean
import com.qcloud.suyuan.beans.PrintContentBean
import com.qcloud.suyuan.beans.ProductDetailsBean
import com.qcloud.suyuan.ui.store.presenter.impl.StockDetailsPresenterImpl
import com.qcloud.suyuan.ui.store.view.IStockDetailsView
import com.qcloud.suyuan.utils.PrintHelper
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.dialog.ProductDetailsDialog
import com.qcloud.suyuan.widgets.toolbar.CustomToolbar
import kotlinx.android.synthetic.main.activity_stock_details.*
import kotlinx.android.synthetic.main.card_stock_product_details.*
import kotlinx.android.synthetic.main.card_stock_product_list.*
import timber.log.Timber

/**
 * Description: 库存详情
 * Author: gaobaiqiang
 * 2018/3/24 下午2:35.
 */
class StockDetailsActivity : BaseActivity<IStockDetailsView, StockDetailsPresenterImpl>(), IStockDetailsView, View.OnClickListener {
    private var mAdapter: InStorageAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var productDetailsDialog: ProductDetailsDialog? = null

    private var currId: String = "-1"
    private var currBean: ProductDetailsBean? = null
    private var isPlatform: Boolean = false

    override val layoutId: Int
        get() = R.layout.activity_stock_details

    override fun initPresenter(): StockDetailsPresenterImpl? {
        return StockDetailsPresenterImpl()
    }

    override fun initViewAndData() {
        currId = intent.getStringExtra("ID")
        isPlatform = intent.getBooleanExtra("IS_PLATFORM", false)
        initView()
        initToolbar()
        initRecyclerView()
    }

    private fun initView() {
        btn_product_details?.setOnClickListener(this)
    }

    private fun initToolbar() {
        if (!isPlatform) {
            // 私有产品
            toolbar.showRight(true)
            toolbar.onBtnClickListener = object : CustomToolbar.OnBtnClickListener {
                override fun onBtnClick(view: View) {
                    if (view.id == R.id.btn_right) {
                        CreateProductIActivity.openActivity(this@StockDetailsActivity, currId)
                    } else {
                        finish()
                    }
                }
            }
        } else {
            toolbar.showRight(false)
        }
    }

    private fun initRecyclerView() {
        list_product?.setLayoutManager(LinearLayoutManager(this))
        PullRefreshUtil.setRefresh(list_product, false, false)

        mAdapter = InStorageAdapter(this)
        list_product.setAdapter(mAdapter!!)
        mAdapter?.onHolderClick = object : CommonRecyclerAdapter.OnHolderClickListener<InStorageRecordBean> {
            override fun onHolderClick(view: View, t: InStorageRecordBean, position: Int) {
                val contentBean = PrintContentBean()
                contentBean.content = currBean?.info?.name
                contentBean.alignIndex = 1
                contentBean.isWalk = 1

                val printBean = PrintBean()
                printBean.type = 1
                printBean.content = contentBean
                printBean.barCode = t.batchNum
                PrintHelper.instance.printData(printBean)
            }
        }

        mEmptyView = NoDataView(this)
        mEmptyView?.noData(R.string.tip_no_purchase_record)
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
            var millBean =bean.mill
            if (infoBean != null) {
                with(infoBean) {
                    // 图片
                    GlideUtil.loadImage(this@StockDetailsActivity, img_product, imageUrl, R.drawable.bmp_product)
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
            if (millBean!=null){
                //生产厂家
                tv_product_manufacture.text = millBean.name
            }
            // 库存
            val totalNumberStr = resources.getString(R.string.tag_total_stock_number)
            tv_total_stock.text = String.format(totalNumberStr, bean.totalAmount)
            tv_product_price.text = bean.priceStr
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
    }

    companion object {
        fun openActivity(@NonNull context: Context, id: String, isPlatform: Boolean) {
            val intent = Intent(context, StockDetailsActivity::class.java)
            intent.putExtra("ID", id)
            intent.putExtra("IS_PLATFORM", isPlatform)
            context.startActivity(intent)
        }
    }
}