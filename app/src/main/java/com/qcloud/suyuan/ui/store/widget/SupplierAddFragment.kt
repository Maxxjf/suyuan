package com.qcloud.suyuan.ui.store.widget

import android.view.View
import android.widget.TextView
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseFragment
import com.qcloud.suyuan.ui.store.presenter.impl.SupplierAddPresenterImpl
import com.qcloud.suyuan.ui.store.view.ISupplierAddView
import com.qcloud.suyuan.widgets.dialog.InputDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
import kotlinx.android.synthetic.main.fragment_supplier_add.*

/**
 * 类型：SupplierAddFragment
 * Author: iceberg
 * Date: 2018/3/26.
 * 新增供应商
 */
class SupplierAddFragment : BaseFragment<ISupplierAddView, SupplierAddPresenterImpl>(), ISupplierAddView, View.OnClickListener {

    private var name: String = ""         //名称
    private var address: String = ""      //地址
    private var person: String = ""        //联系人
    private var phone: String = ""         //联系电话
    private var remark: String = ""          //备注

    var errTip: TipDialog? = null
    var input: InputDialog? = null
    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (errTip == null) {
            errTip = TipDialog(mContext!!)
        }
        errTip?.setTip(errMsg)
        if (isShow) {
            errTip?.show()
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_supplier_add

    override fun initPresenter(): SupplierAddPresenterImpl? = SupplierAddPresenterImpl()

    override fun initViewAndData() {
        tv_name.setOnClickListener(this)
        tv_address.setOnClickListener(this)
        tv_person.setOnClickListener(this)
        tv_phone.setOnClickListener(this)
        tv_remark.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_name -> showInputDialog(tv_name)
            tv_address -> showInputDialog(tv_address)
            tv_person -> showInputDialog(tv_person)
            tv_phone -> showInputDialog(tv_phone)
            tv_remark -> showInputDialog(tv_remark)
            btn_confirm -> addClick()
        }
    }

    private fun showInputDialog(view: TextView) {
        if (input == null) {
            input = InputDialog(mContext!!)
        }
        input?.setBindView(view)
        input?.setInputValue(view.text.toString().trim())
        input?.show()
    }

    /**新增按钮点击**/
    override fun addClick() {
        if (check()) {
            mPresenter?.addSupplier(address, name, phone, person, remark)
        }
    }

    //清空输入框
    override fun clearEdit() {
        tv_name.setText("")
        tv_address.setText("")
        tv_person.setText("")
        tv_phone.setText("")
        tv_remark.setText("")
    }

    //检查输入框
    fun check(): Boolean {
        name = tv_name.text.toString().trim()
        address = tv_address.text.toString().trim()
        person = tv_person.text.toString().trim()
        phone = tv_phone.text.toString().trim()
        remark = tv_remark.text.toString().trim()
        if (StringUtil.isBlank(name)) {
            loadErr(mContext!!.resources.getString(R.string.hint_input_supplier_name))
            return false
        }
        if (StringUtil.isBlank(person)) {
            loadErr(mContext!!.getString(R.string.hint_input_contact_person))
            return false
        }
        return true
    }

    override fun beginLoad() {

    }

}