package com.qcloud.suyuan.ui.store.widget

import android.annotation.SuppressLint
import com.qcloud.qclib.beans.RxBusEvent
import com.qcloud.qclib.rxbus.BusProvider
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseFragment
import com.qcloud.suyuan.beans.SupplierBean
import com.qcloud.suyuan.ui.store.presenter.impl.SupplierDetailPresenterImpl
import com.qcloud.suyuan.ui.store.view.ISupplierDetailView
import com.qcloud.suyuan.widgets.dialog.TipDialog
import kotlinx.android.synthetic.main.fragment_supplier_detail.*

@SuppressLint("ValidFragment")
/**
 * 类型：SupplierDetailFragment
 * Author: iceberg
 * Date: 2018/3/26.
 * 供应商详情
 */
class SupplierDetailFragment (mSupplierBean: SupplierBean) :BaseFragment<ISupplierDetailView,SupplierDetailPresenterImpl>(),ISupplierDetailView{

    private var tip:TipDialog?=null
    private var mCurrentBean: SupplierBean?=null
    override val layoutId: Int
        get() = R.layout.fragment_supplier_detail

    init {
        mCurrentBean=mSupplierBean
    }
    override fun initPresenter(): SupplierDetailPresenterImpl? = SupplierDetailPresenterImpl()
    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (tip==null){
            tip= TipDialog(mContext!!)
        }
        if (isShow){
            tip?.setTip(errMsg)
            tip?.show()
        }
    }
    override fun replaceInfo(bean:SupplierBean){
        mCurrentBean=bean
        if (mCurrentBean!=null){
            tv_number?.setText("${mCurrentBean!!.id}")
            tv_name.setText("${mCurrentBean!!.name}")
            tv_address.setText("${mCurrentBean!!.address}")
            tv_person.setText("${mCurrentBean!!.principal}")
            tv_phone.setText("${mCurrentBean!!.phone}")
            tv_remark.setText("${mCurrentBean!!.remark}")
        }
    }

    override fun initViewAndData() {
        if (mCurrentBean!=null){
            tv_number?.setText("${mCurrentBean!!.id}")
            tv_name.setText("${mCurrentBean!!.name}")
            tv_address.setText("${mCurrentBean!!.address}")
            tv_person.setText("${mCurrentBean!!.principal}")
            tv_phone.setText("${mCurrentBean!!.phone}")
            tv_remark.setText("${mCurrentBean!!.remark}")
        }
        btn_edit.setOnClickListener({_ ->
            if (mContext is MySupplierActivity){
                (mContext as MySupplierActivity).showEditFragment()
            }
            BusProvider.instance.post(RxBusEvent.newBuilder(R.id.id_clidk_edit_supplier).setObj(mCurrentBean).build())
        })
    }

    override fun beginLoad() {

    }

}