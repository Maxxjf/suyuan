package com.qcloud.suyuan.ui.store.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.StoreBean

/**
 * Description: 门店信息
 * Author: gaobaiqiang
 * 2018/3/20 上午9:04.
 */
interface IStoreInfoView :BaseView {
    /**
     * 加载信息
     */
    fun getInfoSuccess(bean: StoreBean)

    fun showInfoDialog()
}