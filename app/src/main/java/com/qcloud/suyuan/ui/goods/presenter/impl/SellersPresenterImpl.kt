package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.RxBusEvent
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.IDVerifyResultBean
import com.qcloud.suyuan.beans.SellersBean
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.goods.presenter.ISellersPresenter
import com.qcloud.suyuan.ui.goods.view.ISellersView
import io.reactivex.functions.Consumer

/**
 * Description:卖货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:22.
 */
class SellersPresenterImpl: BasePresenter<ISellersView>(), ISellersPresenter {

    private val mModel: IGoodsModel = GoodsModelImpl()

    init {
        initEventBus()
    }

    private fun initEventBus() {
        if (mEventBus != null) {
            mEventBus!!.registerSubscriber(this, mEventBus!!.obtainSubscriber(RxBusEvent::class.java, Consumer {
                when (it.type) {
                    R.id.id_verify_result -> {
                        val bean: IDVerifyResultBean? = it.obj as IDVerifyResultBean
                        if (bean != null) {
                            mView?.disposeRfidReceivedData(bean)
                        }
                    }
                }
            }))
        }
    }

    override fun onBtnClick(viewId: Int) {
        when (viewId) {
            R.id.btn_settlement -> mView?.onSettlementClick()
            R.id.btn_input_purchase_info -> mView?.onInputPurchaserClick()
        }
    }

    /**
     * 扫码搜索
     * */
    override fun loadData(keyword: String) {
        mModel.saleSearchProduct(keyword, object : DataCallback<SellersBean> {
            override fun onSuccess(t: SellersBean?, message: String?) {
                if (t?.cart != null) {
                    mView?.addBeanAtEnd(t.cart!!)
                } else {
                    mView?.searchFailure()
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.searchFailure()
            }
        })
    }
}