package com.qcloud.suyuan.ui.store.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.KeyEvent
import android.view.View
import com.qcloud.qclib.AppManager
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.qclib.widget.layoutManager.FullyLinearLayoutManager
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.CreateProductAttrAdapter
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.CreateProductSubmitBean
import com.qcloud.suyuan.beans.ProductAttrBean
import com.qcloud.suyuan.realm.RealmHelper
import com.qcloud.suyuan.ui.store.presenter.impl.CreateProductIIPresenterImpl
import com.qcloud.suyuan.ui.store.view.ICreateProductIIView
import com.qcloud.suyuan.utils.ProductUtil
import com.qcloud.suyuan.utils.UserInfoUtil
import com.qcloud.suyuan.widgets.dialog.OperationTipDialog
import com.qcloud.suyuan.widgets.dialog.TipDialog
import com.qcloud.suyuan.widgets.toolbar.CustomToolbar
import kotlinx.android.synthetic.main.activity_create_product_ii.*
import timber.log.Timber

/**
 * Description: 创建产品第二步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:59.
 */
class CreateProductIIActivity: BaseActivity<ICreateProductIIView, CreateProductIIPresenterImpl>(), ICreateProductIIView {
    private var tipDialog: TipDialog? = null
    private var saveDialog: OperationTipDialog? = null
    private var quickDialog: OperationTipDialog? = null

    // 是否编辑本地
    private var isLocal = false
    // 是否编辑
    private var isEdit = false

    private var mAdapter: CreateProductAttrAdapter? = null

    // 用来提交产品
    private var createProduct = CreateProductSubmitBean()

    override val layoutId: Int
        get() = R.layout.activity_create_product_ii

    override fun initPresenter(): CreateProductIIPresenterImpl? {
        return CreateProductIIPresenterImpl()
    }

    override fun initViewAndData() {
        createProduct = intent.getSerializableExtra("SUBMIT") as CreateProductSubmitBean
        isEdit = intent.getBooleanExtra("IS_EDIT", false)
        isLocal = intent.getBooleanExtra("IS_LOCAL", false)

        initView()
        initAttrList()
        // 加载数据
        mPresenter?.createProductNext(createProduct.goodsId, createProduct.classifyId)
    }

