package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.widget.AdapterView
import com.qcloud.qclib.refresh.pullrefresh.PullRefreshUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.PutProductAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.ui.goods.presenter.impl.PurchasePresenterImpl
import com.qcloud.suyuan.ui.goods.view.IPurchaseView
import com.qcloud.suyuan.widgets.customview.NoDataView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_purchase.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Description: 进货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:33.
 */
class PurchaseActivity: BaseActivity<IPurchaseView, PurchasePresenterImpl>(), IPurchaseView {
    private var mAdapter: PutProductAdapter? = null
    private var mEmptyView: NoDataView? = null

    private var keyword: String? = null

    override val layoutId: Int
        get() = R.layout.activity_purchase

    override fun initPresenter(): PurchasePresenterImpl? {
        return PurchasePresenterImpl()
    }

    override fun initViewAndData() {
        initRecyclerView()
        initEditView()
    }

    private fun initRecyclerView() {
        list_product?.setLayoutManager(LinearLayoutManager(this))

        PullRefreshUtil.setRefresh(list_product, false, false)

        mAdapter = PutProductAdapter(this)
        list_product?.setAdapter(mAdapter!!)
        mAdapter?.replaceList(ArrayList())
        mAdapter?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, id ->
            val id: String = mAdapter?.mList?.get(position)?.id ?: ""
            PurchaseDetailsActivity.openActivity(this, id)
        }

        mEmptyView = NoDataView(this)
        mEmptyView?.noData(R.string.tip_no_any_product)
        list_product?.setEmptyView(mEmptyView!!, Gravity.CENTER_HORIZONTAL)
        showEmptyView()
    }

    /**
     * 初始化搜索输入框
     * */
    private fun initEditView() {
        et_search.setOnKeyListener { _, action, keyEvent ->
            if (keyEvent != null && keyEvent.action == KeyEvent.ACTION_UP) {
                if (action == KeyEvent.KEYCODE_ENTER) {
                    KeyBoardUtil.hideKeybord(this, et_search)
                    keyword = et_search.text.toString().trim()
                    if (StringUtil.isNotBlank(keyword)) {
                        loadData()
                    } else {
                        QToast.show(this, R.string.toast_no_input_value)
                    }
                }
            }
            false
        }
        btn_search.setOnClickListener {
            KeyBoardUtil.hideKeybord(this, et_search)
            keyword = et_search.text.toString().trim()
            if (StringUtil.isNotBlank(keyword)) {
                loadData()
            } else {
                QToast.show(this, R.string.toast_no_input_value)
            }
        }
    }

    private fun loadData() {
        mPresenter?.loadProduct(keyword!!)
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

    override fun replaceList(beans: List<ProductBean>?) {
        if (isRunning) {
            reSetEditText()
            if (beans != null && beans.isNotEmpty()) {
                mAdapter?.replaceList(beans)
                hideEmptyView()
            } else {
                showEmptyView()
            }
        }
    }

    override fun showEmptyView() {
        list_product?.showEmptyView()
    }

    override fun hideEmptyView() {
        list_product?.hideEmptyView()
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

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, PurchaseActivity::class.java))
        }
    }
}