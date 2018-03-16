package com.qcloud.suyuan.ui.main.presenter

import com.qcloud.qclib.base.BtnClickPresenter

/**
 * 类型：ILoginPresenter
 * Author: iceberg
 * Date: 2018/3/15.
 * 登录页
 */
interface ILoginPresenter:BtnClickPresenter {
    fun login(account:String,password:String)
}