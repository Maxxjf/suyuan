package com.qcloud.suyuan.ui.main.view

import android.widget.TextView
import com.qcloud.qclib.base.BaseView
import org.jetbrains.annotations.NotNull

/**
 * 类型：ILoginView
 * Author: iceberg
 * Date: 2018/3/15.
 * 登录页
 */
interface ILoginView :BaseView {
    /**
     *登录按钮点击
     */
    fun loginBtnClick()
    /**
     * 登录成功
     */
    fun loginSuccess()
    /**
     * 忘记密码点击
     */
    fun forgetBtnClick()


    /**
     * 显示输入框
     */
    fun showInput(@NotNull view: TextView?)
}