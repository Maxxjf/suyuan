package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.View
import android.widget.CalendarView
import com.qcloud.qclib.base.BasePopupWindow
import com.qcloud.qclib.toast.QToast
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.ui.goods.presenter.impl.PurchaseDetailsPresenterImpl
import com.qcloud.suyuan.ui.goods.view.IPurchaseDetailsView
import com.qcloud.suyuan.widgets.customview.DatePickerButton
import com.qcloud.suyuan.widgets.dialog.InStorageDialog
import com.qcloud.suyuan.widgets.dialog.InputDialog
import com.qcloud.suyuan.widgets.pop.DropDownBtnPop
import kotlinx.android.synthetic.main.card_purchase_product_input.*

/**
 * Description: 确认入库
 * Author: gaobaiqiang
 * 2018/3/23 下午9:27.
 */
class PurchaseDetailsActivity: BaseActivity<IPurchaseDetailsView, PurchaseDetailsPresenterImpl>(), IPurchaseDetailsView, View.OnClickListener {
    private var id: String? = null

    private var suppliperPop: DropDownBtnPop? = null
    private var inputDialog: InputDialog? = null
    private var inStorageDialog: InStorageDialog? = null

    override val layoutId: Int
        get() = R.layout.activity_purchase_details

    override fun initPresenter(): PurchaseDetailsPresenterImpl? {
        return PurchaseDetailsPresenterImpl()
    }

    override fun initViewAndData() {
        id = intent.getStringExtra("ID")

        btn_in_storage_supplier.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
        btn_clear.setOnClickListener(this)
        initSupplierPop()
        initInputView()
    }

    /**
     * 初始化输入控件
     * */
    private fun initInputView() {
        btn_in_storage_birthday.onDateChangeListener = object : DatePickerButton.OnDateChangeListener {
            override fun onDateChange(year: Int, mouth: Int, day: Int, dateStr: String) {

            }
        }

        btn_in_storage_end_date.onDateChangeListener = object : DatePickerButton.OnDateChangeListener {
            override fun onDateChange(year: Int, mouth: Int, day: Int, dateStr: String) {

            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            mPresenter?.onBtnClick(v.id)
        }
    }

    override fun onAddSupplierClick() {
        suppliperPop?.showAsDropDown(btn_in_storage_supplier)
    }

    override fun onConfirmClick() {
        if (inStorageDialog == null) {
            inStorageDialog = InStorageDialog(this)
        }
        inStorageDialog?.show()
        inStorageDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                QToast.info(this@PurchaseDetailsActivity, R.string.tip_printing_bar_code, false)
            }
        }
    }

    override fun onClearClick() {

    }

    private fun initSupplierPop() {
        val list: MutableList<String> = ArrayList()
        list.add("条目1")
        list.add("条目2")
        list.add("条目3")
        list.add("条目4")
        list.add("条目5")
        btn_in_storage_supplier.post {
            val width = btn_in_storage_supplier.width
            suppliperPop = DropDownBtnPop(this, list, width)

            suppliperPop?.onItemClickListener = object : DropDownBtnPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: String) {
                    tv_in_storage_supplier.text = value
                }
            }
            suppliperPop?.onPopWindowViewClick = object : BasePopupWindow.OnPopWindowViewClick {
                override fun onViewClick(view: View) {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        suppliperPop.let {
            if (suppliperPop != null && suppliperPop!!.isShowing) {
                suppliperPop?.dismiss()
            }
        }

        inStorageDialog.let {
            if (inStorageDialog != null && inStorageDialog!!.isShowing) {
                inStorageDialog?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context, id: String) {
            val intent = Intent(context, PurchaseDetailsActivity::class.java)
            intent.putExtra("ID", id)
            context.startActivity(intent)
        }
    }
}