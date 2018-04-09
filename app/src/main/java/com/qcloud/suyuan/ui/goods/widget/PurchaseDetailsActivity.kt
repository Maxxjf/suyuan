package com.qcloud.suyuan.ui.goods.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.text.InputType
import android.view.View
import android.widget.TextView
import com.haibin.calendarview.Calendar
import com.qcloud.qclib.base.BasePopupWindow
import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.*
import com.qcloud.suyuan.ui.goods.presenter.impl.PurchaseDetailsPresenterImpl
import com.qcloud.suyuan.ui.goods.view.IPurchaseDetailsView
import com.qcloud.suyuan.ui.store.widget.MySupplierActivity
import com.qcloud.suyuan.utils.PrintHelper
import com.qcloud.suyuan.widgets.dialog.DatePickerDialog
import com.qcloud.suyuan.widgets.dialog.InStorageDialog
import com.qcloud.suyuan.widgets.dialog.InputDialog
import com.qcloud.suyuan.widgets.pop.DropDownBtnPop
import kotlinx.android.synthetic.main.card_purchase_product_details.*
import kotlinx.android.synthetic.main.card_purchase_product_input.*
import timber.log.Timber

/**
 * Description: 确认入库
 * Author: gaobaiqiang
 * 2018/3/23 下午9:27.
 */
class PurchaseDetailsActivity: BaseActivity<IPurchaseDetailsView, PurchaseDetailsPresenterImpl>(), IPurchaseDetailsView, View.OnClickListener {
    private var productBean: PurchaseProductBean? = null

    private var supplierPop: DropDownBtnPop? = null
    private var inputDialog: InputDialog? = null
    private var inStorageDialog: InStorageDialog? = null
    private var number: Int = 0
    private var price: Double = 0.00
    private var birthday: String? = null
    private var endDay: String? = null
    private var currSupplier: SupplierBean? = null
    // 生产时间
    private var birthdayPicker: DatePickerDialog? = null
    // 结束时间
    private var endPicker: DatePickerDialog? = null

    override val layoutId: Int
        get() = R.layout.activity_purchase_details

    override fun initPresenter(): PurchaseDetailsPresenterImpl? {
        return PurchaseDetailsPresenterImpl()
    }

    override fun initViewAndData() {
        productBean = intent.getSerializableExtra("PRODUCT") as PurchaseProductBean

        btn_in_storage_supplier.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
        btn_clear.setOnClickListener(this)
        tv_in_storage_number.setOnClickListener(this)
        tv_in_storage_price.setOnClickListener(this)
        btn_in_storage_birthday.setOnClickListener(this)
        btn_in_storage_end_date.setOnClickListener(this)

        initInputView()

        refreshData()
        mPresenter?.loadSupplier()
    }

    /**
     * 初始化输入控件
     * */
    private fun initInputView() {

    }

    private fun refreshData() {
        if (productBean != null) {
            with(productBean!!) {
                GlideUtil.loadImage(this@PurchaseDetailsActivity, img_product, image, R.drawable.bmp_product)
                tv_product_name.text = name
                tv_product_spec.text = specification
                tv_product_classify.text = classifyName
                tv_product_unit.text = unit
                tv_product_manufacturer.text = millName
                tv_product_curr_stock.text = stockStr
            }
        }
    }

