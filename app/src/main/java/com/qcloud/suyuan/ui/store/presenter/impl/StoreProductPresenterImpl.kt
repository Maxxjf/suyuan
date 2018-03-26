package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.ProductReturnBean
import com.qcloud.suyuan.beans.StoreProductBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.store.presenter.IStoreProductPresenter
import com.qcloud.suyuan.ui.store.view.IStoreProductView

/**
 * Description: 门店产品
 * Author: gaobaiqiang
 * 2018/3/15 上午12:50.
 */
class StoreProductPresenterImpl: BasePresenter<IStoreProductView>(), IStoreProductPresenter {

    val mModel = GoodsModelImpl()

    override fun loadData(pageNo: Int, classifyId: String?, keyword: String?) {
        mModel.list(pageNo, AppConstants.PAGE_SIZE, classifyId, 0, keyword, object : DataCallback<ProductReturnBean> {
            override fun onSuccess(t: ProductReturnBean?, message: String?) {
                if (t != null) {
                    mView?.replaceClissifyList(t.classifyList)
                    if (t.list != null) {
                        if (pageNo == 1) {
                            mView?.replaceList(t.list, t.isNext())
                        } else {
                            mView?.addListAtEnd(t.list, t.isNext())
                        }
                    } else {
                        if (pageNo == 1) {
                            mView?.showEmptyView("暂无数据")
                        } else {
                            mView?.loadErr("暂无更多数据", true)
                        }
                    }
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }
        })
    }
}