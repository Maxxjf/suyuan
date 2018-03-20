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
import com.qcloud.suyuan.beans.MainFormBean
import com.qcloud.suyuan.realm.RealmHelper
import com.qcloud.suyuan.ui.goods.widget.ModifyPriceActivity
import com.qcloud.suyuan.ui.goods.widget.PurchaseActivity
import com.qcloud.suyuan.ui.goods.widget.SellersActivity
import com.qcloud.suyuan.ui.main.presenter.impl.MainPresenterImpl
import com.qcloud.suyuan.ui.main.view.IMainView
import com.qcloud.suyuan.ui.order.widget.ReturnActivity
import com.qcloud.suyuan.ui.order.widget.SellingWaterActivity
import com.qcloud.suyuan.ui.record.widget.CreditRecordActivity
import com.qcloud.suyuan.ui.record.widget.ReturnRecordActivity
import com.qcloud.suyuan.ui.storage.widget.OutStorageActivity
import com.qcloud.suyuan.ui.store.widget.StoreProductActivity
import com.qcloud.suyuan.widgets.dialog.MoreOperationDialog
import com.qcloud.suyuan.widgets.dialog.SearchSelectDialog
import com.qcloud.suyuan.widgets.toolbar.CustomToolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_main_credit_record.*
import kotlinx.android.synthetic.main.card_main_modify_price.*
import kotlinx.android.synthetic.main.card_main_more.*
import kotlinx.android.synthetic.main.card_main_out_storage.*
import kotlinx.android.synthetic.main.card_main_purchase.*
import kotlinx.android.synthetic.main.card_main_report_form.*
import kotlinx.android.synthetic.main.card_main_return.*
import kotlinx.android.synthetic.main.card_main_sellers.*
import kotlinx.android.synthetic.main.card_main_selling_water.*
import kotlinx.android.synthetic.main.card_main_store_product.*
import kotlinx.android.synthetic.main.card_main_warn.*
import timber.log.Timber

/**
 * 类说明：主页
 * Author: Kuzan
 * Date: 2018/3/12 15:29.
 */
class MainActivity: BaseActivity<IMainView, MainPresenterImpl>(), IMainView, View.OnClickListener {

    private var searchDialog: SearchSelectDialog? = null
    private var moreDialog: MoreOperationDialog? = null

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
        layout_return.setOnClickListener(this)
        layout_more.setOnClickListener(this)

        initToolbar()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.getMainForm()
    }

    private fun initToolbar() {
        toolbar.onBtnClickListener = object : CustomToolbar.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                if (searchDialog == null) {
                    searchDialog = SearchSelectDialog(this@MainActivity)
                }
                searchDialog?.show()
            }
        }
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
            R.id.layout_return -> ReturnActivity.openActivity(this)
            R.id.layout_more -> {
                if (moreDialog == null) {
                    moreDialog = MoreOperationDialog(this@MainActivity)
                }
                moreDialog?.show()
            }
        }
    }

    override fun showMainForm(bean: MainFormBean) {
        if (isRunning) {
            var todayForm = bean.todayBusiness
            if (todayForm != null) {
                tv_income.text = todayForm.earningStr
                tv_credit.text = todayForm.onCreditStr
                tv_valid_order.text = String.format(resources.getString(R.string.num_of_valid_order), todayForm.order)
                tv_return.text = todayForm.returnMoneyStr
            }
            var warnBean = bean.alarm
            if (warnBean != null) {
                tv_valid_warn.text = warnBean.indateStr
                tv_stock_warn.text = warnBean.stockStr
            }
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isRunning) {
            if (isShow) {
                QToast.show(this, errMsg)
            } else {
                Timber.e(errMsg)
            }
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

    override fun onDestroy() {
        super.onDestroy()
        if (searchDialog != null && searchDialog!!.isShowing) {
            searchDialog?.let {
                searchDialog?.dismiss()
            }
        }

        if (moreDialog != null && moreDialog!!.isShowing) {
            moreDialog?.let {
                moreDialog?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}