package com.qcloud.suyuan.widgets.customview

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.base.BaseLinearLayout
import com.qcloud.qclib.toast.QToast
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.ProductComponentAdapter
import com.qcloud.suyuan.beans.ProductAttrBean
import com.qcloud.suyuan.beans.ProductComponentBean
import com.qcloud.suyuan.widgets.dialog.InputProductComponentDialog
import kotlinx.android.synthetic.main.layout_of_product_attr_form.view.*

/**
 * Description: 产品属性值表格
 * Author: gaobaiqiang
 * 2018/3/30 下午4:47.
 */
class AttrFormView  @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : BaseLinearLayout(context, attrs, defStyleAttr) {

    private var mAdapter: ProductComponentAdapter? = null
    private var inputComponentDialog: InputProductComponentDialog? = null

    private var currBean: ProductAttrBean? = null

    override val viewId: Int
        get() = R.layout.layout_of_product_attr_form

    override fun initViewAndData() {
        initRecyclerView()

        btn_add.setOnClickListener {
            if (inputComponentDialog == null) {
                inputComponentDialog = InputProductComponentDialog(mContext)
            }
            inputComponentDialog?.show()
        }
    }

    private fun initRecyclerView() {
        list_value.layoutManager = LinearLayoutManager(mContext)
        mAdapter = ProductComponentAdapter(mContext)
        list_value.adapter = mAdapter

        mAdapter?.onHolderClick = object : CommonRecyclerAdapter.OnHolderClickListener<ProductComponentBean> {
            override fun onHolderClick(view: View, t: ProductComponentBean, position: Int) {
                QToast.show(mContext, "删除属性")
            }
        }
    }

    /**
     * 初始化数据
     * */
    private fun initData() {
        if (currBean != null) {
            val attrName = currBean!!.attributeName
            if (attrName != null) {
                with(attrName) {
                    tv_required.visibility = if (isCrux == 1) View.VISIBLE else View.GONE
                }
            }
        }
    }

    fun refreshData(bean: ProductAttrBean) {
        currBean = bean
        initData()
    }
}