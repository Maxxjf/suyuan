package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.beans.ProductClassifyBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.enums.PlatformEnum
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.store.presenter.IMyProductPresenter
import com.qcloud.suyuan.ui.store.view.IMyProductView

/**
 * 类说明：我的产品
 * Author: Kuzan
 * Date: 2018/3/30 11:03.
 */
class MyProductPresenterImpl: BasePresenter<IMyProductView>(), IMyProductPresenter {

    val mModel: IGoodsModel = GoodsModelImpl()

    /**
     * 获取产品分类
     * */
    override fun loadClassify() {
        mModel.classifyList(object : DataCallback<ReturnDataBean<ProductClassifyBean>> {
            override fun onSuccess(t: ReturnDataBean<ProductClassifyBean>?, message: String?) {
                if (t?.list != null) {
                    mView?.replaceClassifyList(t.list)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, false)
            }
        })
    }

    /**
     * 获取产品数据
     * */
    override fun loadData(pageNo: Int, classifyId: String?, keyword: String?) {
        mModel.list(pageNo, AppConstants.PAGE_SIZE, classifyId, PlatformEnum.isPrivate.key, keyword, object : DataCallback<ReturnDataBean<ProductBean>> {
            override fun onSuccess(t: ReturnDataBean<ProductBean>?, message: String?) {
                if (t?.list != null) {
                    if (t.list != null) {
                        if (pageNo == 1) {
                            mView?.replaceList(t.list, t.isNext(AppConstants.PAGE_SIZE))
                        } else {
                            mView?.addListAtEnd(t.list, t.isNext(AppConstants.PAGE_SIZE))
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