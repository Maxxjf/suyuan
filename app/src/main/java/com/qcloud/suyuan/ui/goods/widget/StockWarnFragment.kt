package com.qcloud.suyuan.ui.goods.widget

import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseFragment
import com.qcloud.suyuan.ui.goods.presenter.impl.StockWarnPresenterImpl
import com.qcloud.suyuan.ui.goods.view.IStockWarnView

/**
 * Description: 库存警告
 * Author: gaobaiqiang
 * 2018/3/20 下午9:01.
 */
class StockWarnFragment: BaseFragment<IStockWarnView, StockWarnPresenterImpl>(), IStockWarnView {
    override val layoutId: Int
        get() = R.layout.fragment_stock_warn

    override fun initPresenter(): StockWarnPresenterImpl? {
        return StockWarnPresenterImpl()
    }

    override fun initViewAndData() {

    }

    override fun beginLoad() {

    }
}