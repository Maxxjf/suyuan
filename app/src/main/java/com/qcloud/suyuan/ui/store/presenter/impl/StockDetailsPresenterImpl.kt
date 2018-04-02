package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.ProductDetailsBean
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.store.presenter.IStockDetailsPresenter
import com.qcloud.suyuan.ui.store.view.IStockDetailsView

/**
 * Description: 库存详情
 * Author: gaobaiqiang
 * 2018/3/24 下午2:34.
 */
class StockDetailsPresenterImpl: BasePresenter<IStockDetailsView>(), IStockDetailsPresenter {

    private val mModel: IGoodsModel = GoodsModelImpl()

    override fun onBtnClick(viewId: Int) {
        when (viewId) {
            R.id.btn_product_details -> mView?.onProductDetailsClick()

            R.id.btn_product_price -> mView?.onProductPriceClick()

            R.id.btn_adjust_warn -> mView?.onAdjustWarnClick()
        }
    }

    /**
     * 加载产品详情
     *
     * @param id 产品id
     * */
    override fun loadData(id: String) {
        mModel.details(id, object : DataCallback<ProductDetailsBean> {
            override fun onSuccess(t: ProductDetailsBean?, message: String?) {
                mView?.refreshData(t)
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }
        })
    }

    /**
     * 修改库存警告线
     *
     * @param id 产品id
     * @param cordon 库存警告线
     * */
    override fun editWarnLine(id: String, cordon: Int) {
        mModel.editWarnLine(id, cordon, object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                mView?.editWarnLineSuccess()
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }
        })
    }

    /**
     * 修改零售价
     *
     * @param id 产品id
     * @param price 零售价
     * */
    override fun editPrice(id: String, price: Double) {
        mModel.editPrice(id, price, object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                mView?.editPriceSuccess()
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }
        })
    }
}