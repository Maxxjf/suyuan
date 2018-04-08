package com.qcloud.suyuan.ui.main.view

import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.MainFormBean
import com.qcloud.suyuan.beans.VersionBean

/**
 * 类说明：主页
 * Author: Kuzan
 * Date: 2018/3/12 15:27.
 */
interface IMainView: BaseView {
    fun showMainForm(bean: MainFormBean)

    fun checkVersion(bean: VersionBean)
}