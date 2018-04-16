package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.RxBusEvent
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.rxbus.BusProvider
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.model.IStoreModel
import com.qcloud.suyuan.model.impl.StoreModelImpl
import com.qcloud.suyuan.ui.store.presenter.ISupplierAddPresenter
import com.qcloud.suyuan.ui.store.view.ISupplierAddView

/**
 * 类型：SupplierAddPresenterImpl
 * Author: iceberg
 * Date: 2018/3/26.
 * 新增供应商
 */
class SupplierAddPresenterImpl :BasePresenter<ISupplierAddView>(),ISupplierAddPresenter {
    private var mModel: IStoreModel = StoreModelImpl()
    /**
     * @param address	供应商地址
     * @param  classifyId	供应品类id字符串( , 隔开)
     * @param id	供应商id(不传为新增,传为修改)
     * @param name	供应商名称
     * @param phone	联系电话
     * @param  principal	联系人
     * @param remark	备注
     * 新增/修改供应商
     */
    override fun addSupplier(address: String, name: String, phone: String, principal: String, remark: String){
        mModel.supplierSaveOrUpdate(address,"","",name,phone,principal,remark,object :DataCallback<EmptyReturnBean>{
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                if (message != null) {
                    mView?.loadErr(message)
                    mView?.clearEdit()
                    BusProvider.instance.post(RxBusEvent.newBuilder(R.id.id_clidk_add_supplier).build())
                }
            }

            override fun onError(status: Int, message: String) {
                if (message != null) {
                    mView?.loadErr(message)
                }
            }

        })
    }
}