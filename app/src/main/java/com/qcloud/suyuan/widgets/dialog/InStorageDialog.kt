package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.View
import com.qcloud.qclib.utils.ScreenUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.beans.PurchaseProductBean
import com.qcloud.suyuan.utils.BarCodeUtil
import kotlinx.android.synthetic.main.dialog_in_storage_confirm.*

/**
 * Description: 入库弹窗
 * Author: gaobaiqiang
 * 2018/3/24 上午9:41.
 */
class InStorageDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener {
    override val viewId: Int
        get() = R.layout.dialog_in_storage_confirm

    init {
        initView()
    }

    private fun initView() {
        btn_close.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
    }

    fun refreshData(bean: PurchaseProductBean?, inStorageDate: String?, batchNum: String?) {
        if (bean != null) {
            with(bean!!) {
                tv_product_name.text = name
                tv_product_spec.text = specification
                tv_product_manufacture.text = millName
            }
            if (StringUtil.isNotBlank(inStorageDate)) {
                tv_give_change.text = inStorageDate
            }
            if (StringUtil.isNotBlank(batchNum)) {
                createCode(batchNum!!)
            }
        }
    }

    private fun createCode(batchNum: String) {
        img_bar_code.post {
            val barCodeWidth = (ScreenUtil.getScreenWidth(mContext) * 3 / 8)
            val barCodeHeight = img_bar_code.height

            val barCode = BarCodeUtil.createBarCode(batchNum, barCodeWidth, barCodeHeight)
            if (barCode != null) {
                img_bar_code.setImageBitmap(barCode)
                tv_bar_code.text = batchNum
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> dismiss()
            R.id.btn_confirm -> {
                dismiss()
                onBtnClickListener?.onBtnClick(v)
            }
        }
    }
}