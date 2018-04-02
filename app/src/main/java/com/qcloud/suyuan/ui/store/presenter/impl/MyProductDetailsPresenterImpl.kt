package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.ProductDetailsBean
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.store.presenter.IMyProductDetailsPresenter
import com.qcloud.suyuan.ui.store.view.IMyProductDetailsView

/**
 * Description: 我的产品详情
 * Author: gaobaiqiang
 * 2018/4/1 下午5:15.
 */
class MyProductDetailsPresenterImpl: BasePresenter<IMyProductDetailsView>(), IMyProductDetailsPresenter {
    private val mModel: IGoodsModel = GoodsModelImpl()

    /**
     * 获取产品详情
     *
     * @param id 产品id
     * */
    override fun loadData(id: String) {
        mModel.details(id, object : DataCallback<ProductDetailsBean> {
            override fun onSuccess(t: ProductDetailsBean?, message: String?) {
                if (t != null) {
                    mView?.refreshData(t)
                } else {
                    mView?.loadErr(message ?: "加载产品详情出错", true)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }
        })
    }
}