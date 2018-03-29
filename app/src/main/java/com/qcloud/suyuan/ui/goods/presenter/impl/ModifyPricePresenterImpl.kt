package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.ProductReturnBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.goods.presenter.IModifyPricePresenter
import com.qcloud.suyuan.ui.goods.view.IModifyPriceView

/**
 * Description: 修改售价
 * Author: gaobaiqiang
 * 2018/3/15 上午12:36.
 */
class ModifyPricePresenterImpl: BasePresenter<IModifyPriceView>(), IModifyPricePresenter {

    val mModel = GoodsModelImpl()

    override fun loadData(pageNo: Int, classifyId: String?, keyword: String?) {
        mModel.list(pageNo, AppConstants.PAGE_SIZE, classifyId, 0, keyword, object : DataCallback<ProductReturnBean> {
            override fun onSuccess(t: ProductReturnBean?, message: String?) {
                if (t != null) {
                    if (t.list != null) {
                        if (pageNo == 1) {
                            mView?.replaceList(t.list, t.isNext())
                        } else {
                            mView?.addListAtEnd(t.list, t.isNext())
                        }
                    } else {
                        if (pageNo == 1) {
                            mView?.showEmptyView()
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

    override fun modifyWarnLine(id: String, cordon: Int) {
        mModel.editWarnLine(id, cordon, object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                mView?.modifyStockWarnSuccess()
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }
        })
    }

    override fun modifyPrice(id: String, price: Double) {
        mModel.editPrice(id, price, object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                mView?.modifyPriceSuccess()
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }
        })
    }
}