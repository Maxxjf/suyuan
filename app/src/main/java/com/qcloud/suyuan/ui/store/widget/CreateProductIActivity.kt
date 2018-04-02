package com.qcloud.suyuan.ui.store.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import com.haibin.calendarview.Calendar
import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.CreateProductBean
import com.qcloud.suyuan.beans.CreateProductSubmitBean
import com.qcloud.suyuan.beans.ProductClassifyBean
import com.qcloud.suyuan.beans.ProductMillBean
import com.qcloud.suyuan.realm.RealmHelper
import com.qcloud.suyuan.ui.store.presenter.impl.CreateProductIPresenterImpl
import com.qcloud.suyuan.ui.store.view.ICreateProductIView
import com.qcloud.suyuan.utils.UserInfoUtil
import com.qcloud.suyuan.widgets.dialog.DatePickerDialog
import com.qcloud.suyuan.widgets.dialog.InputDialog
import com.qcloud.suyuan.widgets.dialog.OperationTipDialog
import com.qcloud.suyuan.widgets.pop.DropDownPop
import com.qcloud.suyuan.widgets.toolbar.CustomToolbar
import kotlinx.android.synthetic.main.activity_create_product_i.*
import kotlinx.android.synthetic.main.layout_create_product_base_info.*
import kotlinx.android.synthetic.main.layout_create_product_details_info.*
import org.jetbrains.annotations.NotNull
import timber.log.Timber

/**
 * Description: 创建产品第一步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:56.
 */
class CreateProductIActivity: BaseActivity<ICreateProductIView, CreateProductIPresenterImpl>(), ICreateProductIView, View.OnClickListener {

    private var currId: String? = null

    private var saveDialog: OperationTipDialog? = null
    private var editOldDialog: OperationTipDialog? = null
    private var inputDialog: InputDialog? = null
    // 产品分类
    private var classifyPop: DropDownPop? = null
    // 生产厂家
    private var millPop: DropDownPop? = null
    // 开始时间
    private var startPicker: DatePickerDialog? = null
    // 结束时间
    private var endPicker: DatePickerDialog? = null

    private var barCode: String = ""            // 条形码
    private var name: String = ""               // 名称
    private var classifyId: String = ""         // 分类id
    private var millId: String = ""             // 产品厂家id
    private var spec: String = ""               // 规格
    private var unit: String = ""               // 单位
    private var address: String? = null         // 地址
    private var registrationCode: String = ""   // 登记证号
    private var startDate: String = ""          // 开始日期
    private var endDate: String = ""            // 结束日期
    private var license: String = ""            // 生产许可证
    private var standardCode: String = ""       // 标准证号
    private var introduce: String? = null       // 产品介绍

    // 用来提交产品
    private var createProduct = CreateProductSubmitBean()

    override val layoutId: Int
        get() = R.layout.activity_create_product_i

    override fun initPresenter(): CreateProductIPresenterImpl? {
        return CreateProductIPresenterImpl()
    }

    override fun initViewAndData() {
        currId = intent.getStringExtra("ID")

        initView()
        startLoadingDialog()
        mPresenter?.loadProduct(currId)

        showOldProductTip()
    }

