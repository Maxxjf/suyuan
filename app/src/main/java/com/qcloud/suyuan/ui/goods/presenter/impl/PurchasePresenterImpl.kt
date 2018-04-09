package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.beans.PurchaseProductBean
import com.qcloud.suyuan.model.IStorageModel
import com.qcloud.suyuan.model.impl.StorageModelImpl
import com.qcloud.suyuan.ui.goods.presenter.IPurchasePresenter
import com.qcloud.suyuan.ui.goods.view.IPurchaseView

/**
 * Description: 进货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:32.
 */
class PurchasePresenterImpl: BasePresenter<IPurchaseView>(), IPurchasePresenter {

    private val mModel: IStorageModel = StorageModelImpl()

    /**
     * 获取产品
     *
     * @param keyword 产品批次码
     * */
    override fun loadProduct(keyword: String) {
        mModel.searchList(keyword, object : DataCallback<ReturnDataBean<PurchaseProductBean>> {
            override fun onSuccess(t: ReturnDataBean<PurchaseProductBean>?, message: String?) {
                if (t != null) {
                    mView?.replaceList(t.list)
                } else {
                    mView?.showEmptyView("请扫产品条形码或输入产品条形码或名称进行查找产品")
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
                mView?.showEmptyView("请扫产品条形码或输入产品条形码或名称进行查找产品")
            }
        })
    }
}