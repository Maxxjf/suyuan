package com.qcloud.suyuan.widgets.customview

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter
import com.qcloud.qclib.base.BaseLinearLayout
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.ProductComponentAdapter
import com.qcloud.suyuan.adapters.ProductComponentHeadAdapter
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.ProductAttrBean
import com.qcloud.suyuan.beans.ProductComponentBean
import com.qcloud.suyuan.utils.ProductUtil
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

    private var mHeadAdapter: ProductComponentHeadAdapter? = null
    private var mAdapter: ProductComponentAdapter? = null
    private var inputComponentDialog: InputProductComponentDialog? = null

    private var currBean: ProductAttrBean? = null

    override val viewId: Int
        get() = R.layout.layout_of_product_attr_form

    override fun initViewAndData() {
        btn_add.setOnClickListener {
            if (inputComponentDialog == null) {
                initInputDialog()
            }
            inputComponentDialog?.show()
        }
    }

    private fun initInputDialog() {
        inputComponentDialog = InputProductComponentDialog(mContext)
        inputComponentDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                val inputValue = inputComponentDialog!!.attrValue
                if (StringUtil.isNotBlank(inputValue)) {
                    mAdapter?.replaceList(ProductUtil.productAttrStr2List(inputValue))
                    if (mAdapter != null) {
                        currBean?.attrValueSubmitStr = ProductUtil.productAttrStr2Submit(mAdapter!!.mList)
                    }
                }
            }
        }
    }

    private fun initHeadRecyclerView(size: Int, list: List<String>) {
        list_head.post {
            val width = list_head.width
            list_head.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            mHeadAdapter = ProductComponentHeadAdapter(mContext, size, width)
            list_head.adapter = mHeadAdapter
            mHeadAdapter?.replaceList(list)
        }
    }

    private fun initRecyclerView(size: Int) {
        list_value.layoutManager = LinearLayoutManager(mContext)
        mAdapter = ProductComponentAdapter(mContext, size)
        list_value.adapter = mAdapter

        mAdapter?.onHolderClick = object : CommonRecyclerAdapter.OnHolderClickListener<ProductComponentBean> {
            override fun onHolderClick(view: View, t: ProductComponentBean, position: Int) {
                mAdapter?.remove(position)
                if (mAdapter != null) {
                    currBean?.attrValueSubmitStr = ProductUtil.productAttrStr2Submit(mAdapter!!.mList)
                }
            }
        }
    }

    /**
     * 初始化数据
     * */
    @SuppressLint("SetTextI18n")
    private fun initData() {
        if (currBean != null) {
            val attrName = currBean!!.attributeName
            if (attrName != null) {
                with(attrName) {
                    tv_required.visibility = if (isCrux == 1) View.VISIBLE else View.GONE
                    tv_attr_tag.text = "$name: "
                }
                if (inputComponentDialog == null) {
                    initInputDialog()
                }
                inputComponentDialog?.setTitle(attrName.name)
            }
            val attrType = currBean!!.attrType
            if (attrType != null && attrType.isNotEmpty()) {
                attrType.add("操作")
                initHeadRecyclerView(attrType.size, attrType)
                initRecyclerView(attrType.size - 1)
            }
            //val attrValue =
        }
    }

    fun refreshData(bean: ProductAttrBean) {
        currBean = bean
        initData()
    }
}