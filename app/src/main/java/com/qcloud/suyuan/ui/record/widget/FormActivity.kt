package com.qcloud.suyuan.ui.record.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.View
import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.SaleFormBean
import com.qcloud.suyuan.ui.record.presenter.impl.FormPresenterImpl
import com.qcloud.suyuan.ui.record.view.IFormView
import com.qcloud.suyuan.widgets.dialog.DatePickerDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
import kotlinx.android.synthetic.main.activity_form.*
import java.util.*

/**
 * Description: 报表
 * Author: gaobaiqiang
 * 2018/3/20 上午8:59.
 */
class FormActivity: BaseActivity<IFormView, FormPresenterImpl>(), IFormView, View.OnClickListener {


    private var startTime:String=""
    private var endTime:String=""
    private var startDatePicker: DatePickerDialog? = null
    private var endDatePicker:DatePickerDialog? = null
    private var errtip: TipDialog? = null

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (errtip == null) {
            errtip = TipDialog(this)
        }
        errtip?.setTip("${errMsg}")
        if (isShow) {
            errtip?.show()
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_form

    override fun initPresenter(): FormPresenterImpl? {
        return FormPresenterImpl()
    }

    override fun showForm(bean:SaleFormBean){
        tv_all_income.setText("${bean.orderTotalStr}")
        tv_cash_income.setText("${bean.cashStr}")
        tv_alipay_income.setText("${bean.alipayStr}")
        tv_wechat_income.setText("${bean.wechatStr}")
        tv_credit_money.setText("${bean.oncreditStr}")
        tv_order_number.setText("${bean.orderTotalStr}")
        tv_tag_return_money.setText("${bean.returnMoneyStr}")
    }

    override fun initViewAndData() {
        initView()
        loadData()
    }

    private fun initView() {
        tv_date_from.setOnClickListener(this)
        tv_date_to.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       when(v){
           tv_date_from ->showStartPicker()
           tv_date_to ->showEndPicker()
       }
    }

    private fun showStartPicker() {
        if (startDatePicker == null){
            startDatePicker= DatePickerDialog(this)
            startDatePicker?.onDateSelectListener = object :DatePickerDialog.OnDateSelectListener {

                override fun onSelect(time: Calendar) {
                    tv_date_from.text=DateUtil.formatDate(time.time, "yyyy-MM-dd")
                    loadData()
                }
            }
        }
        startDatePicker?.show()
    }

    private fun showEndPicker(){
        if (endDatePicker == null){
            endDatePicker= DatePickerDialog(this)
            endDatePicker?.onDateSelectListener = object :DatePickerDialog.OnDateSelectListener {
                override fun onSelect(time: Calendar) {
                    tv_date_to.text=DateUtil.formatDate(time.time, "yyyy-MM-dd")
                    loadData()
                }
            }
        }
        endDatePicker?.show()
    }

    private fun loadData() {
        startTime=tv_date_from.text.toString().trim()
        endTime=tv_date_to.text.toString().trim()
        if(DateUtil.compareTime(startTime,endTime, DateStyleEnum.YYYY_MM_DD.value)==1){
            loadErr(resources.getString(R.string.toast_start_bigger_end))
            return
        }
        mPresenter?.getDate(startTime,endTime)
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, FormActivity::class.java))
        }
    }
}