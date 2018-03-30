package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.SupplierBean
import com.qcloud.suyuan.model.IStoreModel
import com.qcloud.suyuan.model.impl.StoreModelImpl
import com.qcloud.suyuan.ui.store.presenter.IMySupplierPresenter
import com.qcloud.suyuan.ui.store.view.IMySupplierView

/**
 * Description: 我的供应商
 * Author: gaobaiqiang
 * 2018/3/20 上午9:03.
 */
class MySupplierPresenterImpl: BasePresenter<IMySupplierView>(), IMySupplierPresenter {
    private var mModel:IStoreModel=StoreModelImpl()
    fun getSupplierList(keyword:String){
        mModel.supplierList(keyword,object :DataCallback<ReturnDataBean<SupplierBean>>{
            override fun onSuccess(t: ReturnDataBean<SupplierBean>?, message: String?) {
                if (t!=null){
                    mView?.replaceList(t.list,false)
                }else{
                    if (message != null) {
                        mView?.loadErr(message,true)
                    }
                }

            }

            override fun onError(status: Int, message: String) {
                if (message != null) {
                    mView?.loadErr(message,true)
                }
            }
        })
    }
}