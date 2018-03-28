package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.View
import android.widget.TextView
import com.qcloud.qclib.base.BasePopupWindow
import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.image.GlideUtil
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.InStorageBean
import com.qcloud.suyuan.beans.PrintBean
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.beans.SupplierBean
import com.qcloud.suyuan.ui.goods.presenter.impl.PurchaseDetailsPresenterImpl
import com.qcloud.suyuan.ui.goods.view.IPurchaseDetailsView
import com.qcloud.suyuan.utils.PrintHelper
import com.qcloud.suyuan.widgets.customview.DatePickerButton
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
    private var productBean: ProductBean? = null

    private var suppliperPop: DropDownBtnPop? = null
    private var inputDialog: InputDialog? = null
    private var inStorageDialog: InStorageDialog? = null
    private var number: Int = 0
    private var price: Double = 0.00
    private var birthday: String? = null
    private var endDay: String? = null
    private var currSupplier: SupplierBean? = null

    override val layoutId: Int
        get() = R.layout.activity_purchase_details

    override fun initPresenter(): PurchaseDetailsPresenterImpl? {
        return PurchaseDetailsPresenterImpl()
    }

    override fun initViewAndData() {
        productBean = intent.getSerializableExtra("PRODUCT") as ProductBean

        btn_in_storage_supplier.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
        btn_clear.setOnClickListener(this)
        tv_in_storage_number.setOnClickListener(this)
        tv_in_storage_price.setOnClickListener(this)

        initInputView()

        refreshData()
        mPresenter?.loadSupplier()
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

    override fun onClick(v: View?) {
        if (v != null) {
            mPresenter?.onBtnClick(v.id)
        }
    }

    override fun onAddSupplierClick() {
        suppliperPop?.showAsDropDown(btn_in_storage_supplier)
    }

    override fun onConfirmClick() {
        if (check()) {
            mPresenter?.save(productBean!!.id ?: "0", number, price, birthday!!, endDay!!, currSupplier!!.id)
        }
    }

    override fun onClearClick() {
        if (isRunning) {
            tv_in_storage_number.text = "0"
            tv_in_storage_price.text = "0.00"
            btn_in_storage_birthday.text = ""
            btn_in_storage_end_date.text = ""
            tv_in_storage_supplier.text = ""
        }
    }

    override fun onStockNumberClick() {
        if (isRunning) {
            showInput(tv_in_storage_number)
        }
    }

    override fun onPriceClick() {
        if (isRunning) {
            showInput(tv_in_storage_price)
        }
    }

    override fun showInput(view: TextView) {
        if (inputDialog == null) {
            inputDialog = InputDialog(this)
        }
        inputDialog?.setBindView(view)
        inputDialog?.setInputValue(view.text.toString().trim())

        inputDialog?.show()
    }

    override fun replaceSupplierList(beans: List<SupplierBean>) {
        if (isRunning) {
            btn_in_storage_supplier.post {
                val width = btn_in_storage_supplier.width
                suppliperPop = DropDownBtnPop(this, beans, width)

                suppliperPop?.onItemClickListener = object : DropDownBtnPop.OnItemClickListener {
                    override fun onItemClick(position: Int, value: Any?) {
                        if (value != null) {
                            currSupplier = value as SupplierBean
                            tv_in_storage_supplier.text = currSupplier!!.name
                        }
                    }
                }
                suppliperPop?.onPopWindowViewClick = object : BasePopupWindow.OnPopWindowViewClick {
                    override fun onViewClick(view: View) {
                        QToast.show(this@PurchaseDetailsActivity, "跳转到新增，功能未开发，等等马上好")
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
        if (DateUtil.compareTime(birthday, endDay, DateStyleEnum.YYYY_MM_DD.value) > 1) {
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

        inputDialog.let {
            if (inputDialog != null && inputDialog!!.isShowing) {
                inputDialog?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context, product: ProductBean) {
            val intent = Intent(context, PurchaseDetailsActivity::class.java)
            intent.putExtra("PRODUCT", product)
            context.startActivity(intent)
        }
    }
}