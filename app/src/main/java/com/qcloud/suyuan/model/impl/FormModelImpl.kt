package com.qcloud.suyuan.model.impl

import com.lzy.okgo.model.HttpParams
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.OkGoRequest
import com.qcloud.suyuan.beans.MainFormBean
import com.qcloud.suyuan.model.IFormModel
import com.qcloud.suyuan.net.IFormApi

/**
 * Description: 报表有关
 * Author: gaobaiqiang
 * 2018/3/19 下午7:31.
 */
class FormModelImpl: IFormModel {

    /**
     * 获取首页报表数据
     * */
    override fun getMainForm(callback: DataCallback<MainFormBean>) {
        val params: HttpParams = OkGoRequest.getAppParams()

        BaseApi.dispose(IFormApi.getMainForm(params), callback)
    }
}