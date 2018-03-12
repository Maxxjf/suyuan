package com.qcloud.suyuan.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.utils.SystemBarUtil
import com.qcloud.qclib.widget.dialog.LoadingDialog
import com.qcloud.suyuan.R
import timber.log.Timber

/**
 * 类说明：Activity基类
 * Author: Kuzan
 * Date: 2018/3/12 15:11.
 */
abstract class BaseActivity<V, T: BasePresenter<V>>: AppCompatActivity() {
    open var mContext: Context? = null
    open var isRunning: Boolean = false

    protected var mPresenter: T? = null

    private val fragments: MutableList<BaseFragment<*, *>> = ArrayList()
    private var mCurrFragment: BaseFragment<*, *>? = null

    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBarTint()
        setContentView(layoutId)

        mContext = this
        BaseApplication.mAppManager?.addActivity(this)

        if (mPresenter == null) {
            mPresenter = initPresenter()
        }
        mPresenter?.attach(this as V)

        isRunning = true

        initViewAndData()
    }

    fun addFragment(fragment: BaseFragment<*, *>?) {
        if (fragment != null) {
            if (!fragments.contains(fragment)) {
                fragments.add(fragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detach()
        BaseApplication.mAppManager?.killActivity(this)
        isRunning = false

        for (fragment: BaseFragment<*, *> in fragments) {
            detach(fragment)
        }
        fragments.clear()
    }

    private fun detach(fragment: BaseFragment<*, *>?) {
        if (fragments.contains(fragment)) {
            fragment?.detach()
        }
    }

    fun remove(fragment: BaseFragment<*, *>?) {
        if (fragments.contains(fragment)) {
            fragments.remove(fragment)
        }
    }

    protected open fun replaceFragment(toFragment: BaseFragment<*, *>?, containerId: Int, isAnim: Boolean) {
        if (toFragment == null) {
            Timber.w("将要替换的fragment不存在")
            return
        }
        replaceFragment(toFragment, containerId, isAnim, com.qcloud.qclib.R.anim.left_right_in, com.qcloud.qclib.R.anim.left_right_out)
    }

    /**
     * 改变当前的fragment
     *
     * @param toFragment    需要加载的fragment
     * @param containerId   fragment的布局容器id
     * @param isAnim        是否需要切换动画
     */
    protected open fun replaceFragment(toFragment: BaseFragment<*, *>, containerId: Int, isAnim: Boolean, inAnim: Int, outAnim: Int) {
        val ft = supportFragmentManager.beginTransaction()
        if (isAnim) {
            ft.setCustomAnimations(inAnim, outAnim)
        }
        val toTag: String = toFragment::class.java.simpleName
        if (mCurrFragment != null) {
            ft.hide(mCurrFragment)
        }
        if (!toFragment.isAdded()) {
            ft.add(containerId, toFragment, toTag)
        } else {
            ft.show(toFragment)
        }
        ft.commitAllowingStateLoss()
        mCurrFragment = toFragment
    }

    /**
     * 设置状态栏颜色
     * */
    protected open fun initSystemBarTint() {
        if (translucentStatusBar) {
            // 设置状态栏全透明
            SystemBarUtil.transparencyStatusBar(this)
        } else {
            // 设置状态栏字体颜色为深色
            if (isStatusBarTextDark) {
                if (SystemBarUtil.isSupportStatusBarDarkFont()) {
                    // 设置状态栏颜色
                    SystemBarUtil.setStatusBarColor(this, statusBarColor, false, isPaddingStatusBar)
                    SystemBarUtil.setStatusBarLightMode(this, true)
                } else {
                    Timber.e("当前设备不支持状态栏字体变色")
                    // 设置状态栏颜色为主题颜色
                    SystemBarUtil.setStatusBarColor(this, getDarkColorPrimary(), false, isPaddingStatusBar)
                }
            } else {
                // 设置状态栏颜色
                SystemBarUtil.setStatusBarColor(this, getDarkColorPrimary(), false, isPaddingStatusBar)
            }
        }
    }

    /**
     * 获取主题色
     * */
    fun getColorPrimary(): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        return typedValue.data
    }

    /**
     * 获取深主题色
     * */
    fun getDarkColorPrimary(): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
        return typedValue.data
    }

    /** 模版方法，通过该方法获取该Activity的view的layoutId */
    protected abstract val layoutId: Int

    /** 实例化presenter */
    protected abstract fun initPresenter(): T?

    /** 初始化界面和数据 */
    protected abstract fun initViewAndData()

    /** 子类可以重写改变状态栏颜色 */
    protected open val statusBarColor: Int
        get() = getColorPrimary()

    /** 子类可以重写决定是否使用透明状态栏 */
    protected open val translucentStatusBar: Boolean
        get() = false

    /** 子类可以重写决定是否使用状态栏深色字体 */
    protected open val isStatusBarTextDark: Boolean
        get() = false

    /** 子类可以重写决定是否解决状态栏与标题栏重叠问题 */
    protected open val isPaddingStatusBar: Boolean = true

    fun startLoadingDialog() {
        if (mContext != null) {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog(mContext!!)
            }
            loadingDialog?.show()
        }
    }

    fun stopLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog?.dismiss()
        }
        loadingDialog = null
    }
}