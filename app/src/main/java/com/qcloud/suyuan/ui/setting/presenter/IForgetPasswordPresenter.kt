package com.qcloud.suyuan.ui.setting.presenter

/**
 * 类型：IForgetPasswordPresenter
 * Author: iceberg
 * Date: 2018/3/19.
 * TODO:
 */
interface IForgetPasswordPresenter {
    fun getCode(mobile: String)
    fun forgetPassword(code: String, mobile: String, password: String)
}