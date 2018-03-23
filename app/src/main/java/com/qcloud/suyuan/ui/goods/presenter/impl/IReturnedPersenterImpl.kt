package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.suyuan.beans.GoodsBean
import com.qcloud.suyuan.ui.goods.presenter.IReturnedPresenter
import com.qcloud.suyuan.ui.goods.view.IReturnedView

/**
 * 类型：IReturnedPersenterImpl
 * Author: iceberg
 * Date: 2018/3/19.
 * TODO:
 */
class IReturnedPersenterImpl : BasePresenter<IReturnedView>(),IReturnedPresenter {
    override fun loadData() {
      var list:ArrayList<GoodsBean> = ArrayList<GoodsBean>()
        var goods:GoodsBean= GoodsBean()
        goods.id="sjaifajdadfa02"
        goods.name="我是一瓶毒药"
        goods.date="20180102"
        goods.rule="20元/瓶"
        goods.number="2"
        goods.price="￥2.00"
        list.add(goods)
        var goods2:GoodsBean= GoodsBean()
        goods2.id="sjaifajdadfa02"
        goods2.name="我是一瓶毒药"
        goods2.date="20180102"
        goods2.rule="20元/瓶"
        goods2.number="2"
        goods2.price="￥2.00"
        list.add(goods2)
        var goods3:GoodsBean= GoodsBean()
        goods3.id="sjaifajdadfa02"
        goods3.name="我是一瓶毒药"
        goods3.date="20180102"
        goods3.rule="20元/瓶"
        goods3.number="2"
        goods3.price="￥2.00"
        list.add(goods3)

        mView?.replaceReceiptList(list)
    }
}