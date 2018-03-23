package com.qcloud.suyuan.ui.record.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.CodeBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.record.presenter.IReturnRecordPresenter
import com.qcloud.suyuan.ui.record.view.IReturnRecordView

/**
 * Description: 退货记录
 * Author: gaobaiqiang
 * 2018/3/15 上午1:01.
 */
class ReturnRecordPresenterImpl: BasePresenter<IReturnRecordView>(), IReturnRecordPresenter {
    var mModel:IGoodsModel=GoodsModelImpl()
    /**
     * 加载数据
     */
    override fun loadData(startTime:String,endTime:String,pageNo:Int){
        mModel?.getReturnRecord(startTime,endTime,pageNo,AppConstants.PAGE_SIZE,object : DataCallback<ReturnDataBean<CodeBean>>{
            override fun onSuccess(t: ReturnDataBean<CodeBean>?, message: String?) {
                if (t != null) {
                    if (pageNo == 1) {
                        mView?.replaceList(t.list, t.isNext(AppConstants.PAGE_SIZE))
                    } else {
                        mView?.addListAtEnd(t.list, t.isNext(AppConstants.PAGE_SIZE))
                    }
                }
            }
            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }
        })
    }
}