package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.StockWarnBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.goods.presenter.IStockWarnPresenter
import com.qcloud.suyuan.ui.goods.view.IStockWarnView

/**
 * Description: 库存警告
 * Author: gaobaiqiang
 * 2018/3/20 下午9:01.
 */
class StockWarnPresenterImpl: BasePresenter<IStockWarnView>(), IStockWarnPresenter {

    var mModel: IGoodsModel? = null

    init {
        mModel = GoodsModelImpl()
    }

    override fun loadData(pageNo: Int) {
        mModel?.getStockWarnList(pageNo, AppConstants.PAGE_SIZE, object : DataCallback<ReturnDataBean<StockWarnBean>> {
            override fun onSuccess(t: ReturnDataBean<StockWarnBean>?, message: String?) {
                if (t != null) {
                    if (pageNo == 1) {
                        mView?.replaceList(t.list, t.isNext(AppConstants.PAGE_SIZE))
                    } else {
                        mView?.addListAtEnd(t.list, t.isNext(AppConstants.PAGE_SIZE))
                    }
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }

        })
    }
}