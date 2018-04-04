package com.qcloud.suyuan.ui.search.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.BarCodeDetailsBean
import com.qcloud.suyuan.beans.SuyuanDetailsBean
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.search.presenter.ISearchProductPresenter
import com.qcloud.suyuan.ui.search.view.ISearchProductView

/**
 * Description: 搜索产品条形码
 * Author: gaobaiqiang
 * 2018/4/1 下午11:14.
 */
class SearchProductPresenterImpl: BasePresenter<ISearchProductView>(), ISearchProductPresenter {
    private val mModel: IGoodsModel = GoodsModelImpl()

    /**
     * 搜索产品条形码
     *
     * @param keyword 产品条形码
     * */
    override fun loadData(keyword: String) {
        mModel.barCodeSearch(keyword, object : DataCallback<BarCodeDetailsBean> {
            override fun onSuccess(t: BarCodeDetailsBean?, message: String?) {
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