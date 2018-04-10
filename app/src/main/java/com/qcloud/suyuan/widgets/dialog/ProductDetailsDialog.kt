package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.qclib.utils.ScreenUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.ProductDetailsBean
import kotlinx.android.synthetic.main.card_dialog_product_info.*
import kotlinx.android.synthetic.main.card_dialog_product_introduce.*
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
        lp.width = ScreenUtil.getScreenWidth(mContext) * 10 / 16 //设置宽度
        lp.height = ScreenUtil.getScreenHeight(mContext) * 3 / 4 //设置宽度
        lp.gravity = Gravity.CENTER
        window!!.attributes = lp
        initView()
    }

    private fun initView() {
        btn_close.setOnClickListener(this)
    }

    fun refreshData(bean: ProductDetailsBean) {
        val millBean = bean.mill
        val infoBean = bean.info
        if (millBean != null) {
            with(millBean) {
                tv_factory.text = name
                tv_factory_address.text = address
                tv_factory_mobile.text = tel
                tv_factory_web.text = url
            }
        }
        if (infoBean != null) {
            with(infoBean) {
                tv_pesticides_registration.text = registerCard
                tv_production_license.text = licenseCard
                tv_product_standard.text = standardCard
                tv_product_introduce.text = ApiReplaceUtil.fromHtml(details)
            }
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> dismiss()
        }
    }
}