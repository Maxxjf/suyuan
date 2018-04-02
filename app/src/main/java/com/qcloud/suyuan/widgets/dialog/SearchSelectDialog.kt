package com.qcloud.suyuan.widgets.dialog

import android.app.Dialog
import android.content.Context
import android.support.annotation.NonNull
import android.support.annotation.StyleRes
import android.view.Gravity
import android.view.View
import com.qcloud.qclib.utils.ScreenUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.enums.SearchType
import com.qcloud.suyuan.ui.search.widget.SearchBatchActivity
import com.qcloud.suyuan.ui.search.widget.SearchProductActivity
import com.qcloud.suyuan.ui.search.widget.SearchSuyuanActivity
import kotlinx.android.synthetic.main.dialog_search_select.*

/**
 * Description: 搜索选择
 * Author: gaobaiqiang
 * 2018/3/19 下午8:44.
 */
class SearchSelectDialog @JvmOverloads constructor(
    @NonNull private val mContext: Context,
    @StyleRes themeResId: Int = R.style.InputDialog) : Dialog(mContext, themeResId), View.OnClickListener {

    init {
        setContentView(R.layout.dialog_search_select)
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
        btn_search_suyuan_code.setOnClickListener(this)
        btn_search_bitch_bar_code.setOnClickListener(this)
        btn_search_product_bar_code.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_close -> dismiss()
            R.id.btn_search_suyuan_code -> {
                dismiss()
                SearchSuyuanActivity.openActivity(mContext)
            }
            R.id.btn_search_bitch_bar_code -> {
                dismiss()
                SearchBatchActivity.openActivity(mContext)
            }
            R.id.btn_search_product_bar_code -> {
                dismiss()
                SearchProductActivity.openActivity(mContext)
            }
        }
    }
}