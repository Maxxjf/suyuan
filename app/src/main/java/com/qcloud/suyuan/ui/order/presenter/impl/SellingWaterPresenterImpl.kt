package com.qcloud.suyuan.ui.order.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.SaleInfoBean
import com.qcloud.suyuan.beans.SaleListBean
import com.qcloud.suyuan.model.IMoneyModel
import com.qcloud.suyuan.model.impl.MoneyModelImpl
import com.qcloud.suyuan.ui.order.presenter.ISellingWaterPresenter
import com.qcloud.suyuan.ui.order.view.ISellingWaterView

/**
 * Description: 销售流水
 * Author: gaobaiqiang
 * 2018/3/15 上午12:41.
 */
class SellingWaterPresenterImpl : BasePresenter<ISellingWaterView>(), ISellingWaterPresenter {
    private var mModel: IMoneyModel = MoneyModelImpl()
    /**得到销售列表*/
    override fun getSaleList(dayTime: String, keyword: String) {
        mModel.getSaleList(dayTime, keyword, object : DataCallback<ReturnDataBean<SaleListBean>> {
            override fun onSuccess(bean: ReturnDataBean<SaleListBean>?, message: String?) {
                if (bean != null) {
                    mView?.replaceSaleList(bean.list, false)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }

        })
    }

    /**得到销售详情*/
    override fun getSaleInfo(id: String) {
        mModel.getSaleInfo(id, object : DataCallback<SaleInfoBean> {
            override fun onSuccess(bean: SaleInfoBean?, message: String?) {
                if (bean != null) {
                    mView?.getSaleInfoSuccess(bean)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }

        })
    }

}