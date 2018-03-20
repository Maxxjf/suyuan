package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.suyuan.ui.goods.presenter.IWarnPresenter
import com.qcloud.suyuan.ui.goods.view.IWarnView
import java.util.ArrayList

/**
 * Description: 告警提示
 * Author: gaobaiqiang
 * 2018/3/20 下午8:30.
 */
class WarnPresenterImpl: BasePresenter<IWarnView>(), IWarnPresenter {

    override fun createViewPager() {
        val list: MutableList<String> = ArrayList()
        list.add("库存告警")
        list.add("有效期告警")

        mView?.replaceList(list)
    }
}