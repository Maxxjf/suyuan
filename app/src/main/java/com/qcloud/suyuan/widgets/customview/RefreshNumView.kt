package com.qcloud.suyuan.widgets.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.qcloud.qclib.base.BaseLinearLayout
import com.qcloud.suyuan.R
import com.qcloud.suyuan.beans.SellersBean
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

    var currNum = 1
    var maxNum = 100
    var currBean: SellersBean? = null

    override val viewId: Int
        get() = R.layout.layout_refresh_num

    override fun initViewAndData() {
        btn_minus.setOnClickListener(this)
        btn_plus.setOnClickListener(this)
        tv_number.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_minus -> {
                if (currNum > 1) {
                    currNum--
                    refreshNum(currNum)
                }
            }
            R.id.btn_plus -> {
                if (currNum < maxNum) {
                    currNum++
                    refreshNum(currNum)
                }
            }
            R.id.tv_number -> {

            }
        }
    }

    private fun refreshNum(number: Int) {
        tv_number?.text = number.toString()
        //currBean?.number = number
    }

    fun refreshBean(bean: SellersBean) {
        currBean = bean
        //currNum = bean.number
        //tv_number?.text = bean.number.toString()
    }
}