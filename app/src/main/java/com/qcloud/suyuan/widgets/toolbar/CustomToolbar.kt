package com.qcloud.suyuan.widgets.toolbar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.TokenUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseApplication
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.model.impl.UserModelImpl
import com.qcloud.suyuan.ui.main.widget.LoginActivity
import com.qcloud.suyuan.ui.main.widget.MainActivity
import com.qcloud.suyuan.utils.UserInfoUtil
import com.qcloud.suyuan.widgets.dialog.TipDialog
import kotlinx.android.synthetic.main.custom_toolbar.view.*

/**
 * 类说明：自定义标题栏
 * Author: Kuzan
 * Date: 2018/3/19 14:50.
 */
class CustomToolbar @JvmOverloads constructor(
        private val mContext: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : Toolbar(mContext, attrs, defStyleAttr), View.OnClickListener {

    private var isBack: Boolean = true
    private var isLogout: Boolean = false
    private var isGoMain: Boolean = true
    private var isTitle: Boolean = true
    private var isMain: Boolean = false
    private var isRight: Boolean = false
    @DrawableRes
    private var backIcon: Int = 0
    @DrawableRes
    var goMainIcon: Int = 0
    @StringRes
    private var titleText: Int = 0
    @DrawableRes
    private var rightIcon: Int = 0
    @StringRes
    private var rightText: Int = 0

    var onBtnClickListener: OnBtnClickListener? = null

    private var logoutDialog: TipDialog? = null

    init {
        initAttrs(attrs)
        initBar()
    }

    @SuppressLint("Recycle")
    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = this.mContext.obtainStyledAttributes(attrs, R.styleable.CustomBar)
            try {
                // 返回
                isBack = typedArray.getBoolean(R.styleable.CustomBar_is_back, true)
                backIcon = typedArray.getResourceId(R.styleable.CustomBar_back_icon, R.drawable.icon_back)

                // 标题
                isTitle = typedArray.getBoolean(R.styleable.CustomBar_is_title, true)
                titleText = typedArray.getResourceId(R.styleable.CustomBar_title_text, R.string.title_suyuan)

                // 退出登录
                isLogout = typedArray.getBoolean(R.styleable.CustomBar_is_logout, false)
                // 返回首页
                isGoMain = typedArray.getBoolean(R.styleable.CustomBar_is_go_main, true)

                // 是否首页
                isMain = typedArray.getBoolean(R.styleable.CustomBar_is_main, false)

                // 是否显示右控件
                isRight = typedArray.getBoolean(R.styleable.CustomBar_is_right, false)
                rightIcon = typedArray.getResourceId(R.styleable.CustomBar_right_icon, R.drawable.icon_search_code)
                rightText = typedArray.getResourceId(R.styleable.CustomBar_right_text, R.string.btn_search)
            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun initBar() {
        val rootView = LayoutInflater.from(mContext).inflate(R.layout.custom_toolbar, null, false)
        addView(rootView, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, resources.getDimensionPixelOffset(R.dimen.barHeight)))

        // 标题
        if (isTitle) {
            tv_title.visibility = View.VISIBLE
            tv_title.setText(titleText)
        } else {
            tv_title.visibility = View.GONE
        }

        // 返回
        if (isBack) {
            btn_back.visibility = View.VISIBLE
            btn_back.setImageResource(backIcon)
        } else {
            btn_back.visibility = View.GONE
        }
        btn_back.setOnClickListener(this)

        // 退出登录
        if (isLogout) {
            btn_logout.visibility = View.VISIBLE
        } else {
            btn_logout.visibility = View.GONE
        }
        btn_logout.setOnClickListener(this)

        // 返回首页
        if (isGoMain) {
            btn_go_main.visibility = View.VISIBLE
        } else {
            btn_go_main.visibility = View.GONE
        }
        btn_go_main.setOnClickListener(this)

        // 首页
        if (isMain) {
            layout_main.visibility = View.VISIBLE
        } else {
            layout_main.visibility = View.GONE
        }

        // 右控件
        if (isRight) {
            btn_right.visibility = View.VISIBLE
            img_right.setImageResource(rightIcon)
            tv_right.setText(rightText)
        } else {
            btn_right.visibility = View.GONE
        }
        btn_right.setOnClickListener(this)
    }

    fun setTitleText(@NonNull titleText: String) {
        tv_title.text = titleText
    }

    fun showRight(isShow: Boolean = false) {
        if (isShow) {
            btn_right.visibility = View.VISIBLE
        } else {
            btn_right.visibility = View.GONE
        }
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btn_back -> {
                if (onBtnClickListener != null) {
                    onBtnClickListener?.onBtnClick(p0)
                } else {
                    (mContext as Activity).finish()
                }
            }
            R.id.btn_logout -> toLogout()
            R.id.btn_go_main -> toGoMain()
            R.id.btn_right -> onBtnClickListener?.onBtnClick(p0)
        }
    }

    private fun toLogout() {
        if (logoutDialog == null) {
            initLogoutDialog()
        }
        logoutDialog?.show()
    }

    private fun toGoMain() {
        BaseApplication.mAppManager?.killAllActivity()
        MainActivity.openActivity(mContext)
    }

    private fun initLogoutDialog() {
        logoutDialog = TipDialog(mContext)
        logoutDialog?.setTip(R.string.toast_logout)
        logoutDialog?.onBtnClickListener = object : BaseDialog.OnBtnClickListener {
            override fun onBtnClick(view: View) {
                logout()
            }
        }
    }

    private fun logout() {
        UserModelImpl().logout(object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                QToast.show(mContext, R.string.toast_logout_success)
                TokenUtil.clearToken()
                UserInfoUtil.delUser()
                UserInfoUtil.delStore()
                BaseApplication.mAppManager?.killAllActivity()
                LoginActivity.openActivity(mContext)
            }

            override fun onError(status: Int, message: String) {
                QToast.show(mContext, message)
            }
        })
    }

    interface OnBtnClickListener {
        fun onBtnClick(view: View)
    }
}