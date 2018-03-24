package com.qcloud.suyuan.base

import android.app.Dialog
import android.content.Context
import android.support.annotation.NonNull
import android.support.annotation.StyleRes
import android.view.Gravity
import android.view.View
import com.qcloud.qclib.utils.ScreenUtil
import com.qcloud.suyuan.R

/**
 * Description: Dialog基类
 * Author: gaobaiqiang
 * 2018/3/23 下午5:20.
 */
abstract class BaseDialog @JvmOverloads constructor(
        @NonNull protected val mContext: Context,
        @StyleRes themeResId: Int = R.style.InputDialog) : Dialog(mContext, themeResId) {

    var onBtnClickListener: OnBtnClickListener? = null

    init {
        initDialog()
    }

    private fun initDialog() {
        setContentView(viewId)
        val lp = window!!.attributes
        lp.width = ScreenUtil.getScreenWidth(mContext) * 7 / 16 //设置宽度
        lp.gravity = Gravity.CENTER
        window!!.attributes = lp
        setCancelable(true)
    }

    /** 获取viewId */
    abstract val viewId: Int

    interface OnBtnClickListener {
        fun onBtnClick(view: View)
    }
}