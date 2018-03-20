package com.qcloud.suyuan.ui.goods.widget

import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseFragment
import com.qcloud.suyuan.ui.goods.presenter.impl.ValidWarnPresenterImpl
import com.qcloud.suyuan.ui.goods.view.IValidWarnView

/**
 * Description: 有效期告警
 * Author: gaobaiqiang
 * 2018/3/20 下午9:08.
 */
class ValidWarnFragment: BaseFragment<IValidWarnView, ValidWarnPresenterImpl>(), IValidWarnView {
    override val layoutId: Int
        get() = R.layout.fragment_valid_warn

    override fun initPresenter(): ValidWarnPresenterImpl? {
        return ValidWarnPresenterImpl()
    }

    override fun initViewAndData() {

    }

    override fun beginLoad() {

    }
}