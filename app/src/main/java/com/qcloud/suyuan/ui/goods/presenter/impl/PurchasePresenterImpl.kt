package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.ui.goods.presenter.IPurchasePresenter
import com.qcloud.suyuan.ui.goods.view.IPurchaseView

/**
 * Description: 进货
 * Author: gaobaiqiang
 * 2018/3/15 上午12:32.
 */
class PurchasePresenterImpl: BasePresenter<IPurchaseView>(), IPurchasePresenter {

    override fun loadProduct(barCode: String) {
        val bean = ProductBean()
        bean.amount = 998
        bean.barCode = barCode
        bean.id = "24240807"
        bean.name = "莠去津50%(悬浮剂)"
        bean.specification = "100g*20瓶/件"
        bean.millName = "甘肃省武山县农药厂"

        mView?.addProductAtEnd(bean)
    }
}