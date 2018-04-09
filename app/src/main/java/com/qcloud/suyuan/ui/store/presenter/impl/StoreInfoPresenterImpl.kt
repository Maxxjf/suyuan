package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.TokenUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseApplication
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.StoreBean
import com.qcloud.suyuan.model.impl.StoreModelImpl
import com.qcloud.suyuan.ui.main.widget.LoginActivity
import com.qcloud.suyuan.ui.store.presenter.IStoreInfoPresenter
import com.qcloud.suyuan.ui.store.view.IStoreInfoView
import com.qcloud.suyuan.utils.UserInfoUtil

/**
 * Description: 门店信息
 * Author: gaobaiqiang
 * 2018/3/20 上午9:05.
 */
class StoreInfoPresenterImpl: BasePresenter<IStoreInfoView>(), IStoreInfoPresenter {
    var mModel=StoreModelImpl()

    /**
     * 获取初始信息
     */
    override fun getInfo(){
        mModel.getInfo(object :DataCallback<StoreBean>{
            override fun onSuccess(t: StoreBean?, message: String?) {
                if (t!=null) {
                    mView?.getInfoSuccess(t)
                }
            }

            override fun onError(status: Int, message: String) {
                if (message != null) {
                    mView?.loadErr(message)
                }
            }

        })
    }

    /**
     * 修改密码
     */
    override fun editPassword(oldPwd:String,newPwd:String,againNewPwd:String){
        mModel.editStorePassword(oldPwd,newPwd,againNewPwd,object :DataCallback<EmptyReturnBean>{
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {

            }

            override fun onError(status: Int, message: String) {
                if (message != null) {
                    mView?.loadErr(message)
                }
            }

        })
    }
    /**
     * 修改信息
     */
    override fun editInfo(address: String, phone: String, shopkeeperName: String){
        mModel.editStoreInfo(address,phone,shopkeeperName,"","","","",object :DataCallback<EmptyReturnBean>{
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                if (message != null) {
                    mView?.loadErr(message)
                    getInfo()//修改成功后重新加载
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