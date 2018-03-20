package com.qcloud.suyuan.widgets.dialog

import android.app.Dialog
import android.content.Context
import android.support.annotation.NonNull
import android.support.annotation.StyleRes
import android.view.Gravity
import android.view.View
import com.qcloud.qclib.utils.ScreenUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.ui.record.widget.FormActivity
import com.qcloud.suyuan.ui.record.widget.ReturnRecordActivity
import com.qcloud.suyuan.ui.record.widget.SuyuanRecordActivity
import com.qcloud.suyuan.ui.setting.widget.DeviceStateActivity
import com.qcloud.suyuan.ui.store.widget.MySupplierActivity
import com.qcloud.suyuan.ui.store.widget.StoreInfoActivity
import kotlinx.android.synthetic.main.card_main_device_state.*
import kotlinx.android.synthetic.main.card_main_form.*
import kotlinx.android.synthetic.main.card_main_my_supplier.*
import kotlinx.android.synthetic.main.card_main_return_record.*
import kotlinx.android.synthetic.main.card_main_store_info.*
import kotlinx.android.synthetic.main.card_main_suyuan_record.*
import kotlinx.android.synthetic.main.dialog_more_operation.*

/**
 * Description: 更多操作弹窗
 * Author: gaobaiqiang
 * 2018/3/19 下午10:56.
 */
class MoreOperationDialog@JvmOverloads constructor(
        @NonNull private val mContext: Context,
        @StyleRes themeResId: Int = R.style.InputDialog) : Dialog(mContext, themeResId), View.OnClickListener {

    init {
        setContentView(R.layout.dialog_more_operation)
        initDialog()
        initView()
    }

    private fun initDialog() {
        val lp = window!!.attributes
        lp.width = ScreenUtil.getScreenWidth(mContext) * 9 / 10 //设置宽度
        lp.gravity = Gravity.CENTER
        window!!.attributes = lp
        setCancelable(true)
    }

    private fun initView() {
        btn_close.setOnClickListener(this)
        layout_return_record.setOnClickListener(this)
        layout_suyuan_record.setOnClickListener(this)
        layout_form.setOnClickListener(this)
        layout_my_suppler.setOnClickListener(this)
        layout_store_info.setOnClickListener(this)
        layout_device_state.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> dismiss()
            R.id.layout_return_record -> {
                ReturnRecordActivity.openActivity(mContext)
                dismiss()
            }
            R.id.layout_suyuan_record -> {
                SuyuanRecordActivity.openActivity(mContext)
                dismiss()
            }
            R.id.layout_form -> {
                FormActivity.openActivity(mContext)
                dismiss()
            }
            R.id.layout_my_suppler -> {
                MySupplierActivity.openActivity(mContext)
                dismiss()
            }
            R.id.layout_store_info -> {
                StoreInfoActivity.openActivity(mContext)
                dismiss()
            }
            R.id.layout_device_state -> {
                DeviceStateActivity.openActivity(mContext)
                dismiss()
            }
        }
    }
}