package com.qcloud.suyuan.widgets.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Rect
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.TextView
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.KeyBoardUtil
import com.qcloud.qclib.utils.ScreenUtil
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R
import kotlinx.android.synthetic.main.dialog_input.*
import timber.log.Timber

/**
 * Description: 输入弹窗
 * Author: gaobaiqiang
 * 2018/3/13 下午10:07.
 */
class InputDialog @JvmOverloads constructor(
        @NonNull private val mContext: Context,
        @StyleRes themeResId: Int = R.style.InputDialog) : Dialog(mContext, themeResId) {

    private var mLastDiff = 0

    private var mInputValue: String? = null

    var mEtView: TextView? = null

    var onFinishInputListener: OnFinishInputListener? = null
    var onTextChangeListener: OnTextChangeListener? = null

    init {
        setContentView(R.layout.dialog_input)
        initDialog()
    }

    private fun initDialog() {
        val lp = window!!.attributes
        lp.width = ScreenUtil.getScreenWidth(mContext) //设置宽度
        lp.height = mContext.resources.getDimensionPixelOffset(R.dimen.btn_height)
        lp.gravity = Gravity.BOTTOM
        window!!.attributes = lp
        setCancelable(true)
        window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        initView()
    }

    private fun initView() {
        //et_text.imeOptions = EditorInfo.IME_ACTION_UNSPECIFIED
        et_text.inputType = InputType.TYPE_CLASS_TEXT
        //修改下划线颜色
        et_text.background.setColorFilter(ContextCompat.getColor(mContext, R.color.transparent), PorterDuff.Mode.CLEAR)
        et_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                onTextChangeListener?.onTextChange(editable.toString())
            }
        })

        //监听键盘
        et_text.setOnEditorActionListener { _, actionId, event ->
            when (actionId) {
                KeyEvent.KEYCODE_ENDCALL, KeyEvent.KEYCODE_ENTER -> {
                    onFinishClick()
                    true
                }
                KeyEvent.KEYCODE_BACK -> {
                    dismiss()
                    false
                }
                else -> false
            }
        }

        et_text.setOnKeyListener { _, _, keyEvent ->
            Timber.e("keyEvent " + keyEvent.characters)
            false
        }

        // dialog消失键盘会消失，但是键盘消失，dialog不一定消失，比如按软键盘上的消失键，键盘消失，dialog不会消失
        // 所以在这里监听键盘的高度，如果高度为0则表示键盘消失，那么就应该dimiss dialog
        layout_input.addOnLayoutChangeListener { view, i, i1, i2, i3, i4, i5, i6, i7 ->
            val r = Rect()
            //获取当前界面可视部分
            window!!.decorView.getWindowVisibleDisplayFrame(r)
            //获取屏幕的高度
            val screenHeight = window!!.decorView.rootView.height
            //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
            val heightDifference = screenHeight - r.bottom

            if (heightDifference <= 0 && mLastDiff > 0) {
                dismiss()
            }
            mLastDiff = heightDifference
        }
        btn_finish.setOnClickListener {
            onFinishClick()
        }
    }

    /**
     * 点击完成
     * */
    private fun onFinishClick() {
        if (check()) {
            onFinishInputListener?.onFinishInput(mInputValue)
            KeyBoardUtil.showSoftInput(mContext, et_text)
            KeyBoardUtil.hideKeybord(mContext, et_text)
            mEtView?.setText(mInputValue)
            dismiss()
        }
    }

    private fun check(): Boolean {
        mInputValue = et_text.text.toString().trim { it <= ' ' }

        if (StringUtil.isBlank(mInputValue)) {
            QToast.show(mContext, R.string.toast_input_not_null)
            return false
        }

        return true
    }

    fun setInputValue(value: String) {
        et_text.setText(value)
    }

    fun initInputHint(@StringRes hintRes: Int) {
        et_text?.setHint(hintRes)
    }

    fun initInputHint(hintValue: String) {
        et_text?.hint = hintValue
    }

    fun setBindView(@NonNull view: TextView) {
        this.mEtView=view
    }

    override fun dismiss() {
        super.dismiss()
        // dismiss之前重置mLastDiff值避免下次无法打开
        mLastDiff = 0
    }

    override fun show() {
        super.show()
        KeyBoardUtil.showKeybord(mContext, et_text)
    }

    /**
     * 完成输入
     * */
    interface OnFinishInputListener {
        fun onFinishInput(message: String?)
    }

    /**
     * 文字变化
     * */
    interface OnTextChangeListener {
        fun onTextChange(message: String)
    }
}