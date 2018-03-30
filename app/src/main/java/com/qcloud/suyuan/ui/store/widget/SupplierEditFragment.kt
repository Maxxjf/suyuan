package com.qcloud.suyuan.ui.store.widget

import android.view.View
import android.widget.TextView
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseFragment
import com.qcloud.suyuan.beans.SupplierBean
import com.qcloud.suyuan.ui.store.presenter.impl.SupplierEditPresenterImpl
import com.qcloud.suyuan.ui.store.view.ISupplierEditView
import com.qcloud.suyuan.widgets.dialog.InputDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
import kotlinx.android.synthetic.main.fragment_supplier_edit.*

/**
 * 类型：SupplierEditFragment
 * Author: iceberg
 * Date: 2018/3/26.
 * 修改供应商
 */
class SupplierEditFragment :BaseFragment<ISupplierEditView,SupplierEditPresenterImpl>(),ISupplierEditView, View.OnClickListener {

    private var name: String=""         //名称
    private var Editress: String=""      //地址
    private var person: String=""        //联系人
    private var phone: String=""         //联系电话
    private var remark: String=""          //备注
    private var mCurrentBean:SupplierBean?=null  //当前供应商


    var errTip:TipDialog?=null
    var input:InputDialog?=null
    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (errTip==null){
            errTip=TipDialog(mContext!!)
        }
        errTip?.setTip(errMsg)
        if (isShow){
            errTip?.show()
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_supplier_edit

    override fun initPresenter(): SupplierEditPresenterImpl? = SupplierEditPresenterImpl()

    override fun initViewAndData() {
        tv_name.setOnClickListener(this)
        tv_address.setOnClickListener(this)
        tv_person.setOnClickListener(this)
        tv_phone.setOnClickListener(this)
        tv_remark.setOnClickListener(this)
        btn_edit.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v){
            tv_name ->showInputDialog(tv_name)
            tv_address ->showInputDialog(tv_address)
            tv_person ->showInputDialog(tv_person)
            tv_phone ->showInputDialog(tv_phone)
            tv_remark ->showInputDialog(tv_remark)
            btn_edit -> editClick()
        }
    }

    private fun showInputDialog(view: TextView) {
        if(input==null){
            input= InputDialog(mContext!!)
        }
        input?.setBindView(view)
        input?.setInputValue(view.text.toString().trim())
        input?.show()
    }

    /**新增按钮点击**/
    override fun editClick(){
        if (check()){
            mPresenter?.editSupplier(Editress, mCurrentBean?.id!!,name,phone,person,remark)
            if (mContext is MySupplierActivity){
                (mContext as MySupplierActivity).hasEdit()
            }
        }
    }
    override fun replaceInfo(bean:SupplierBean){
        mCurrentBean=bean
        if (mCurrentBean!=null){
            tv_name.setText("${mCurrentBean!!.name}")
            tv_address.setText("${mCurrentBean!!.address}")
            tv_person.setText("${mCurrentBean!!.principal}")
            tv_phone.setText("${mCurrentBean!!.phone}")
            tv_remark.setText("${mCurrentBean!!.remark}")
        }
    }
    //清空输入框
    override fun clearEdit(){
        tv_name.setText("")
        tv_address.setText("")
        tv_person.setText("")
        tv_phone.setText("")
        tv_remark.setText("")
    }

    //检查输入框
    fun check():Boolean{
         name=tv_name.text.toString().trim()
         Editress=tv_address.text.toString().trim()
         person=tv_person.text.toString().trim()
         phone=tv_phone.text.toString().trim()
         remark=tv_remark.text.toString().trim()
        if (StringUtil.isBlank(name)){
            loadErr(mContext!!.resources.getString(R.string.hint_input_name_supplier),true)
            return  false
        }
        if(mCurrentBean==null){
            loadErr(mContext!!.resources.getString(R.string.tip_err_return_tip),true)
            return false
        }
        return  true
    }

    override fun beginLoad() {

    }

}