package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.StockWarnBean
import com.qcloud.suyuan.beans.ValidWarnBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.goods.presenter.IValidWarnPresenter
import com.qcloud.suyuan.ui.goods.view.IValidWarnView

/**
 * Description: 有效期告警
 * Author: gaobaiqiang
 * 2018/3/20 下午9:05.
 */
class ValidWarnPresenterImpl: BasePresenter<IValidWarnView>(), IValidWarnPresenter {

    var mModel: IGoodsModel? = null

    init {
        mModel = GoodsModelImpl()
    }

    override fun loadData(pageNo: Int) {
        mModel?.getValidWarnList(pageNo, AppConstants.PAGE_SIZE, object : DataCallback<ReturnDataBean<ValidWarnBean>> {
            override fun onSuccess(t: ReturnDataBean<ValidWarnBean>?, message: String?) {
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