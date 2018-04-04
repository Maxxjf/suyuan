package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.View
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
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
class StoreInfoActivity : BaseActivity<IStoreInfoView, StoreInfoPresenterImpl>(), IStoreInfoView,View.OnClickListener {


    var mCurrentStore: StoreBean? = null


    var infoDialog: EditStoreInfoDialog? = null
    var passwordDialog: UpdatePasswordDialog? = null
    var tipDialog: TipDialog? = null
    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (tipDialog == null) {
            tipDialog = TipDialog(this)
        }
        if (isShow) {
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
        initView()
        getInfo()
    }

    private fun initView() {
        btn_edit_info.setOnClickListener(this)
        btn_edit_password.setOnClickListener(this)
    }

    private fun getInfo() {
        mPresenter?.getInfo()
    }
    override fun onClick(v: View?) {
        when(v){
            btn_edit_info ->showInfoDialog()
            btn_edit_password ->showPasswordDialog()
        }
    }
    /**
     * 展示修改信息的对话框
     */
    override fun showInfoDialog() {
        if (infoDialog == null) {
            infoDialog = EditStoreInfoDialog(this)
            infoDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
                override fun onBtnClick(view: View) {
                    when (view.id) {
                        R.id.btn_confirm -> editInfo()
                    }
                }

            }
        }
        infoDialog?.replaceInfo(mCurrentStore!!)
        infoDialog?.show()
    }

    /**
     * 修改信息提交
     */
    private fun editInfo() {
        var address = infoDialog?.getAddress()
        var person = infoDialog?.getPerson()
        var phone = infoDialog?.getPhone()
        mPresenter?.editInfo(address!!, phone!!, person!!)
    }

    /**
     * 展示修改密码对话框
     */
    override fun showPasswordDialog() {
        if (passwordDialog == null) {
            passwordDialog = UpdatePasswordDialog(this)
            passwordDialog?.onBtnClickListener=object :BaseDialog.OnBtnClickListener{
                override fun onBtnClick(view: View) {
                    when(view.id){
                        R.id.btn_confirm ->editPassword()
                    }
                }

            }
        }
        passwordDialog?.show()

    }

    private fun editPassword() {
        var oldPassword=passwordDialog?.getPassword()
        var newPassword=passwordDialog?.getNewPassword()
        var newPassword2rd=passwordDialog?.getNewPassword2rd()
        mPresenter?.editPassword(oldPassword!!, newPassword!!, newPassword2rd!!)
    }

    /**
     * 加载信息成功
     */
    override fun getInfoSuccess(bean: StoreBean) {
        mCurrentStore = bean
        tv_number.text = "${bean.store!!.storeCode}"
        tv_name.text = "${bean.store!!.name}"
        tv_address.text = "${bean.store!!.address}"
        tv_person.text = "${bean.store!!.shopkeeperName}"
        tv_phone.text = "${bean.store!!.phone}"
        tv_area.text = "${bean.store!!.areaName}"
        tv_login_account.text = "${bean.store!!.userName}"
        if (bean.storeBusiness!!.size>=2){
            GlideUtil.loadImage(this, iv_bussiness_license, bean.storeBusiness!![0].image)
            GlideUtil.loadImage(this, iv_bussiness_certificate, bean.storeBusiness!![1].image)
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, StoreInfoActivity::class.java))
        }
    }
}