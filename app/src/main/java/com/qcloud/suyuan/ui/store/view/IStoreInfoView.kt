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
    /**
     * 展示修改信息对话框
     */
    fun showInfoDialog()
    /**
     * 展示修改密码对话框
     */
    fun showPasswordDialog()

    fun editPwSuccess(string: String)
}