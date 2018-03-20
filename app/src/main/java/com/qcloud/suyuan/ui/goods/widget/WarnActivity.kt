package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat
import com.qcloud.qclib.utils.DensityUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.WarnViewPagerAdapte
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.goods.presenter.impl.WarnPresenterImpl
import com.qcloud.suyuan.ui.goods.view.IWarnView
import com.shizhefei.view.indicator.IndicatorViewPager
import com.shizhefei.view.indicator.slidebar.ColorBar
import com.shizhefei.view.indicator.slidebar.DrawableBar
import com.shizhefei.view.indicator.slidebar.ScrollBar
import com.shizhefei.view.indicator.transition.OnTransitionTextListener
import kotlinx.android.synthetic.main.activity_warn.*

/**
 * Description: 告警提示
 * Author: gaobaiqiang
 * 2018/3/20 下午8:31.
 */
class WarnActivity: BaseActivity<IWarnView, WarnPresenterImpl>(), IWarnView {

    private var mIndicatorViewPager: IndicatorViewPager? = null
    private var mAdapter: WarnViewPagerAdapte? = null

    override val layoutId: Int
        get() = R.layout.activity_warn

    override fun initPresenter(): WarnPresenterImpl? {
        return WarnPresenterImpl()
    }

    override fun initViewAndData() {
        initIndicator()
    }

    /**
     * 初始化指示器
     */
    private fun initIndicator() {
        val barr = DrawableBar(this, R.drawable.bg_gray_top_radius, ScrollBar.Gravity.CENTENT)
        view_page_indicator?.scrollBar = barr

        val unSelectSize = 16f
        val selectSize = unSelectSize * 1.2f

        val selectColor = ContextCompat.getColor(this, R.color.white)
        val unSelectColor = ContextCompat.getColor(this, R.color.colorTitle)

        view_page_indicator.onTransitionListener = OnTransitionTextListener()
                .setColor(selectColor, unSelectColor)
                .setSize(selectSize, unSelectSize)

        // 设置viewpager保留界面不重新加载的页面数量
        view_pager.offscreenPageLimit = 2

        mIndicatorViewPager = IndicatorViewPager(view_page_indicator, view_pager)
        mAdapter = WarnViewPagerAdapte(this, supportFragmentManager)
        mIndicatorViewPager?.adapter = mAdapter

        mPresenter?.createViewPager()
    }

    override fun replaceList(list: List<String>) {
        if (isRunning) {
            mAdapter?.replaceList(list)
        }
    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, WarnActivity::class.java))
        }
    }
}