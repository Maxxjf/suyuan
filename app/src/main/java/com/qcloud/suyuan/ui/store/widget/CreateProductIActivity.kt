package com.qcloud.suyuan.ui.store.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.View
import android.widget.TextView
import com.haibin.calendarview.Calendar
import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.store.presenter.impl.CreateProductIPresenterImpl
import com.qcloud.suyuan.ui.store.view.ICreateProductIView
import com.qcloud.suyuan.widgets.dialog.DatePickerDialog
import com.qcloud.suyuan.widgets.dialog.InputDialog
import com.qcloud.suyuan.widgets.pop.DropDownPop
import kotlinx.android.synthetic.main.activity_create_product_i.*
import kotlinx.android.synthetic.main.layout_create_product_base_info.*
import kotlinx.android.synthetic.main.layout_create_product_details_info.*
import org.jetbrains.annotations.NotNull

/**
 * Description: 创建产品第一步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:56.
 */
class CreateProductIActivity: BaseActivity<ICreateProductIView, CreateProductIPresenterImpl>(), ICreateProductIView, View.OnClickListener {

    private var inputDialog: InputDialog? = null
    // 产品分类
    private var classifyPop: DropDownPop? = null
    // 生产厂家
    private var millPop: DropDownPop? = null
    // 开始时间
    private var startPicker: DatePickerDialog? = null
    // 结束时间
    private var endPicker: DatePickerDialog? = null

    // 条形码
    private var barCode: String? = null
    // 名称
    private var name: String? = null
    // 规格
    private var spec: String? = null
    // 地址
    private var address: String? = null
    // 登记证号
    private var registrationCode: String? = null
    // 开始日期
    private var startDate: String? = null
    // 结束日期
    private var endDate: String? = null
    // 生产许可证
    private var license: String? = null
    // 标准证号
    private var standardCode: String? = null
    // 产品介绍
    private var introduce: String? = null

    override val layoutId: Int
        get() = R.layout.activity_create_product_i

    override fun initPresenter(): CreateProductIPresenterImpl? {
        return CreateProductIPresenterImpl()
    }

    override fun initViewAndData() {
        initClassifyDropDown()
        initMillDropDown()
        et_product_bar_code.setOnClickListener(this)
        et_product_name.setOnClickListener(this)
        et_product_spec.setOnClickListener(this)
        et_mill_address.setOnClickListener(this)
        et_registration_code.setOnClickListener(this)
        et_production_license.setOnClickListener(this)
        et_product_standard.setOnClickListener(this)
        et_product_introduce.setOnClickListener(this)
        tv_registration_start.setOnClickListener(this)
        tv_registration_end.setOnClickListener(this)
        btn_next.setOnClickListener(this)
    }

    /**
     * 显示输入
     * */
    private fun showInput(@NotNull view: TextView?) {
        if (inputDialog == null) {
            inputDialog = InputDialog(this)
        }
        if (view != null) {
            inputDialog?.setBindView(view)
            inputDialog?.initInputHint(view.hint.toString().trim())
            inputDialog?.setInputValue(view.text.toString().trim())
        }

        inputDialog?.show()
    }

