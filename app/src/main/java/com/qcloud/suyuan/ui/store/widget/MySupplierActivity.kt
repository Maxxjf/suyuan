package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.View
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.store.presenter.impl.MySupplierPresenterImpl
import com.qcloud.suyuan.ui.store.view.IMySupplierView

/**
 * Description: 我的供应商
 * Author: gaobaiqiang
 * 2018/3/20 上午9:04.
 */
class MySupplierActivity : BaseActivity<IMySupplierView, MySupplierPresenterImpl>(), IMySupplierView, View.OnClickListener {


    private var mAddFragment: SupplierAddFragment = SupplierAddFragment()
    private var mDetailFragment: SupplierDetailFragment = SupplierDetailFragment()


    override val layoutId: Int
        get() = R.layout.activity_my_supplier

    override fun initPresenter(): MySupplierPresenterImpl? {
        return MySupplierPresenterImpl()
    }

    override fun initViewAndData() {

    }

    override fun onClick(v: View?) {

    }

    fun showAddFragment(){
        if (mAddFragment!=null){
            mAddFragment= SupplierAddFragment()
        }
        replaceFragment(mAddFragment,R.id.fragment_container,false)
    }

    fun showDetailFragment(){
        if (mDetailFragment!=null){
            mDetailFragment= SupplierDetailFragment()
        }
        replaceFragment(mDetailFragment,R.id.fragment_container,false)
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, MySupplierActivity::class.java))
        }
    }
}