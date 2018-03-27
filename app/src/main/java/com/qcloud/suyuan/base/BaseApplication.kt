package com.qcloud.suyuan.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.qcloud.qclib.AppManager
import com.qcloud.qclib.FrameConfig
import com.qcloud.qclib.utils.SharedUtil
import com.qcloud.suyuan.BuildConfig
import com.qcloud.suyuan.R
import com.qcloud.suyuan.utils.FileLoggingTree
import com.qcloud.suyuan.utils.NFCHelper
import com.qcloud.suyuan.utils.PrintHelper
import com.tencent.bugly.crashreport.CrashReport
import io.realm.Realm
import timber.log.Timber

/**
 * 类说明：BaseApplication
 * Author: Kuzan
 * Date: 2018/3/12 14:45.
 */
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FrameConfig.initSystemConfig(this, R.raw.config)

        mApplication = this
        mAppManager = AppManager.instance

        Realm.init(this)

        // 初始化缓存
        SharedUtil.initSharedPreferences(this)

        // 腾讯Bugly crash异常捕捉
        CrashReport.initCrashReport(applicationContext, "74e849e38b", false)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(FileLoggingTree())
        }

        NFCHelper.instance.initSerialPort(this)
        PrintHelper.instance.initPrinter(this)
    }

    /**
     * 解决Android 5.0以下方法超限
     * */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        var mApplication: BaseApplication? = null
        var mAppManager: AppManager? = null // Activity 管理器
    }
}