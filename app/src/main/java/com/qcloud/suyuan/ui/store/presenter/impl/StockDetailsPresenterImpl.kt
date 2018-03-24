package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.StockBean
import com.qcloud.suyuan.ui.store.presenter.IStockDetailsPresenter
import com.qcloud.suyuan.ui.store.view.IStockDetailsView

/**
 * Description: 库存详情
 * Author: gaobaiqiang
 * 2018/3/24 下午2:34.
 */
class StockDetailsPresenterImpl: BasePresenter<IStockDetailsView>(), IStockDetailsPresenter {

    override fun onBtnClick(viewId: Int) {
        when (viewId) {
            R.id.btn_product_details -> mView?.onProductDetailsClick()

            R.id.btn_product_price -> mView?.onProductPriceClick()

            R.id.btn_adjust_warn -> mView?.onAdjustWarnClick()
        }
    }

    override fun loadData() {
        val list: MutableList<StockBean> = ArrayList()
        var bean = StockBean()
        bean.batch = "10"
        bean.number = "288"
        bean.operator = "张大伟"
        bean.price = "￥100.00"
        bean.stock = "49"
        bean.time = "2018/03/25"
        bean.valid = "2017/12/12-2020/12/12"
        list.add(bean)
        list.add(bean)
        list.add(bean)
        list.add(bean)
        list.add(bean)
        list.add(bean)
        list.add(bean)

        mView?.replaceList(list)
    }
}