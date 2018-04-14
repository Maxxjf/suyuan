package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshLayout
import com.qcloud.qclib.refresh.swiperefresh.SwipeRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.StoreProductAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.beans.ProductClassifyBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.enums.PlatformEnum
import com.qcloud.suyuan.ui.store.presenter.impl.StoreProductPresenterImpl
import com.qcloud.suyuan.ui.store.view.IStoreProductView
import com.qcloud.suyuan.widgets.customview.NoDataView
import com.qcloud.suyuan.widgets.pop.DropDownPop
import com.qcloud.suyuan.widgets.toolbar.CustomToolbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_store_product.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Description: 门店产品
 * Author: gaobaiqiang
 * 2018/3/15 上午12:51.
 */
class StoreProductActivity: BaseActivity<IStoreProductView, StoreProductPresenterImpl>(), IStoreProductView {
    private var mAdapter: StoreProductAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var mClassifyPop: DropDownPop? = null
    private var mTypePop: DropDownPop? = null

    private var pageNo = 1
    private var classifyId: String? = null
    private var platformKey: Int = -1
    private var keyword: String? = null

    override val layoutId: Int
        get() = R.layout.activity_store_product

    override fun initPresenter(): StoreProductPresenterImpl? {
        return StoreProductPresenterImpl()
    }

    override fun initViewAndData() {
        initRecyclerView()
        initEditView()
        initToolbar()
        initType()

        mPresenter?.loadClassify()
    }

    private fun initToolbar() {
        toolbar.onBtnClickListener = object : CustomToolbar.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                if (view.id == R.id.btn_right) {
                    CreateProductIActivity.openActivity(this@StoreProductActivity, null)
                } else {
                    finish()
                }
            }
        }
    }

    private fun initRecyclerView() {
        list_product?.setLayoutManager(LinearLayoutManager(this))

        SwipeRefreshUtil.setLoadMore(list_product, true)
        SwipeRefreshUtil.setColorSchemeColors(list_product, AppConstants.loadColors)
        list_product?.onRefreshListener = object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                pageNo = 1
                loadData()
            }

        }
        list_product?.onLoadMoreListener = object : SwipeRefreshLayout.OnLoadMoreListener {
            override fun onLoadMore() {
                pageNo++
                loadData()
            }
        }

        mAdapter = StoreProductAdapter(this)
        list_product?.setAdapter(mAdapter!!)
        mAdapter?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val id = mAdapter?.mList?.get(position)?.id ?: "-1"
            val isPlatform = mAdapter?.mList?.get(position)?.isPlatform == 1
            StockDetailsActivity.openActivity(this@StoreProductActivity, id, isPlatform)
        }

        mEmptyView = NoDataView(this)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)

        loadData()
    }

    private fun initEditView() {
        et_search.setOnKeyListener { _, action, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_UP) {
                if (action == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtil.hideKeybord(this, et_search)
                    val inputValue = et_search.text.toString().trim()
                    if (StringUtil.isNotBlank(inputValue)) {
                        reSetEditText()
                        keyword = inputValue
                        pageNo = 1
                        loadData()
                    } else {
                        QToast.show(this, R.string.hint_input_product_code_and_name)
                    }
                }
            }
            false
        }
        btn_search.setOnClickListener {
            KeyBoardUtil.hideKeybord(this, et_search)
            val inputValue = et_search.text.toString().trim()
            if (StringUtil.isNotBlank(inputValue)) {
                reSetEditText()
                keyword = inputValue
                pageNo = 1
                loadData()
            } else {
                QToast.show(this, R.string.hint_input_product_code_and_name)
            }
        }
    }

    private fun loadData() {
        mPresenter?.loadData(pageNo, classifyId, platformKey, keyword)
    }

    /**
     * 获取扫码数据
     * */
    private fun reSetEditText() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    et_search.setText("")
                    et_search.requestFocus()
                }
    }

    /**
     * 初始化下拉弹窗
     * */
    private fun initClassify(list: List<ProductClassifyBean>) {
        btn_product_classify.post {
            val width = btn_product_classify.width
            mClassifyPop = DropDownPop(this, list, width)

            mClassifyPop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: Any?) {
                    val bean: ProductClassifyBean = value as ProductClassifyBean
                    classifyId = bean.id
                    keyword = null
                    tv_product_classify.text = ApiReplaceUtil.fromHtml(bean.name)
                    pageNo = 1
                    loadData()
                }
            }
        }

        btn_product_classify.setOnClickListener {
            mClassifyPop?.showAsDropDown(btn_product_classify)
        }
    }

    /**
     * 初始化产品来源弹窗
     * */
    private fun initType() {
        val list: MutableList<String> = ArrayList()
        list.add(PlatformEnum.isAll.value)
        list.add(PlatformEnum.isPlatform.value)
        list.add(PlatformEnum.isPrivate.value)
        btn_product_type.post {
            val width = btn_product_type.width
            mTypePop = DropDownPop(this, list, width)

            mTypePop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: Any?) {
                    val key: String = value as String
                    keyword = null
                    tv_product_type.text = key
                    platformKey = PlatformEnum.getKey(key)
                    pageNo = 1
                    loadData()
                }
            }
        }

        btn_product_type.setOnClickListener {
            mTypePop?.showAsDropDown(btn_product_type)
        }
    }

    override fun replaceClassifyList(list: List<ProductClassifyBean>?) {
        if (isRunning) {
            if (list != null && list.isNotEmpty()) {
                initClassify(list)
            }
        }
    }

    override fun replaceList(beans: List<ProductBean>?, isNext: Boolean, total: Int) {
        if (isRunning) {
            list_product?.loadedFinish()
            tv_total_product.text = "$total"
            if (beans != null && beans.isNotEmpty()) {
                if (mAdapter != null) {
                    mAdapter!!.replaceList(beans)
                }
                list_product?.isMore(isNext)
                hideEmptyView()
            } else {
                showEmptyView(resources.getString(R.string.tip_no_data))
            }
        }
    }

    override fun addListAtEnd(beans: List<ProductBean>?, isNext: Boolean) {
        if (isRunning) {
            list_product?.loadedFinish()
            if (beans != null && beans.isNotEmpty()) {
                if (mAdapter != null) {
                    mAdapter!!.addListAtEnd(beans)
                }
                list_product?.isMore(isNext)
            } else {
                QToast.show(this, R.string.toast_no_more_data)
                list_product?.isMore(false)
            }
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isRunning) {
            list_product?.loadedFinish()
            if (isShow) {
                QToast.show(this, errMsg)
            } else {
                Timber.e(errMsg)
            }
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
        mClassifyPop.let {
            if (mClassifyPop != null && mClassifyPop!!.isShowing) {
                mClassifyPop?.dismiss()
            }
        }

        mTypePop.let {
            if (mTypePop != null && mTypePop!!.isShowing) {
                mTypePop?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, StoreProductActivity::class.java))
        }
    }
}