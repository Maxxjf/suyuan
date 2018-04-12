package com.qcloud.suyuan.widgets.customview

/**
 * 类型：自定义可输入金额的EditText(仿微信输入框效果)
 * Author: iceberg
 * Date: 2018/4/12  14:57
 */

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.EditText
import com.qcloud.qclib.utils.StringUtil
import timber.log.Timber


class MoneyEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : EditText(context, attrs, defStyleAttr) {
    private var textChange: Boolean = false

    /**
     * 获取金额
     */
    //如果最后一位是小数点
    val moneyText: String
        get() {
            val money = text.toString()
            return if (money.endsWith(".")) {
                money.substring(0, money.length - 1)
            } else money
        }

    init {
        this.setOnKeyListener({ view, i, keyEvent ->
            Timber.e("_________")
            if (keyEvent.action == KeyEvent.ACTION_UP) {
                if ((i == KeyEvent.KEYCODE_ENTER)) {
                    addPotText()
                }
            }
            false
        })
        //设置可以输入小数
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        isFocusable = true
        isFocusableInTouchMode = true
//        监听文字变化
        this.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (!textChange) {
                    restrictText()
                }
                textChange = false
            }
        })

    }

    /**
     * 将整形自动添加".0"
     */
    private fun addPotText() {
        var input = text.toString().trim()
        if (StringUtil.isBlank(input)) {
            return
        }
        if (!input.contains(".")) {
            setText(input + ".0")
        }
    }

    /**
     * 将小数限制为2位
     */
    private fun restrictText() {
        var input = text.toString()
        if (StringUtil.isBlank(input)) {
            return
        }
        if (input.contains(".")) {
            val pointIndex = input.indexOf(".")
            val totalLenth = input.length
            val len = totalLenth - 1 - pointIndex
            if (len > 2) {
                input = input.substring(0, totalLenth - 1)
                textChange = true
                setText(input)
                setSelection(input.length)
            }
        }

        if (input.trim { it <= ' ' }.substring(0) == ".") {
            input = "0" + input
            setText(input)
            setSelection(2)
        }

    }

    companion object {
        private val TAG = "MoneyEditText"
    }
}