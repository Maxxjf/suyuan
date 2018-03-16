package com.qcloud.jiahua.widget.toolbar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.qcloud.suyuan.R
import kotlinx.android.synthetic.main.toolbar_base.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColor

/**
 * 类说明：自定义标题栏
 * Author: Kuzan
 * Date: 2017/6/7 15:04
 */
class BaseBar @JvmOverloads constructor(
        private val mContext: Context,
        private val mAttrs: AttributeSet,
        defStyleAttr: Int = 0) : Toolbar(mContext, mAttrs, defStyleAttr), View.OnClickListener {

    private var toolbarView: View? = null
    private var tvTitle: TextView? = null
    private var ibLeft: ImageButton? = null
    private var btnLeft: TextView? = null
    private var ibRight: ImageButton? = null
    private var btnRight: TextView? = null

    private var isLeft: Boolean = false
    private var leftIcon: Int = 0
    private var isLeftText: Boolean = false
    private var leftText: Int = 0
    private var isRight: Boolean = false
    private var rightIcon: Int = 0
    private var isRightText: Boolean = false
    private var rightText: Int = 0
    private var rightColor: Int = 0
    private var isTitleText: Boolean = false
    private var titleText: Int = 0
    private var titleColor: Int = 0
    private var barBackground: Int = 0

    private var mListener: OnBtnListener? = null

    init {
        initCustomAttributes(mAttrs)
        initLayout(mContext)
    }

    private fun initLayout(context: Context) {
        toolbarView = LayoutInflater.from(context).inflate(R.layout.toolbar_base, null)
        addView(toolbarView!!, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, resources.getDimensionPixelOffset(R.dimen.toolbar_height)))
        tvTitle = tv_title
        ibLeft = ib_left
        btnLeft = btn_left
        ibRight = ib_right
        btnRight = btn_right

        if (isTitleText) {
            tvTitle!!.visibility = View.VISIBLE
            tvTitle!!.setText(titleText)
        } else {
            tvTitle!!.visibility = View.GONE
        }

        if (isLeft) {
            ibLeft!!.visibility = View.VISIBLE
            ibLeft!!.setImageResource(leftIcon)
        } else {
            ibLeft!!.visibility = View.GONE
        }

        if (isLeftText) {
            btnLeft!!.visibility = View.VISIBLE
            btnLeft!!.setText(leftText)
        } else {
            btnLeft!!.visibility = View.GONE
        }

        if (isRight) {
            ibRight!!.visibility = View.VISIBLE
            ibRight!!.setImageResource(rightIcon)
        } else {
            ibRight!!.visibility = View.GONE
        }

        if (isRightText) {
            btnRight!!.visibility = View.VISIBLE
            btnRight!!.setText(rightText)
            btnRight!!.setTextColor(rightColor)
        } else {
            btnRight!!.visibility = View.GONE
        }

        tvTitle!!.textColor = titleColor
        toolbarView!!.backgroundColor = barBackground

        ibLeft!!.setOnClickListener(this)
        btnLeft!!.setOnClickListener(this)
        ibRight!!.setOnClickListener(this)
        btnRight!!.setOnClickListener(this)
    }

    /**
     * 获取自定义属性值
     */
    @SuppressLint("Recycle")
    private fun initCustomAttributes(attrs: AttributeSet) {
        val typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BaseBar)
        isLeft = typedArray.getBoolean(R.styleable.BaseBar_is_left, false)
        leftIcon = typedArray.getResourceId(R.styleable.BaseBar_left_icon, 0)

        isLeftText = typedArray.getBoolean(R.styleable.BaseBar_is_left_text, false)
        leftText = typedArray.getResourceId(R.styleable.BaseBar_left_text, R.string.save)

        isRight = typedArray.getBoolean(R.styleable.BaseBar_is_right, false)
        rightIcon = typedArray.getResourceId(R.styleable.BaseBar_right_icon, 0)

        isRightText = typedArray.getBoolean(R.styleable.BaseBar_is_right_text, false)
        rightText = typedArray.getResourceId(R.styleable.BaseBar_right_text, R.string.save)
        rightColor = typedArray.getColor(R.styleable.BaseBar_right_color, Color.BLACK)

        isTitleText = typedArray.getBoolean(R.styleable.BaseBar_is_title, false)
        titleText = typedArray.getResourceId(R.styleable.BaseBar_title_text, R.string.app_name)

        titleColor = typedArray.getColor(R.styleable.BaseBar_title_color, Color.BLACK)
        barBackground = typedArray.getColor(R.styleable.BaseBar_bar_background, Color.WHITE)
    }

    fun setTitle(title: String) {
        tvTitle!!.text = title
    }

    override fun setTitle(titleRes: Int) {
        tvTitle!!.setText(titleRes)
    }

    override fun setTitleTextColor(color: Int) {
        tvTitle!!.textColor = color
    }

    fun setTitleBackground(res: Int) {
        toolbarView!!.backgroundResource = res
    }

    fun setLeftIcon(isLeft: Boolean, res: Int) {
        if (ibLeft != null) {
            if (isLeft) {
                ibLeft!!.visibility = View.VISIBLE
                ibLeft!!.setImageResource(res)
            } else {
                ibLeft!!.visibility = View.INVISIBLE
            }
        }
    }

    fun setRightIcon(isRight: Boolean, res: Int) {
        if (ibRight != null) {
            if (isRight) {
                ibRight!!.visibility = View.VISIBLE
                ibRight!!.setImageResource(res)
            } else {
                ibRight!!.visibility = View.INVISIBLE
            }
        }
    }

    fun setRightIconPadding(left: Int, top: Int, right: Int, bottom: Int) {
        ibRight!!.setPadding(left, top, right, bottom)
    }

    fun setLeftText(res: String) {
        if (isLeftText) {
            btnLeft!!.visibility = View.VISIBLE
            btnLeft!!.text = res
        }
    }

    fun setLeftText(res: Int) {
        if (isLeftText) {
            btnLeft!!.visibility = View.VISIBLE
            btnLeft!!.setText(res)
        }
    }

    fun setRightText(res: String) {
        if (isRightText) {
            btnRight!!.visibility = View.VISIBLE
            btnRight!!.text = res
        }
    }

    fun setRightText(res: Int) {
        if (isRightText) {
            btnRight!!.visibility = View.VISIBLE
            btnRight!!.setText(res)
        }
    }

    fun setBackgroundAlpha(alpha: Int) {
        this.toolbarView!!.background.alpha = alpha
    }

    fun hideTitleText() {
        tvTitle!!.visibility = View.INVISIBLE
    }

    fun showTitleText() {
        tvTitle!!.visibility = View.VISIBLE
    }

    fun hideLeft() {
        ibLeft!!.visibility = View.INVISIBLE
    }

    fun showLeft() {
        ibLeft!!.visibility = View.VISIBLE
    }

    fun hideRightText() {
        btnRight!!.visibility = View.INVISIBLE
    }

    fun showRightText() {
        btnRight!!.visibility = View.VISIBLE
    }

    fun hideRight() {
        ibRight!!.visibility = View.INVISIBLE
    }

    fun showRight() {
        ibRight!!.visibility = View.VISIBLE
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ib_left -> {
                if (mListener != null) {
                    mListener!!.onBtnClick(ib_left)
                } else {
                    (mContext as Activity).finish()
                }
            }

            else -> {
                if (mListener != null) {
                    mListener!!.onBtnClick(p0)
                }
            }
        }
    }

    fun setOnBtnListener(listener: OnBtnListener) {
        mListener = listener
    }

    interface OnBtnListener {
        fun onBtnClick(view: View)
    }
}
