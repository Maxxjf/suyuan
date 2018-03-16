package com.qcloud.suyuan.ui.main.view

import com.qcloud.qclib.base.BaseView

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

}