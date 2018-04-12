package com.qcloud.suyuan.ui.main.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.update.UpdateUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseApplication
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.MainFormBean
import com.qcloud.suyuan.beans.VersionBean
import com.qcloud.suyuan.realm.RealmHelper
import com.qcloud.suyuan.ui.goods.widget.*
import com.qcloud.suyuan.ui.main.presenter.impl.MainPresenterImpl
import com.qcloud.suyuan.ui.main.view.IMainView
import com.qcloud.suyuan.ui.order.widget.SellingWaterActivity
import com.qcloud.suyuan.ui.record.widget.CreditRecordActivity
import com.qcloud.suyuan.ui.storage.widget.OutStorageActivity
import com.qcloud.suyuan.ui.store.widget.StoreProductActivity
import com.qcloud.suyuan.utils.NFCHelper
import com.qcloud.suyuan.utils.PrintHelper
import com.qcloud.suyuan.widgets.dialog.DownloadApkDialog
import com.qcloud.suyuan.widgets.dialog.MoreOperationDialog
import com.qcloud.suyuan.widgets.dialog.OperationTipDialog
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
class MainActivity : BaseActivity<IMainView, MainPresenterImpl>(), IMainView, View.OnClickListener {

    private var searchDialog: SearchSelectDialog? = null
    private var moreDialog: MoreOperationDialog? = null
    private var outDialog: OperationTipDialog? = null //退出登录的对话框
    private var newVersionDialog: OperationTipDialog? = null

    private var latestVersion: String? = null

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
        btn_get_more.setOnClickListener(this)

        initToolbar()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.getMainForm()
        mPresenter?.checkVersion(UpdateUtil.getVersionCode(this))
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
            R.id.layout_return -> ReturnedActivity.openActivity(this)
            R.id.layout_more -> {
                if (moreDialog == null) {
                    moreDialog = MoreOperationDialog(this@MainActivity)
                }
                moreDialog?.show()
            }
            R.id.btn_get_more -> WarnActivity.openActivity(this)
        }
    }

    override fun showMainForm(bean: MainFormBean) {
        if (isRunning) {
            val todayForm = bean.todayBusiness
            if (todayForm != null) {
                tv_income.text = todayForm.earningStr
                tv_credit.text = todayForm.onCreditStr
                tv_valid_order.text = String.format(resources.getString(R.string.num_of_valid_order), todayForm.order)
                tv_return.text = todayForm.returnMoneyStr
            }
            val warnBean = bean.alarm
            if (warnBean != null) {
                tv_valid_warn.text = warnBean.indateStr
                tv_stock_warn.text = warnBean.stockStr
            }
        }
    }

    override fun checkVersion(bean: VersionBean) {
        if (isRunning) {
            if (bean.hasLatest) {
                if (newVersionDialog == null) {
                    initNewVersionDialog()
                }
                newVersionDialog?.setTip(String.format(getString(R.string.tip_find_new_version), bean.latestVersion))
                newVersionDialog?.show()
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

    private fun initNewVersionDialog() {
        newVersionDialog = OperationTipDialog(this)
        newVersionDialog?.setCancelBtn(R.string.tip_no)
        newVersionDialog?.setConfirmBtn(R.string.tip_yes)
        newVersionDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                if (view.id == R.id.btn_ok) {
                    showDownloadDialog()
                }
            }
        }
    }

    private fun showDownloadDialog() {
        val dialog = DownloadApkDialog(this)
        dialog.downloadApk(latestVersion ?: "V1.0.0")
        dialog.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (outDialog == null) {
                outDialog = OperationTipDialog(this)
                outDialog?.setTip(R.string.toast_app_exit)
                outDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
                    override fun onBtnClick(view: View) {
                        when (view.id) {
                            R.id.btn_ok -> {
                                RealmHelper.instance.closeRealm()
                                BaseApplication.mAppManager?.appExit(this@MainActivity)
                                NFCHelper.instance.close()
                                PrintHelper.instance.close()
                            }
                        }
                    }
                }
            }
            outDialog?.show()
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

        if (outDialog != null && outDialog!!.isShowing) {
            outDialog?.let {
                outDialog?.dismiss()
            }
        }

        if (newVersionDialog != null && newVersionDialog!!.isShowing) {
            newVersionDialog?.let {
                newVersionDialog?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}