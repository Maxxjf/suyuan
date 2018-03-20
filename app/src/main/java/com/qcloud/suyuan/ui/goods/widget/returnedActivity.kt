package com.qcloud.suyuan.ui.goods.widget

import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.SwipeBaseActivity
import com.qcloud.suyuan.ui.goods.presenter.impl.IReturnedPersenterImpl
import com.qcloud.suyuan.ui.goods.view.IReturnedView

/**
 * 类型：returnedActivity
 * Author: iceberg
 * Date: 2018/3/19.
 * 退货
 */
class returnedActivity :SwipeBaseActivity<IReturnedView,IReturnedPersenterImpl>(),IReturnedView{
    override val layoutId: Int
        get() = R.layout.activity_cart

    override fun initPresenter(): IReturnedPersenterImpl? = IReturnedPersenterImpl()

    override fun initViewAndData() {

    }
}