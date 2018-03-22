package com.qcloud.suyuan.ui.setting.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.EmptyResBean
import com.qcloud.suyuan.model.impl.UserModelImpl
import com.qcloud.suyuan.ui.setting.presenter.IForgetPasswordPresenter
import com.qcloud.suyuan.ui.setting.view.IForgetpasswordView

/**
 * 类型：IForgetPasswordPresenterImpl
 * Author: iceberg
 * Date: 2018/3/19.
 * 忘记密码
 */
class IForgetPasswordPresenterImpl:BasePresenter<IForgetpasswordView>(), IForgetPasswordPresenter {
    private var mModel:UserModelImpl= UserModelImpl()
    override fun getCode(mobile: String) {
            mModel.getCode(mobile,object :DataCallback<EmptyResBean>{
                override fun onSuccess(t: EmptyResBean?, message: String?) {

                }

                override fun onError(status: Int, message: String) {

                }
            })
    }

    override fun forgetPassword(code:String,mobile:String,password:String){
        mModel.forgetPassword(code,mobile,password,object :DataCallback<EmptyResBean>{
            override fun onSuccess(t: EmptyResBean?, message: String?) {
                mView?.forgetPasswordSuccess()
            }

            override fun onError(status: Int, message: String) {

            }
        })
    }

}