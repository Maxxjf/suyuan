package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.store.presenter.impl.CreateProductIIPresenterImpl
import com.qcloud.suyuan.ui.store.view.ICreateProductIIView
import com.qcloud.suyuan.widgets.dialog.TipDialog

/**
 * Description: 创建产品第二步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:59.
 */
class CreateProductIIActivity: BaseActivity<ICreateProductIIView, CreateProductIIPresenterImpl>(), ICreateProductIIView {

    private var tipDialog: TipDialog? = null

    override val layoutId: Int
        get() = R.layout.activity_create_product_ii

    override fun initPresenter(): CreateProductIIPresenterImpl? {
        return CreateProductIIPresenterImpl()
    }

    override fun initViewAndData() {

    }

    private fun initTip() {
        tipDialog = TipDialog(this)
        tipDialog?.setConfirmBtn(R.string.btn_i_know)
    }

    override fun onDestroy() {
        super.onDestroy()
        tipDialog.let {
            if (tipDialog != null && tipDialog!!.isShowing) {
                tipDialog?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            val intent = Intent(context, CreateProductIIActivity::class.java)
            context.startActivity(intent)
        }
    }
}