    private fun initView() {
        toolbar.onBtnClickListener = object : CustomToolbar.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                if (view.id == R.id.btn_back) {
                    showSaveTip()
                }
            }
        }
        et_product_bar_code.setOnClickListener(this)
        et_product_name.setOnClickListener(this)
        et_product_spec.setOnClickListener(this)
        et_product_unit.setOnClickListener(this)
        et_registration_code.setOnClickListener(this)
        et_production_license.setOnClickListener(this)
        et_product_standard.setOnClickListener(this)
        et_product_introduce.setOnClickListener(this)
        tv_registration_start.setOnClickListener(this)
        tv_registration_end.setOnClickListener(this)
        btn_next.setOnClickListener(this)
    }

    /**
     * 显示上次未提交的产品信息
     * */
    private fun showOldProductTip() {
        if (StringUtil.isBlank(currId) || StringUtil.isEquals(currId, "0") || StringUtil.isEquals(currId, "-1")) {
            val createUser = UserInfoUtil.getUser()
            if (createUser != null) {
                val submitBean: CreateProductSubmitBean? =
                        RealmHelper.instance.queryBeanById(CreateProductSubmitBean::class.java, "createUserId", createUser.id)
                if (submitBean != null) {
                    if (editOldDialog == null) {
                        editOldDialog = OperationTipDialog(this)
                    }
                    editOldDialog?.setTip(R.string.tip_edit_old)
                    editOldDialog?.setCancelBtn(R.string.tip_edit_cancel)
                    editOldDialog?.setConfirmBtn(R.string.tip_edit_ok)
                    editOldDialog?.show()
                    editOldDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
                        override fun onBtnClick(view: View) {
                            if (view.id == R.id.btn_ok) {
                                RealmHelper.instance.deleteBeanById(CreateProductSubmitBean::class.java, "createUserId", createUser.id)
                            } else {
                                refreshOldData(submitBean)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 刷新上次保存的数据
     * */
    private fun refreshOldData(submitBean: CreateProductSubmitBean) {
        createProduct = CreateProductSubmitBean()
        with(submitBean) {
            et_product_bar_code.text = barCode
            et_product_name.text = name
            et_product_spec.text = specification
            et_product_unit.text = unit
            tv_product_classify.text = classifyName
            tv_product_mill.text = millName
            et_mill_address.text = millAddress
            et_registration_code.text = registerCard
            et_production_license.text = licenseCard
            et_product_standard.text = standardCard
            et_product_introduce.text = details
            tv_registration_start.text = startTime
            tv_registration_end.text = endTime

            // 保存之前的数据
            createProduct.createUserId = createUserId
            createProduct.goodsId = goodsId
            createProduct.barCode = barCode
            createProduct.classifyId = classifyId
            createProduct.classifyName = classifyName
            createProduct.millId = millId
            createProduct.millName = millName
            createProduct.millAddress = millAddress
            createProduct.infoId = infoId
            createProduct.name = name
            createProduct.image = image
            createProduct.specification = specification
            createProduct.unit = unit
            createProduct.registerCard = registerCard
            createProduct.startTime = startTime
            createProduct.endTime = endTime
            createProduct.licenseCard = licenseCard
            createProduct.standardCard = standardCard
            createProduct.details = details
            createProduct.remark = remark
            createProduct.attrId = attrId
            createProduct.attrValues = attrValues
        }
        // 加载厂家列表
        startLoadingDialog()
        classifyId = submitBean.classifyId
        millId = submitBean.millId
        mPresenter?.loadFactory(classifyId)
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
    private fun initClassifyDropDown(list: List<ProductClassifyBean>) {
        classifyId = ""
        btn_select_product_classify.post {
            val width = btn_select_product_classify.width
            classifyPop = DropDownPop(this, list, width)

            classifyPop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: Any?) {
                    if (value != null) {
                        val classifyBean: ProductClassifyBean? = value as ProductClassifyBean
                        if (classifyBean != null) {
                            startLoadingDialog()
                            tv_product_classify.text = classifyBean.name

                            classifyId = classifyBean.id
                            mPresenter?.loadFactory(classifyId)
                        }
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
    private fun initMillDropDown(list: List<ProductMillBean>) {
        btn_select_product_mill.post {
            val width = btn_select_product_mill.width
            millPop = DropDownPop(this, list, width)

            millPop?.onItemClickListener = object : DropDownPop.OnItemClickListener {
                override fun onItemClick(position: Int, value: Any?) {
                    if (value != null) {
                        val millBean: ProductMillBean? = value as ProductMillBean
                        if (millBean != null) {
                            millId = millBean.id ?: ""
                            tv_product_mill.text = millBean.name
                            et_mill_address.text = millBean.address
                        }
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
                R.id.et_product_bar_code, R.id.et_product_name, R.id.et_product_spec,
                R.id.et_registration_code, R.id.et_production_license, R.id.et_product_standard,
                R.id.et_product_introduce, R.id.et_product_unit -> {
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
                    if (check()) {
                        CreateProductIIActivity.openActivity(this, createProduct)
                    }
                }
            }
        }
    }

    override fun refreshData(bean: CreateProductBean) {
        if (isRunning) {
            stopLoadingDialog()

            val goodsBean = bean.goods
            if (goodsBean != null) {
                with(goodsBean) {
                    et_product_bar_code.text = barCode
                    tv_product_classify.text = classifyId
                }
                classifyId = goodsBean.classifyId ?: ""
                millId = goodsBean.millId ?: ""

                // 加载厂家列表
                startLoadingDialog()
                mPresenter?.loadFactory(classifyId)
            }
            val infoBean = bean.info
            if (infoBean != null) {
                with(infoBean) {
                    et_product_name.text = name
                    et_product_spec.text = specification
                    et_product_unit.text = unit
                    et_registration_code.text = registerCard
                    et_production_license.text = licenseCard
                    et_product_standard.text = standardCard
                    et_product_introduce.text = details
                    tv_registration_start.text = startTime
                    tv_registration_end.text = endTime
                }
            }

            // TODO
            tv_product_mill.text = ""
            et_mill_address.text = ""
        }
    }

    override fun replaceClassify(list: List<ProductClassifyBean>) {
        if (isRunning) {
            initClassifyDropDown(list)
        }
    }

    override fun replaceMill(list: List<ProductMillBean>) {
        if (isRunning) {
            stopLoadingDialog()
            initMillDropDown(list)
        }
    }

    override fun loadErr(errMsg: String, isShow: Boolean) {
        if (isRunning) {
            stopLoadingDialog()
            if (isShow) {
                QToast.show(this, errMsg)
            } else {
                Timber.e(errMsg)
            }
        }
    }

    private fun check(): Boolean {
        barCode = et_product_bar_code.text.toString()
        name = et_product_name.text.toString()
        spec = et_product_spec.text.toString()
        unit = et_product_unit.text.toString()
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
        if (StringUtil.isBlank(classifyId)) {
            QToast.show(this, R.string.hint_select_product_classify)
            return false
        }
        if (StringUtil.isBlank(spec)) {
            QToast.show(this, R.string.hint_input_product_spec)
            return false
        }
        if (StringUtil.isBlank(unit)) {
            QToast.show(this, R.string.hint_input_product_unit)
            return false
        }
        if (StringUtil.isBlank(millId)) {
            QToast.show(this, R.string.hint_select_product_mill)
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
//        if (StringUtil.isBlank(introduce)) {
//            QToast.show(this, R.string.hint_input_product_introduce)
//            return false
//        }
        createProduct.goodsId = currId ?: ""
        createProduct.barCode = barCode
        createProduct.name = name
        createProduct.classifyId = classifyId
        createProduct.classifyName = tv_product_classify.text.toString()
        createProduct.millId = millId
        createProduct.millName = tv_product_mill.text.toString()
        createProduct.millAddress = et_mill_address.text.toString()
        createProduct.name = name
        createProduct.specification = spec
        createProduct.unit = unit
        createProduct.registerCard = registrationCode
        createProduct.startTime = startDate
        createProduct.endTime = endDate
        createProduct.licenseCard = license
        createProduct.standardCard = standardCode
        createProduct.details = introduce ?: ""

        return true
    }

    private fun showSaveTip() {
        if (saveDialog == null) {
            saveDialog = OperationTipDialog(this)
        }
        saveDialog?.setTip(R.string.tip_save_product)
        saveDialog?.setCancelBtn(R.string.tip_save_cancel)
        saveDialog?.setConfirmBtn(R.string.tip_save_ok)
        saveDialog?.show()
        saveDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                if (view.id == R.id.btn_ok) {
                    val createUser = UserInfoUtil.getUser()
                    if (createUser != null) {
                        createProduct.createUserId = createUser.id
                    }
                    RealmHelper.instance.addOrUpdateBean(createProduct)
                    saveDialog?.dismiss()
                    finish()
                } else {
                    saveDialog?.dismiss()
                    finish()
                }
            }
        }
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
        editOldDialog.let {
            if (editOldDialog != null && editOldDialog!!.isShowing) {
                editOldDialog?.dismiss()
            }
        }
        saveDialog.let {
            if (saveDialog != null && saveDialog!!.isShowing) {
                saveDialog?.dismiss()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            showSaveTip()
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        fun openActivity(@NonNull context: Context, id: String?) {
            val intent = Intent(context, CreateProductIActivity::class.java)
            intent.putExtra("ID", id)
            context.startActivity(intent)
        }
    }
}