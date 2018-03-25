package com.qcloud.suyuan.ui.record.presenter

/**
 * Description: 赊账记录
 * Author: gaobaiqiang
 * 2018/3/15 上午12:54.
 */
interface ICreditRecordPresenter {
    fun getCreditList(pageNo: Int, pageSize: Int)
    fun getCreditInfo(id: String, pageNo: Int, pageSize: Int)
}