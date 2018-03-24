package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import com.qcloud.qclib.utils.ScreenUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_product_details.*

/**
 * Description: 产品详情
 * Author: gaobaiqiang
 * 2018/3/24 下午10:51.
 */
class ProductDetailsDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {

    override val viewId: Int
        get() = R.layout.dialog_product_details

    init {
        val lp = window!!.attributes
        lp.width = ScreenUtil.getScreenWidth(mContext) * 7 / 16 //设置宽度
        lp.height = ScreenUtil.getScreenHeight(mContext) * 3 / 4 //设置宽度
        lp.gravity = Gravity.CENTER
        window!!.attributes = lp
        initView()
    }

    private fun initView() {
        btn_close.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> dismiss()
        }
    }
}