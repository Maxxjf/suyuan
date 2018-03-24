package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.suyuan.R
import com.qcloud.suyuan.ui.goods.presenter.IPurchaseDetailsPresenter
import com.qcloud.suyuan.ui.goods.view.IPurchaseDetailsView

/**
 * Description: 确认入库
 * Author: gaobaiqiang
 * 2018/3/23 下午9:26.
 */
class PurchaseDetailsPresenterImpl: BasePresenter<IPurchaseDetailsView>(), IPurchaseDetailsPresenter {
    override fun onBtnClick(viewId: Int) {
        when (viewId) {
            R.id.btn_in_storage_supplier -> mView?.onAddSupplierClick()
            R.id.btn_confirm -> mView?.onConfirmClick()
            R.id.btn_clear -> mView?.onClearClick()
        }
    }
}