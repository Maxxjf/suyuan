package com.qcloud.suyuan.widgets.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.qcloud.qclib.base.BaseLinearLayout
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.SellersAdapter
import com.qcloud.suyuan.beans.SaleProductBean
import com.qcloud.suyuan.widgets.dialog.InputDialog
import kotlinx.android.synthetic.main.layout_refresh_num.view.*

/**
 * Description: 修改数量布局
 * Author: gaobaiqiang
 * 2018/3/21 下午9:05.
 */
class RefreshNumView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : BaseLinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private var currNum = 1
    private var maxNum = 100
    private var currBean: SaleProductBean? = null
    private var inputDialog: InputDialog? = null

    var onRefreshNumClickListener: SellersAdapter.OnRefreshNumClickListener? = null

    override val viewId: Int
        get() = R.layout.layout_refresh_num

    override fun initViewAndData() {
        btn_minus.setOnClickListener(this)
        btn_plus.setOnClickListener(this)
        tv_number.setOnClickListener(this)
    }

    private fun initInputDialog() {
        inputDialog = InputDialog(mContext)
        inputDialog?.setBindView(tv_number)
        inputDialog?.setInputValue(tv_number.text.toString().trim())

        inputDialog?.onFinishInputListener = object : InputDialog.OnFinishInputListener {
            override fun onFinishInput(message: String?) {
                if (StringUtil.isNotBlank(message)) {
                    currNum = message!!.toInt()
                } else {
                    tv_number.text = "1"
                    currNum = 1
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_minus -> {
                if (currNum > 1) {
                    currNum--
                    refreshNum(currNum)
                    onRefreshNumClickListener?.onRefreshNum(-1, currBean!!)
                }
            }
            R.id.btn_plus -> {
                if (currNum < maxNum) {
                    currNum++
                    refreshNum(currNum)
                    onRefreshNumClickListener?.onRefreshNum(1, currBean!!)
                }
            }
            R.id.tv_number -> {
                if (inputDialog == null) {
                    initInputDialog()
                }
                inputDialog?.show()
            }
        }
    }

    private fun refreshNum(number: Int) {
        currBean?.number = number
        tv_number?.text = number.toString()
    }

    fun refreshBean(bean: SaleProductBean) {
        currBean = bean
        currNum = bean.number
        tv_number?.text = bean.numberStr
    }
}