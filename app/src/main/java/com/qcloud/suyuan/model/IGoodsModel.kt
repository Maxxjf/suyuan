package com.qcloud.suyuan.model

import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.CodeBean
import com.qcloud.suyuan.beans.StockWarnBean
import com.qcloud.suyuan.beans.ValidWarnBean

/**
 * Description: 货物有关
 * Author: gaobaiqiang
 * 2018/3/21 上午9:04.
 */
interface IGoodsModel {
    /**获取库存告警列表*/
    fun getStockWarnList(pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<StockWarnBean>>)

    /**获取有期告警列表*/
    fun getValidWarnList(pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<ValidWarnBean>>)

    /**获取退货记录*/
    fun getReturnRecord(startTime:String,endTime:String,pageNo: Int, pageSize: Int, callback: DataCallback<ReturnDataBean<CodeBean>>)


}