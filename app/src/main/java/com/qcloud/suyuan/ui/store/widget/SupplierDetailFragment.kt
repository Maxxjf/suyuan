package com.qcloud.suyuan.ui.store.widget

import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseFragment
import com.qcloud.suyuan.ui.store.presenter.impl.SupplierDetailPresenterImpl
import com.qcloud.suyuan.ui.store.view.ISupplierDetailView

/**
 * 类型：SupplierDetailFragment
 * Author: iceberg
 * Date: 2018/3/26.
 * TODO:
 */
class SupplierDetailFragment :BaseFragment<ISupplierDetailView,SupplierDetailPresenterImpl>(),ISupplierDetailView{


    override val layoutId: Int
        get() = R.layout.fragment_supplier_detail

    override fun initPresenter(): SupplierDetailPresenterImpl? = SupplierDetailPresenterImpl()
    override fun loadErr(errMsg: String, isShow: Boolean) {

    }
    override fun initViewAndData() {

    }

    override fun beginLoad() {

    }
}