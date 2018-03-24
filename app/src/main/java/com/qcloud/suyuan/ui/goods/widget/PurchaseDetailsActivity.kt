package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.view.View
import com.qcloud.qclib.base.BasePopupWindow
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.goods.presenter.impl.PurchaseDetailsPresenterImpl
import com.qcloud.suyuan.ui.goods.view.IPurchaseDetailsView
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

    }

    override fun onClearClick() {

    }

    private fun initSupplierPop() {
        val list: MutableList<String> = ArrayList()
        list.add("病虫防治1")
        list.add("病虫防治2")
        list.add("病虫防治3")
        list.add("病虫防治4")
        list.add("病虫防治5")
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
    }

    companion object {
        fun openActivity(@NonNull context: Context, id: String) {
            val intent = Intent(context, PurchaseDetailsActivity::class.java)
            intent.putExtra("ID", id)
            context.startActivity(intent)
        }
    }
}