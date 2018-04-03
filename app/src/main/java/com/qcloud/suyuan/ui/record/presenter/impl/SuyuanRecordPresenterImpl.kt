package com.qcloud.suyuan.ui.record.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.SuyuanRecordBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.record.presenter.ISuyuanRecordPresenter
import com.qcloud.suyuan.ui.record.view.ISuyuanRecordView

/**
 * Description: 溯源记录
 * Author: gaobaiqiang
 * 2018/3/20 上午8:54.
 */
class SuyuanRecordPresenterImpl: BasePresenter<ISuyuanRecordView>(), ISuyuanRecordPresenter {
    private val mModel: IGoodsModel = GoodsModelImpl()

    override fun loadData(keyword: String?, pageNo: Int) {
        mModel.suyuanList(keyword, pageNo, AppConstants.PAGE_SIZE, object : DataCallback<ReturnDataBean<SuyuanRecordBean>> {
            override fun onSuccess(t: ReturnDataBean<SuyuanRecordBean>?, message: String?) {
                if (t?.list != null) {
                    if (pageNo == 1) {
                        mView?.replaceList(t.list, t.isNext(AppConstants.PAGE_SIZE))
                    } else {
                        mView?.addListAtEnd(t.list, t.isNext(AppConstants.PAGE_SIZE))
                    }
                } else {
                    mView?.loadErr(message ?: "加载数据出错")
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }
        })
    }
}