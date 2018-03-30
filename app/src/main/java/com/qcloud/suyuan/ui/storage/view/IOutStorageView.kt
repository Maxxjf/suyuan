package com.qcloud.suyuan.ui.storage.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.OutStorageBean

/**
 * Description: 撤消入库
 * Author: gaobaiqiang
 * 2018/3/15 上午12:45.
 */
interface IOutStorageView :BaseView{
    fun searchProductInfo()
    fun loadProductInfo(bean: OutStorageBean.InfoBean)
}