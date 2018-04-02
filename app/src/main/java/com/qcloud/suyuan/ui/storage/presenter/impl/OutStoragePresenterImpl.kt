package com.qcloud.suyuan.ui.storage.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.OutStorageBean
import com.qcloud.suyuan.model.IStorageModel
import com.qcloud.suyuan.model.impl.StorageModelImpl
import com.qcloud.suyuan.ui.storage.presenter.IOutStoragePresenter
import com.qcloud.suyuan.ui.storage.view.IOutStorageView

/**
 * Description: 撤消入库
 * Author: icebreg
 * 2018/3/30
 */
class OutStoragePresenterImpl : BasePresenter<IOutStorageView>(), IOutStoragePresenter {
    var mModel: IStorageModel = StorageModelImpl()

    //    搜索产品消息
    override fun search(keyword: String) {
        mModel.outStorageSearch(keyword, object : DataCallback<OutStorageBean> {
            override fun onSuccess(t: OutStorageBean?, message: String?) {
                mView?.loadProductInfo(t!!.info!!)

            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
//
            }

        })
    }

    //    提交出库
    override fun outStorage(id: String, number: Int) {
        mModel.outStorageInValidWarn(id, number, object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                if (message != null) {
                    mView?.loadErr(message)
                    mView?.searchProductInfo()
                }
            }

            override fun onError(status: Int, message: String) {
                if (message != null) {
                    mView?.loadErr(message)
                }
            }

        })
    }

}