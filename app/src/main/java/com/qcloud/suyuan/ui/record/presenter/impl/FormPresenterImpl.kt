package com.qcloud.suyuan.ui.record.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.SaleFormBean
import com.qcloud.suyuan.model.IFormModel
import com.qcloud.suyuan.model.impl.FormModelImpl
import com.qcloud.suyuan.ui.record.presenter.IFormPresenter
import com.qcloud.suyuan.ui.record.view.IFormView

/**
 * Description: 报表
 * Author: gaobaiqiang
 * 2018/3/20 上午8:58.
 */
class FormPresenterImpl : BasePresenter<IFormView>(), IFormPresenter {
    private var mModle: IFormModel = FormModelImpl()
    override fun getDate(startTime: String, endTime: String) {
        mModle?.getSaleForm(
                startTime,
                endTime,
                object : DataCallback<SaleFormBean> {
                    override fun onSuccess(t: SaleFormBean?, message: String?) {
                        if (t != null) {
                            mView?.showForm(t)
                        }
                    }

                    override fun onError(status: Int, message: String) {
                        mView?.loadErr("${message}")
                    }
                }
        )
    }
}