    /**
     * 初始化分类下拉弹窗
     * */
    private fun initClassifyDropDown() {
        val purchase = resources.getStringArray(R.array.purchase)
        val list: MutableList<String> = ArrayList()
        list.addAll(purchase)
        btn_select_product_classify.post {
            val width = btn_select_product_classify.width
            classifyPop = DropDownPop(this, list, width)

            classifyPop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: Any?) {
                    if (value != null) {
                        tv_product_classify.text = value.toString()
                    }
                }
            }
        }

        btn_select_product_classify.setOnClickListener {
            classifyPop?.showAsDropDown(btn_select_product_classify)
        }
    }

    /**
     * 初始化厂家下拉弹窗
     * */
    private fun initMillDropDown() {
        val purchase = resources.getStringArray(R.array.purchase)
        val list: MutableList<String> = ArrayList()
        list.addAll(purchase)
        btn_select_product_mill.post {
            val width = btn_select_product_mill.width
            millPop = DropDownPop(this, list, width)

            millPop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: Any?) {
                    if (value != null) {
                        tv_product_mill.text = value.toString()
                    }
                }
            }
        }

        btn_select_product_mill.setOnClickListener {
            millPop?.showAsDropDown(btn_select_product_mill)
        }
    }

    private fun initStartPicker() {
        startPicker = DatePickerDialog(this)
        startPicker?.onDateSelectListener = object :DatePickerDialog.OnDateSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun dateSelected(calendar: Calendar?) {
                if (calendar != null) {
                    tv_registration_start.text = "${calendar.year}-${calendar.month}-${calendar.day}"
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
                    tv_registration_end.text = "${calendar.year}-${calendar.month}-${calendar.day}"
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.et_product_bar_code, R.id.et_product_name, R.id.et_product_spec, R.id.et_mill_address,
                R.id.et_registration_code, R.id.et_production_license, R.id.et_product_standard, R.id.et_product_introduce -> {
                    showInput(v as TextView)
                }
                R.id.tv_registration_start -> {
                    if (startPicker == null) {
                        initStartPicker()
                    }
                    startPicker?.show()
                }
                R.id.tv_registration_end -> {
                    if (endPicker == null) {
                        initEndPicker()
                    }
                    endPicker?.show()
                }
                R.id.btn_next -> {
                    CreateProductIIActivity.openActivity(this)
//                    if (check()) {
//
//                    }
                }

            }
        }
    }

    private fun check(): Boolean {
        barCode = et_product_bar_code.text.toString()
        name = et_product_name.text.toString()
        spec = et_product_spec.text.toString()
        address = et_mill_address.text.toString()
        registrationCode = et_registration_code.text.toString()
        license = et_production_license.text.toString()
        standardCode = et_product_standard.text.toString()
        introduce = et_product_introduce.text.toString()
        startDate = tv_registration_start.text.toString()
        endDate = tv_registration_end.text.toString()

        if (StringUtil.isBlank(barCode)) {
            QToast.show(this, R.string.hint_input_product_bar_code)
            return false
        }
        if (StringUtil.isBlank(name)) {
            QToast.show(this, R.string.hint_input_product_name)
            return false
        }
        if (StringUtil.isBlank(spec)) {
            QToast.show(this, R.string.hint_input_product_spec)
            return false
        }
        if (StringUtil.isBlank(address)) {
            QToast.show(this, R.string.hint_input_mill_address)
            return false
        }
        if (StringUtil.isBlank(startDate)) {
            QToast.show(this, R.string.toast_select_registration_code_start)
            return false
        }
        if (StringUtil.isBlank(endDate)) {
            QToast.show(this, R.string.toast_select_registration_code_end)
            return false
        }
        if (DateUtil.compareTime(startDate, endDate, DateStyleEnum.YYYY_MM_DD.value) > 1) {
            QToast.show(this, R.string.toast_select_registration_code_error)
            return false
        }
        if (StringUtil.isBlank(registrationCode)) {
            QToast.show(this, R.string.hint_input_registration_code)
            return false
        }
        if (StringUtil.isBlank(license)) {
            QToast.show(this, R.string.hint_input_production_license)
            return false
        }
        if (StringUtil.isBlank(introduce)) {
            QToast.show(this, R.string.hint_input_product_introduce)
            return false
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        inputDialog.let {
            if (inputDialog != null && inputDialog!!.isShowing) {
                inputDialog?.dismiss()
            }
        }

        classifyPop.let {
            if (classifyPop != null && classifyPop!!.isShowing) {
                classifyPop?.dismiss()
            }
        }

        millPop.let {
            if (millPop != null && millPop!!.isShowing) {
                millPop?.dismiss()
            }
        }

        startPicker.let {
            if (startPicker != null && startPicker!!.isShowing) {
                startPicker?.dismiss()
            }
        }

        endPicker.let {
            if (endPicker != null && endPicker!!.isShowing) {
                endPicker?.dismiss()
            }
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, CreateProductIActivity::class.java))
        }
    }
}