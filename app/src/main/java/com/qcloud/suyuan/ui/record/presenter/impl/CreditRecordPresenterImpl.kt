package com.qcloud.suyuan.ui.record.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.*
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
    var iMoneyModel = MoneyModelImpl()
    override fun getCreditList(keyword: String, pageNo: Int, pageSize: Int) {
        iMoneyModel.getCreditList(keyword, pageNo, pageSize, object : DataCallback<CreditListBean> {
            override fun onSuccess(t: CreditListBean?, message: String?) {
                if (t != null) {
                    mView?.replaceCreditList(t.list, t.isNext(AppConstants.PAGE_SIZE))
                    mView?.showCreditMoney(t.allSumRepaymentStr)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }
        })
    }

    override fun getCreditInfo(id: String, pageNo: Int, pageSize: Int) {
        iMoneyModel.getCreditInfo(id, pageNo, pageSize, object : DataCallback<ReturnDataBean<CreditInfoBean>> {
            override fun onSuccess(t: ReturnDataBean<CreditInfoBean>?, message: String?) {
                if (t != null) {
                    mView?.replaceCreditInfo(t.list, t.isNext(AppConstants.PAGE_SIZE))
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }

        })
    }

    /**还款*/
    override fun repayment(id: String, money: Double) {
        iMoneyModel.repayment(id, money, object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                if (message != null) {
                    mView?.repaymentSuccess()
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }

        })
    }

    /**查看还款历史*/
    override fun repaymentHistory(purchaserId: String) {
        iMoneyModel.repaymentHistory(purchaserId, object : DataCallback<ReturnDataBean<RepaymentListBean>> {
            override fun onSuccess(t: ReturnDataBean<RepaymentListBean>?, message: String?) {
                if (message != null) {
                    mView?.showRepaymentHistoryDialog(t?.list!!, t.isNext(AppConstants.PAGE_SIZE))
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }

        })
    }

    /**查看个人赊账*/
    override fun creditAllmoney(purchaserId: String) {
        iMoneyModel.creditAllMoney(purchaserId, object : DataCallback<CreditAllMoneyBean> {
            override fun onSuccess(t: CreditAllMoneyBean?, message: String?) {
                if (message != null) {
                    mView?.loadErr(message)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }

        })
    }
    /**全部还清*/
    override fun repaymentAll(money: String,purchaserId: String) {
        iMoneyModel.repaymentAll(money,purchaserId, object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                if (message != null) {
                    mView?.repaymentSuccess()
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }

        })
    }
}