    private fun initBirthdayPicker() {
        birthdayPicker = DatePickerDialog(this)
        birthdayPicker?.onDateSelectListener = object :DatePickerDialog.OnDateSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun dateSelected(calendar: Calendar?) {
                if (calendar != null) {
                    btn_in_storage_birthday.text = "${calendar.year}-${calendar.month}-${calendar.day}"
                }
            }
        }
    }

    private fun initEndPicker() {
        endPicker = DatePickerDialog(this)
        endPicker?.onDateSelectListener = object :DatePickerDialog.OnDateSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun dateSelected(calendar: Calendar?) {
                if (calendar != null) {
                    btn_in_storage_end_date.text = "${calendar.year}-${calendar.month}-${calendar.day}"
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            mPresenter?.onBtnClick(v.id)
        }
    }

    override fun onAddSupplierClick() {
        supplierPop?.showAsDropDown(btn_in_storage_supplier)
    }

    override fun onConfirmClick() {
        if (check()) {
            mPresenter?.save(productBean!!.id ?: "0", number, price, birthday!!, endDay!!, currSupplier!!.id)
        }
    }

    override fun onClearClick() {
        if (isRunning) {
            tv_in_storage_number.text = ""
            tv_in_storage_price.text = ""
            btn_in_storage_birthday.text = ""
            btn_in_storage_end_date.text = ""
            tv_in_storage_supplier.text = ""

            number = 0
            price = 0.00
            birthday = null
            endDay = null
            currSupplier = null
        }
    }

    override fun onStockNumberClick() {
        if (isRunning) {
            showInput(tv_in_storage_number, 0)
        }
    }

    override fun onPriceClick() {
        if (isRunning) {
            showInput(tv_in_storage_price, 1)
        }
    }

    override fun onBirthdayClick() {
        if (birthdayPicker == null) {
            initBirthdayPicker()
        }
        birthdayPicker?.show()
    }

    override fun onEndDateClick() {
        if (endPicker == null) {
            initEndPicker()
        }
        endPicker?.show()
    }

    override fun showInput(view: TextView, type: Int) {
        if (inputDialog == null) {
            inputDialog = InputDialog(this)
        }
        if (type == 1) {
            // 价格
            inputDialog?.setInputPrice()
        } else {
            // 数量
            inputDialog?.setInputMethod(InputType.TYPE_CLASS_NUMBER)
        }
        inputDialog?.setBindView(view)
        inputDialog?.setInputValue(view.text.toString().trim())

        inputDialog?.show()
    }

    override fun replaceSupplierList(beans: List<SupplierBean>) {
        if (isRunning) {
            if (beans.size > 1) {
                currSupplier = beans[0]
                tv_in_storage_supplier.text = currSupplier!!.name
            }
            btn_in_storage_supplier.post {
                val width = btn_in_storage_supplier.width
                supplierPop = DropDownBtnPop(this, beans, width)

                supplierPop?.onItemClickListener = object : DropDownBtnPop.OnItemClickListener {
                    override fun onItemClick(position: Int, value: Any?) {
                        if (value != null) {
                            currSupplier = value as SupplierBean
                            tv_in_storage_supplier.text = currSupplier!!.name
                        }
                    }
                }
                supplierPop?.onPopWindowViewClick = object : BasePopupWindow.OnPopWindowViewClick {
                    override fun onViewClick(view: View) {
                        MySupplierActivity.openActivity(this@PurchaseDetailsActivity)
                    }
                }
            }
        }
    }

    override fun saveSuccess(bean: InStorageBean) {
        if (isRunning) {
            if (inStorageDialog == null) {
                inStorageDialog = InStorageDialog(this)
            }
            inStorageDialog?.refreshData(productBean!!, bean.date, bean.batchNum)
            inStorageDialog?.show()
            inStorageDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
                override fun onBtnClick(view: View) {
                    val printBean = PrintBean()
                    printBean.type = 1
                    printBean.barCode = bean.batchNum
                    PrintHelper.instance.printData(printBean)
                }
            }
            onClearClick()
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

    private fun check(): Boolean {
        val numberStr = tv_in_storage_number.text.toString()
        val priceStr = tv_in_storage_price.text.toString()
        birthday = btn_in_storage_birthday.text.toString()
        endDay = btn_in_storage_end_date.text.toString()
        if (productBean == null) {
            QToast.show(this, R.string.toast_product_is_empty)
            return false
        }
        if (!StringUtil.isNumberStr(numberStr) || numberStr.toInt() <= 0) {
            QToast.show(this, R.string.toast_input_in_storage_number)
            return false
        }
        if (!StringUtil.isMoneyStr(priceStr) || priceStr.toDouble() <= 0.00) {
            QToast.show(this, R.string.toast_input_in_storage_price)
            return false
        }
        if (StringUtil.isBlank(birthday)) {
            QToast.show(this, R.string.toast_select_product_birthday)
            return false
        }
        if (StringUtil.isBlank(endDay)) {
            QToast.show(this, R.string.toast_select_product_end_day)
            return false
        }
        if (DateUtil.compareTime(birthday, endDay, DateStyleEnum.YYYY_MM_DD.value) >= 0) {
            QToast.show(this, R.string.toast_end_day_min_birthday)
            return false
        }
        if (currSupplier == null) {
            QToast.show(this, R.string.toast_select_in_storage_supplier)
            return false
        }
        number = numberStr.toInt()
        price = priceStr.toDouble()

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        supplierPop.let {
            if (supplierPop != null && supplierPop!!.isShowing) {
                supplierPop?.dismiss()
            }
        }

        inStorageDialog.let {
            if (inStorageDialog != null && inStorageDialog!!.isShowing) {
                inStorageDialog?.dismiss()
            }
        }

        inputDialog.let {
            if (inputDialog != null && inputDialog!!.isShowing) {
                inputDialog?.dismiss()
            }
        }

         birthdayPicker.let {
            if (birthdayPicker != null && birthdayPicker!!.isShowing) {
                birthdayPicker?.dismiss()
            }
        }

        endPicker.let {
            if (endPicker != null && endPicker!!.isShowing) {
                endPicker?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context, product: PurchaseProductBean) {
            val intent = Intent(context, PurchaseDetailsActivity::class.java)
            intent.putExtra("PRODUCT", product)
            context.startActivity(intent)
        }
    }
}