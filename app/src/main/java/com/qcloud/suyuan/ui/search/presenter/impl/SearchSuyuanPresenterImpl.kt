package com.qcloud.suyuan.ui.search.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.SuyuanDetailsBean
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.search.presenter.ISearchSuyuanPresenter
import com.qcloud.suyuan.ui.search.view.ISearchSuyuanView

/**
 * Description: 搜索溯源码
 * Author: gaobaiqiang
 * 2018/4/1 下午10:39.
 */
class SearchSuyuanPresenterImpl: BasePresenter<ISearchSuyuanView>(), ISearchSuyuanPresenter {
    private val mModel: IGoodsModel = GoodsModelImpl()

    /**
     * 搜索溯源码
     *
     * @param keyword 溯源码
     * */
    override fun loadData(keyword: String) {
        mModel.suyuanSearch(keyword, object : DataCallback<SuyuanDetailsBean> {
            override fun onSuccess(t: SuyuanDetailsBean?, message: String?) {
                if (t != null) {
                    mView?.replaceData(t)
                } else {
                    mView?.showEmptyView("抱歉！没有搜索到符合条件的产品，请重新扫码获取~")
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
                mView?.showEmptyView(message)
            }
        })
    }

}