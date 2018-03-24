package com.qcloud.suyuan.widgets.dialog

import android.content.Context
import android.view.View
import com.qcloud.qclib.utils.ScreenUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
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

        createCode()
    }

    private fun createCode() {
        img_bar_code.post {
            val barCodeWidth = (ScreenUtil.getScreenWidth(mContext) * 3 / 8)
            val barCodeHeight = img_bar_code.height

            val barCode = BarCodeUtil.createBarCode("189983004300", barCodeWidth, barCodeHeight)
            if (barCode != null) {
                img_bar_code.setImageBitmap(barCode)
                tv_bar_code.text = "189983004300"
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