package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.InStorageBean
import com.qcloud.suyuan.model.IStorageModel
import com.qcloud.suyuan.model.impl.StorageModelImpl
import com.qcloud.suyuan.ui.goods.presenter.IPurchaseDetailsPresenter
import com.qcloud.suyuan.ui.goods.view.IPurchaseDetailsView

/**
 * Description: 确认入库
 * Author: gaobaiqiang
 * 2018/3/23 下午9:26.
 */
class PurchaseDetailsPresenterImpl: BasePresenter<IPurchaseDetailsView>(), IPurchaseDetailsPresenter {
    private val mModel: IStorageModel = StorageModelImpl()

    override fun onBtnClick(viewId: Int) {
        when (viewId) {
            R.id.btn_in_storage_supplier -> mView?.onAddSupplierClick()
            R.id.btn_confirm -> mView?.onConfirmClick()
            R.id.btn_clear -> mView?.onClearClick()
            R.id.tv_in_storage_number -> mView?.onStockNumberClick()
            R.id.tv_in_storage_price -> mView?.onPriceClick()
        }
    }

    override fun save(goodId: String, number: Int, price: Double, expDate: String, stopDate: String, supplierId: String?) {
        mModel.save(goodId, number, price, expDate, stopDate, supplierId, object : DataCallback<InStorageBean> {
            override fun onSuccess(t: InStorageBean?, message: String?) {
                if (t != null) {
                    mView?.saveSuccess(t)
                } else {
                    mView?.loadErr("入库失败，请重新提交", true)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }
        })
    }
}