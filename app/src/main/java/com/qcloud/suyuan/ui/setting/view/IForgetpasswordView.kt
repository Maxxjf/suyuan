package com.qcloud.suyuan.ui.setting.view

import com.qcloud.qclib.base.BaseView

/**
 * 类型：IForgetpasswordView
 * Author: iceberg
 * Date: 2018/3/19.
 * 忘记密码页
 */
interface IForgetpasswordView:BaseView {
    /**
     * 修改密码成功
     */
    fun forgetPasswordSuccess()

    fun getCodeSuccess()
}