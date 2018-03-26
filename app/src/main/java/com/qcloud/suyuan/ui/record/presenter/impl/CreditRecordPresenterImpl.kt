package com.qcloud.suyuan.ui.record.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.CreditInfoBean
import com.qcloud.suyuan.beans.CreditListBean
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.model.impl.MoneyModelImpl
import com.qcloud.suyuan.ui.record.presenter.ICreditRecordPresenter
import com.qcloud.suyuan.ui.record.view.ICreditRecordView

/**
 * Description: 赊账记录
 * Author: gaobaiqiang
 * 2018/3/15 上午12:55.
 */
class CreditRecordPresenterImpl : BasePresenter<ICreditRecordView>(), ICreditRecordPresenter {
    var iMoneyModel=MoneyModelImpl()
    override fun getCreditList(keyword:String,pageNo: Int, pageSize: Int) {
        iMoneyModel.getCreditList(keyword,pageNo,pageSize,object : DataCallback<CreditListBean>{
            override fun onSuccess(t: CreditListBean?, message: String?) {
                if (t != null) {
                    mView?.replaceCreditList(t.list,t.isNext(AppConstants.PAGE_SIZE))
                }
            }

            override fun onError(status: Int, message: String) {
               mView?.loadErr(message,true)
            }
        })
    }

    override fun getCreditInfo(id: String, pageNo: Int, pageSize: Int) {
        iMoneyModel.getCreditInfo(id,pageNo,pageSize,object : DataCallback<ReturnDataBean<CreditInfoBean>>{
            override fun onSuccess(t: ReturnDataBean<CreditInfoBean>?, message: String?) {
                if (t != null) {
                    mView?.replaceCreditInfo(t.list,t.isNext(AppConstants.PAGE_SIZE))
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message,true)
            }

        })
    }
    /**还款*/
    override fun repayment(id: String, money: Double) {
        iMoneyModel.repayment(id,money,object : DataCallback<EmptyReturnBean>{
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                if (message != null) {
                    mView?.loadErr(message,true)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message,true)
            }

        })
    }
}