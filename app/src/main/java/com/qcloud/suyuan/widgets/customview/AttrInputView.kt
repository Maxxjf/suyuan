package com.qcloud.suyuan.widgets.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.qcloud.qclib.base.BaseLinearLayout
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.ProductAttrBean
import com.qcloud.suyuan.widgets.dialog.InputDialog
import kotlinx.android.synthetic.main.layout_of_product_attr_input.view.*

/**
 * Description: 产品属性值输入
 * Author: gaobaiqiang
 * 2018/3/30 下午4:46.
 */
class AttrInputView  @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : BaseLinearLayout(context, attrs, defStyleAttr) {
    private var currBean: ProductAttrBean? = null
    private var inputDialog: InputDialog? = null

    override val viewId: Int
        get() = R.layout.layout_of_product_attr_input

    override fun initViewAndData() {
        et_value.setOnClickListener {
            if (inputDialog == null) {
                initInputDialog()
            }
            inputDialog?.show()
        }
    }

    private fun initInputDialog() {
        inputDialog = InputDialog(mContext)
        inputDialog?.setBindView(et_value)
        inputDialog?.setInputValue(et_value.text.toString().trim())

        inputDialog?.onFinishInputListener = object : InputDialog.OnFinishInputListener {
            override fun onFinishInput(message: String?) {
                currBean?.attrValueSubmitStr = message
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
                    tv_attr_tag.text = "$name: "
                }
            }
            // 给予默认值
            if (StringUtil.isNotBlank(currBean!!.value)) {
                et_value.text = currBean!!.value
                currBean?.attrValueSubmitStr = currBean!!.value
            }
        }
    }

    fun refreshData(bean: ProductAttrBean) {
        currBean = bean
        initData()
    }
}