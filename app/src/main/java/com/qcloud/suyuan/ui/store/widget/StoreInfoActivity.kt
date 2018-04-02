package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.beans.StoreBean
import com.qcloud.suyuan.ui.store.presenter.impl.StoreInfoPresenterImpl
import com.qcloud.suyuan.ui.store.view.IStoreInfoView
import com.qcloud.suyuan.widgets.dialog.EditStoreInfoDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
import com.qcloud.suyuan.widgets.dialog.UpdatePasswordDialog
import kotlinx.android.synthetic.main.activity_store_info.*

/**
 * Description: 门店信息
 * Author: iceberg
 * 2018/4/2 我的门店信息
 */
class StoreInfoActivity: BaseActivity<IStoreInfoView, StoreInfoPresenterImpl>(), IStoreInfoView {

    var infoDialog:EditStoreInfoDialog?=null
    var passwordDialog:UpdatePasswordDialog?=null
    var tipDialog:TipDialog?=null
    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (tipDialog==null){
            tipDialog= TipDialog(this)
        }
        if (isShow){
            tipDialog?.setTip(errMsg)
            tipDialog?.show()
        }
    }
    override val layoutId: Int
        get() = R.layout.activity_store_info

    override fun initPresenter(): StoreInfoPresenterImpl? {
        return StoreInfoPresenterImpl()
    }

    override fun initViewAndData() {
        getInfo()
    }

    private fun getInfo() {
        mPresenter?.getInfo()
    }

    /**
     * 展示修改信息的对话框
     */
    override fun showInfoDialog(){
        if (infoDialog==null){
            infoDialog= EditStoreInfoDialog(this)
        }
    }

    /**
     * 加载信息成功
     */
    override fun getInfoSuccess(bean:StoreBean){
        tv_number.text= "${bean.store!!.storeCode}"
        tv_name.text= "${bean.store!!.name}"
        tv_address.text="${bean.store!!.address}"
        tv_person.text="${ bean.store!!.shopkeeperName}"
        tv_phone.text= "${bean.store!!.phone}"
        tv_area.text= "${bean.store!!.areaName}"
        tv_login_account.text="${bean.store!!.userName}"
        GlideUtil.loadImage(this,iv_bussiness_license, bean.storeBusiness!![0].image)
        GlideUtil.loadImage(this,iv_bussiness_certificate, bean.storeBusiness!![1].image)
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, StoreInfoActivity::class.java))
        }
    }
}