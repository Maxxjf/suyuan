package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.suyuan.beans.StoreProductBean
import com.qcloud.suyuan.ui.store.presenter.IStoreProductPresenter
import com.qcloud.suyuan.ui.store.view.IStoreProductView

/**
 * Description: 门店产品
 * Author: gaobaiqiang
 * 2018/3/15 上午12:50.
 */
class StoreProductPresenterImpl: BasePresenter<IStoreProductView>(), IStoreProductPresenter {

    override fun loadData(pageNo: Int) {
        val list: MutableList<StoreProductBean> = ArrayList()
        var bean = StoreProductBean()
        bean.amount = 338
        bean.barCode = "785763352032"
        bean.name = "莠去津50%(悬浮剂)"
        bean.specification = "100g*20瓶/件"
        bean.millName = "甘肃省武山县农药厂"
        bean.operator = "易辉"
        bean.lastTime = "2018/03/14 12:03"
        list.add(bean)

        bean = StoreProductBean()
        bean.amount = 338
        bean.barCode = "785763352032"
        bean.name = "莠去津50%(悬浮剂)"
        bean.specification = "100g*20瓶/件"
        bean.millName = "甘肃省武山县农药厂"
        bean.operator = "易辉"
        bean.lastTime = "2018/03/14 12:03"
        list.add(bean)

        bean = StoreProductBean()
        bean.amount = 338
        bean.barCode = "785763352032"
        bean.name = "莠去津50%(悬浮剂)"
        bean.specification = "100g*20瓶/件"
        bean.millName = "甘肃省武山县农药厂"
        bean.operator = "易辉"
        bean.lastTime = "2018/03/14 12:03"
        list.add(bean)

        bean = StoreProductBean()
        bean.amount = 338
        bean.barCode = "785763352032"
        bean.name = "莠去津50%(悬浮剂)"
        bean.specification = "100g*20瓶/件"
        bean.millName = "甘肃省武山县农药厂"
        bean.operator = "易辉"
        bean.lastTime = "2018/03/14 12:03"
        list.add(bean)

        bean = StoreProductBean()
        bean.amount = 338
        bean.barCode = "785763352032"
        bean.name = "莠去津50%(悬浮剂)"
        bean.specification = "100g*20瓶/件"
        bean.millName = "甘肃省武山县农药厂"
        bean.operator = "易辉"
        bean.lastTime = "2018/03/14 12:03"
        list.add(bean)

        bean = StoreProductBean()
        bean.amount = 338
        bean.barCode = "785763352032"
        bean.name = "莠去津50%(悬浮剂)"
        bean.specification = "100g*20瓶/件"
        bean.millName = "甘肃省武山县农药厂"
        bean.operator = "易辉"
        bean.lastTime = "2018/03/14 12:03"
        list.add(bean)

        bean = StoreProductBean()
        bean.amount = 338
        bean.barCode = "785763352032"
        bean.name = "莠去津50%(悬浮剂)"
        bean.specification = "100g*20瓶/件"
        bean.millName = "甘肃省武山县农药厂"
        bean.operator = "易辉"
        bean.lastTime = "2018/03/14 12:03"
        list.add(bean)

        mView?.replaceList(list, false)
    }
}