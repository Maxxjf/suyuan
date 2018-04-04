package com.qcloud.suyuan.ui.search.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.BatchDetailsBean
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.IStorageModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.model.impl.StorageModelImpl
import com.qcloud.suyuan.ui.search.presenter.ISearchBatchPresenter
import com.qcloud.suyuan.ui.search.view.ISearchBatchView

/**
 * Description: 搜索批次条形码
 * Author: gaobaiqiang
 * 2018/4/1 下午11:03.
 */
class SearchBatchPresenterImpl: BasePresenter<ISearchBatchView>(), ISearchBatchPresenter {

    private val mModel: IGoodsModel = GoodsModelImpl()

    /**
     * 获取产品
     *
     * @param keyword 产品批次码
     * */
    override fun loadData(keyword: String) {
        mModel.batchSearch(keyword, object : DataCallback<BatchDetailsBean> {
            override fun onSuccess(t: BatchDetailsBean?, message: String?) {
                if (t != null) {
                    mView?.replaceData(t)
                } else {
                    mView?.showEmptyView("抱歉！没有搜索到符合条件的产品，请重新扫码获取~")
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
                mView?.showEmptyView(message)
            }
        })
    }
}