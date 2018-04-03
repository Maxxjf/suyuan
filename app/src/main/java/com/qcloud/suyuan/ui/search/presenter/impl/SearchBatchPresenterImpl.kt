package com.qcloud.suyuan.ui.search.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.model.IStorageModel
import com.qcloud.suyuan.model.impl.StorageModelImpl
import com.qcloud.suyuan.ui.search.presenter.ISearchBatchPresenter
import com.qcloud.suyuan.ui.search.view.ISearchBatchView

/**
 * Description: 搜索批次条形码
 * Author: gaobaiqiang
 * 2018/4/1 下午11:03.
 */
class SearchBatchPresenterImpl: BasePresenter<ISearchBatchView>(), ISearchBatchPresenter {

    private val mModel: IStorageModel = StorageModelImpl()

    /**
     * 获取产品
     *
     * @param keyword 产品批次码
     * */
    override fun loadProduct(keyword: String) {
        mModel.searchList(keyword, object : DataCallback<ReturnDataBean<ProductBean>> {
            override fun onSuccess(t: ReturnDataBean<ProductBean>?, message: String?) {
                if (t?.list != null && t.list!!.isNotEmpty()) {
                    mView?.replaceData(t.list!![0])
                } else {
                    mView?.loadErr("暂无数据")
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
                mView?.showEmptyView(message)
            }
        })
    }
}