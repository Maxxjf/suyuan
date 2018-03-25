package com.qcloud.suyuan.model

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.CreditInfoBean
import com.qcloud.suyuan.beans.CreditListBean


/**
 * 类说明：金钱有关的
 * Author: iceberg
 * Date: 2018-3-25.
 */
interface IMoneyModel {
    /**
     * 得到赊账列表
     */
    fun getCreditList(pageNo: Int, pageSize: Int, callback: DataCallback<CreditListBean>)

    fun getCreditInfo(creditId: String, pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<CreditInfoBean>>)
}