    private fun initView() {
        // 返回按钮
        toolbar.onBtnClickListener = object : CustomToolbar.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                if (view.id == R.id.btn_back) {
                    if (isEdit) {
                        showQuickTip()
                    } else {
                        showSaveTip()
                    }
                }
            }
        }
        btn_confirm_create.setText(if (isEdit) R.string.btn_confirm_edit else R.string.btn_confirm_create)

        // 提交数据
        btn_confirm_create.setOnClickListener {
            if (check()) {
                mPresenter?.add(createProduct)
            }
        }
    }

    private fun initAttrList() {
        list_arrts.layoutManager = FullyLinearLayoutManager(this)

        mAdapter = CreateProductAttrAdapter(this)
        list_arrts?.adapter = mAdapter
    }

    private fun initSuccessTip() {
        tipDialog = TipDialog(this)
        tipDialog?.setTip(if (isEdit) R.string.tip_product_edit_success else R.string.tip_product_create_success)
        tipDialog?.setConfirmBtn(R.string.btn_i_know)
        tipDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                val activity = AppManager.instance.getActivity(CreateProductIActivity::class.java)
                activity?.finish()
                finish()
            }
        }
    }

    override fun replaceList(beans: List<ProductAttrBean>?) {
        if (isRunning) {
            if (beans != null) {
                if (isLocal) {
                    mAdapter?.replaceList(ProductUtil.disposeRealmData(beans))
                } else {
                    mAdapter?.replaceList(beans)
                }
            }
        }
    }

    override fun createSuccess() {
        if (isRunning) {
            if (tipDialog == null) {
                initSuccessTip()
            }
            tipDialog?.show()
            // 删除本地数据
            val createUser = UserInfoUtil.getUser()
            if (createUser != null) {
                RealmHelper.instance.deleteBeanById(CreateProductSubmitBean::class.java, "createUserId", createUser.id)
            }
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
        if (mAdapter == null || mAdapter!!.mList.isEmpty()) {
            return true
        }

        val idList: MutableList<String> = ArrayList()
        val valueList: MutableList<String> = ArrayList()
        for (bean in mAdapter!!.mList) {
            val attrName = bean.attributeName
            if (attrName != null) {
                if (attrName.isCrux == 1 && StringUtil.isBlank(bean.attrValueSubmitStr)) {
                    QToast.show(this, "请输入${attrName.name}")
                    return false
                }
                idList.add(attrName.id ?: "")
                valueList.add(bean.attrValueSubmitStr ?: "")
            }
        }
        createProduct.attrId = StringUtil.combineList(idList, ",")
        createProduct.attrValues = StringUtil.combineList(valueList, ",")
        Timber.e("submit = $createProduct")

        return true
    }

    private fun showQuickTip() {
        if (quickDialog == null) {
            quickDialog = OperationTipDialog(this)
        }
        quickDialog?.setTip(R.string.tip_quick_product)
        quickDialog?.setCancelBtn(R.string.tip_quick_cancel)
        quickDialog?.setConfirmBtn(R.string.tip_quick_ok)
        quickDialog?.show()
        quickDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                if (view.id == R.id.btn_cancel) {
                    val activity = AppManager.instance.getActivity(CreateProductIActivity::class.java)
                    activity?.finish()
                    saveDialog?.dismiss()
                    finish()
                }
            }
        }
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
                val createUser = UserInfoUtil.getUser()
                if (view.id == R.id.btn_ok) {
                    saveAttrValue()
                    if (createUser != null) {
                        createProduct.createUserId = createUser.id
                    }
                    RealmHelper.instance.addOrUpdateBean(createProduct)
                } else {
                    // 删除本地数据
                    if (createUser != null) {
                        RealmHelper.instance.deleteBeanById(CreateProductSubmitBean::class.java, "createUserId", createUser.id)
                    }
                }
                val activity = AppManager.instance.getActivity(CreateProductIActivity::class.java)
                activity?.finish()
                saveDialog?.dismiss()
                finish()
            }
        }
    }

    /**
     * 保存数据到本地
     * */
    private fun saveAttrValue() {
        if (mAdapter == null || mAdapter!!.mList.isEmpty()) {
            return
        }
        val idList: MutableList<String> = ArrayList()
        val valueList: MutableList<String> = ArrayList()
        for (bean in mAdapter!!.mList) {
            val attrName = bean.attributeName
            if (attrName != null) {
                idList.add(attrName.id ?: "")
                valueList.add(bean.attrValueSubmitStr ?: "")
            }
        }
        createProduct.attrId = StringUtil.combineList(idList, ",")
        createProduct.attrValues = StringUtil.combineList(valueList, ",")

        Timber.e("submit = $createProduct")
    }

    override fun onDestroy() {
        super.onDestroy()
        tipDialog.let {
            if (tipDialog != null && tipDialog!!.isShowing) {
                tipDialog?.dismiss()
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
            if (isEdit) {
                showQuickTip()
            } else {
                showSaveTip()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        /**
         * @param bean 提交的内容
         * @param isEdit 是否编辑
         * @param isLocal 是否缓存的数据
         * */
        fun openActivity(@NonNull context: Context, bean: CreateProductSubmitBean, isEdit: Boolean, isLocal: Boolean) {
            val intent = Intent(context, CreateProductIIActivity::class.java)
            intent.putExtra("SUBMIT", bean)
            intent.putExtra("IS_EDIT", isEdit)
            intent.putExtra("IS_LOCAL", isLocal)
            context.startActivity(intent)
        }
    }
}