package com.qcloud.suyuan.ui.store.widget

import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseFragment
import com.qcloud.suyuan.ui.store.presenter.impl.SupplierAddPresenterImpl
import com.qcloud.suyuan.ui.store.view.ISupplierAddView

/**
 * 类型：SupplierAddFragment
 * Author: iceberg
 * Date: 2018/3/26.
 * 新增供应商
 */
class SupplierAddFragment:BaseFragment<ISupplierAddView,SupplierAddPresenterImpl>(),ISupplierAddView {
    override fun loadErr(errMsg: String, isShow: Boolean) {

    }

    override val layoutId: Int
        get() = R.layout.fragment_supplier_add

    override fun initPresenter(): SupplierAddPresenterImpl? = SupplierAddPresenterImpl()

    override fun initViewAndData() {

    }

    override fun beginLoad() {

    }

}