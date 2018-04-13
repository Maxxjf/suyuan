package com.qcloud.suyuan.widgets.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R

/**
 * 类说明：右边带清除按钮的EditText
 * Author: Kuzan
 * Date: 2018/1/20 10:56.
 */
class ClearEditText @JvmOverloads constructor(
        val mContext: Context,
        attrs: AttributeSet? = null) : AppCompatEditText(mContext, attrs),
        View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    private var rightDrawable: Drawable? = null
    private var touchListener: OnTouchListener? = null
    private var focusChangeListener: OnFocusChangeListener? = null

    init {
        rightDrawable = compoundDrawables[2]
        if (rightDrawable == null) {
            rightDrawable = ContextCompat.getDrawable(mContext, R.drawable.et_delete)
        }
        rightDrawable!!.setBounds(0, 0, rightDrawable!!.intrinsicWidth, rightDrawable!!.intrinsicHeight)
        compoundDrawablePadding = 10
        super.setOnFocusChangeListener(this)
        super.setOnTouchListener(this)
        addTextChangedListener(this)
    }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener?) {
        this.focusChangeListener = l
    }

    override fun setOnTouchListener(l: OnTouchListener?) {
        this.touchListener = l
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val tapped = (event.x > width - paddingRight - rightDrawable!!.intrinsicWidth
                    && event.x < width
                    && event.y > (height - rightDrawable!!.intrinsicHeight) / 2
                    && event.y < (height - rightDrawable!!.intrinsicHeight) / 2 + rightDrawable!!.intrinsicHeight)
            if (tapped) {
                if (event.action == MotionEvent.ACTION_UP) {
                    setText("")
                }
                return true
            }
        }
        touchListener?.onTouch(v, event)
        return false
    }

    override fun onFocusChange(p0: View, p1: Boolean) {
        setClearIconVisible(p1 && StringUtil.isNotBlank(text.toString()))
        focusChangeListener?.onFocusChange(p0, p1)
    }

    override fun afterTextChanged(p0: Editable) {
        setClearIconVisible(isFocusable && StringUtil.isNotBlank(p0.toString()))
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    private fun setClearIconVisible(visible: Boolean) {
        val temp: Drawable? = if (visible) rightDrawable else null
        val drawables: Array<Drawable> = compoundDrawables
        setCompoundDrawables(drawables[0], drawables[1], temp, drawables[3])
    }
}