package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.RxBusEvent
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.IDVerifyResultBean
import com.qcloud.suyuan.beans.SellersBean
import com.qcloud.suyuan.ui.goods.presenter.ISellersPresenter
import com.qcloud.suyuan.ui.goods.view.ISellersView
import io.reactivex.functions.Consumer

/**
 * Description:卖货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:22.
 */
class SellersPresenterImpl: BasePresenter<ISellersView>(), ISellersPresenter {

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

    override fun loadData() {
        val list: MutableList<SellersBean> = ArrayList()
        var bean = SellersBean()
        bean.number = 10
        bean.barCode = "868689954562"
        bean.batchCode = "24242407686"
        bean.name = "安环曹素环250克/升+金何玲10%(水剂+5%安环曹素昂250克)"
        bean.outDate = ""
        bean.price = 218.0
        bean.spec = "200ml/瓶"
        bean.stock = 856
        list.add(bean)

        bean = SellersBean()
        bean.number = 10
        bean.barCode = "868689954562"
        bean.batchCode = "24242407686"
        bean.name = "安环曹素环250克/升+金何玲10%(水剂+5%安环曹素昂250克)"
        bean.outDate = ""
        bean.price = 218.0
        bean.spec = "200ml/瓶"
        bean.stock = 856
        list.add(bean)

        bean = SellersBean()
        bean.number = 10
        bean.barCode = "868689954562"
        bean.batchCode = "24242407686"
        bean.name = "安环曹素环250克/升+金何玲10%(水剂+5%安环曹素昂250克)"
        bean.outDate = ""
        bean.price = 218.0
        bean.spec = "200ml/瓶"
        bean.stock = 856
        list.add(bean)

        bean = SellersBean()
        bean.number = 10
        bean.barCode = "868689954562"
        bean.batchCode = "24242407686"
        bean.name = "安环曹素环250克/升+金何玲10%(水剂+5%安环曹素昂250克)"
        bean.outDate = ""
        bean.price = 218.0
        bean.spec = "200ml/瓶"
        bean.stock = 856
        list.add(bean)

        bean = SellersBean()
        bean.number = 10
        bean.barCode = "868689954562"
        bean.batchCode = "24242407686"
        bean.name = "安环曹素环250克/升+金何玲10%(水剂+5%安环曹素昂250克)"
        bean.outDate = ""
        bean.price = 218.0
        bean.spec = "200ml/瓶"
        bean.stock = 856
        list.add(bean)

        bean = SellersBean()
        bean.number = 10
        bean.barCode = "868689954562"
        bean.batchCode = "24242407686"
        bean.name = "安环曹素环250克/升+金何玲10%(水剂+5%安环曹素昂250克)"
        bean.outDate = ""
        bean.price = 218.0
        bean.spec = "200ml/瓶"
        bean.stock = 856
        list.add(bean)

        mView?.replaceList(list)
    }
}