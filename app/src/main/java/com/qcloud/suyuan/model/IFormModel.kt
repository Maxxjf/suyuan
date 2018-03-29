package com.qcloud.suyuan.model

import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.MainFormBean
import com.qcloud.suyuan.beans.SaleFormBean

/**
 * Description: 报表有关
 * Author: gaobaiqiang
 * 2018/3/19 下午7:30.
 */
interface IFormModel {
    /** 首页报表 */
    fun getMainForm(callback: DataCallback<MainFormBean>)

    fun getSaleForm(startTime: String, endTime: String, callback: DataCallback<SaleFormBean>)
}