package com.qcloud.suyuan.ui.main.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.materialdesign.dialogs.MaterialDialog
import com.qcloud.qclib.materialdesign.enums.DialogAction
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseApplication
import com.qcloud.suyuan.realm.RealmHelper
import com.qcloud.suyuan.ui.goods.widget.ModifyPriceActivity
import com.qcloud.suyuan.ui.goods.widget.PurchaseActivity
import com.qcloud.suyuan.ui.goods.widget.SellersActivity
import com.qcloud.suyuan.ui.main.presenter.impl.MainPresenterImpl
import com.qcloud.suyuan.ui.main.view.IMainView
import com.qcloud.suyuan.ui.order.widget.SellingWaterActivity
import com.qcloud.suyuan.ui.record.widget.CreditRecordActivity
import com.qcloud.suyuan.ui.record.widget.ReturnRecordActivity
import com.qcloud.suyuan.ui.storage.widget.OutStorageActivity
import com.qcloud.suyuan.ui.store.widget.StoreProductActivity
import kotlinx.android.synthetic.main.card_main_credit_record.*
import kotlinx.android.synthetic.main.card_main_modify_price.*
import kotlinx.android.synthetic.main.card_main_more.*
import kotlinx.android.synthetic.main.card_main_out_storage.*
import kotlinx.android.synthetic.main.card_main_purchase.*
import kotlinx.android.synthetic.main.card_main_return_record.*
import kotlinx.android.synthetic.main.card_main_sellers.*
import kotlinx.android.synthetic.main.card_main_selling_water.*
import kotlinx.android.synthetic.main.card_main_store_product.*

/**
 * 类说明：主页
 * Author: Kuzan
 * Date: 2018/3/12 15:29.
 */
class MainActivity: BaseActivity<IMainView, MainPresenterImpl>(), IMainView, View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initPresenter(): MainPresenterImpl? {
        return MainPresenterImpl()
    }

    override fun initViewAndData() {
        layout_sellers.setOnClickListener(this)
        layout_purchase.setOnClickListener(this)
        layout_modify_price.setOnClickListener(this)
        layout_selling_water.setOnClickListener(this)
        layout_out_storage.setOnClickListener(this)
        layout_store_product.setOnClickListener(this)
        layout_credit_record.setOnClickListener(this)
        layout_return_record.setOnClickListener(this)
        layout_more.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.layout_sellers -> SellersActivity.openActivity(this)
            R.id.layout_purchase -> PurchaseActivity.openActivity(this)
            R.id.layout_modify_price -> ModifyPriceActivity.openActivity(this)
            R.id.layout_selling_water -> SellingWaterActivity.openActivity(this)
            R.id.layout_out_storage -> OutStorageActivity.openActivity(this)
            R.id.layout_store_product -> StoreProductActivity.openActivity(this)
            R.id.layout_credit_record -> CreditRecordActivity.openActivity(this)
            R.id.layout_return_record -> ReturnRecordActivity.openActivity(this)
            R.id.layout_more -> QToast.error(this, R.string.tab_main_more)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            MaterialDialog.Builder(this)
                    .content(R.string.toast_app_exit)
                    .contentColor(ApiReplaceUtil.getColor(this, R.color.colorTitle))
                    .positiveText(R.string.btn_confirm)
                    .negativeText(R.string.btn_cancel)
                    .onPositive(object : MaterialDialog.SingleButtonCallback {
                        override fun onClick(dialog: MaterialDialog, which: DialogAction) {
                            RealmHelper.instance.closeRealm()
                            BaseApplication.mAppManager?.appExit(this@MainActivity)
                        }
                    })
                    .show()
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}