package com.qcloud.suyuan.ui.setting.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.ui.setting.presenter.impl.DeviceStatePresenterImpl
import com.qcloud.suyuan.ui.setting.view.IDeviceStateView

/**
 * Description: 设备状态
 * Author: gaobaiqiang
 * 2018/3/20 上午9:11.
 */
class DeviceStateActivity: BaseActivity<IDeviceStateView, DeviceStatePresenterImpl>(), IDeviceStateView {
    override val layoutId: Int
        get() = R.layout.activity_device_state

    override fun initPresenter(): DeviceStatePresenterImpl? {
        return DeviceStatePresenterImpl()
    }

    override fun initViewAndData() {

    }

    companion object {
        fun openActivity(@NonNull context: Context) {
            context.startActivity(Intent(context, DeviceStateActivity::class.java))
        }
    }
}