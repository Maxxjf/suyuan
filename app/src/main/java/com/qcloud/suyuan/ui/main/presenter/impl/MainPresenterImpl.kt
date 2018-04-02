package com.qcloud.suyuan.ui.main.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.MainFormBean
import com.qcloud.suyuan.model.IFormModel
import com.qcloud.suyuan.model.impl.FormModelImpl
import com.qcloud.suyuan.ui.main.presenter.IMainPresenter
import com.qcloud.suyuan.ui.main.view.IMainView

/**
 * 类说明：主页
 * Author: Kuzan
 * Date: 2018/3/12 15:28.
 */
class MainPresenterImpl: BasePresenter<IMainView>(), IMainPresenter {
    var mModel: IFormModel? = null

    init {
        mModel = FormModelImpl()
    }

    /**
     * 获取今日报表
     * */
    override fun getMainForm() {
        mModel?.getMainForm(object : DataCallback<MainFormBean> {
            override fun onSuccess(t: MainFormBean?, message: String?) {
                if (t != null) {
                    mView?.showMainForm(t)
                } else {
                    mView?.loadErr(message ?: "获取数据出错", true)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }

        })
    }
}