package com.qcloud.suyuan.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.utils.SwipeBackUtil
import com.qcloud.qclib.widget.swipeback.ISwipeBack
import com.qcloud.qclib.widget.swipeback.SwipeBackHelper
import com.qcloud.qclib.widget.swipeback.SwipeBackLayout

/**
 * 类说明：侧滑返回实现
 * Author: Kuzan
 * Date: 2018/3/12 15:14.
 */
abstract class SwipeBaseActivity<V, T : BasePresenter<V>> : BaseActivity<V, T>(), ISwipeBack {
    private var mHelper: SwipeBackHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHelper = SwipeBackHelper(this)
        mHelper!!.onActivityCreate()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper!!.onPostCreate()
    }

    override fun <T : View?> findViewById(id: Int): T {
        val view = super.findViewById<View>(id)
        return if (view == null && mHelper != null) {
            mHelper!!.findViewById(id) as T
        } else {
            view as T
        }
    }

    override fun getSwipeBackLayout(): SwipeBackLayout? {
        return mHelper!!.swipeBackLayout
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        getSwipeBackLayout()?.isEnableGesture = enable
    }

    override fun scrollToFinishActivity() {
        SwipeBackUtil.convertActivityToTranslucent(this)
        getSwipeBackLayout()?.scrollToFinishActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHelper = null
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }
}