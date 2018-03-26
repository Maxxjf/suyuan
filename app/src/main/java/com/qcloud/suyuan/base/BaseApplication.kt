package com.qcloud.suyuan.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheEntity
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.cookie.CookieJarImpl
import com.lzy.okgo.cookie.store.DBCookieStore
import com.lzy.okgo.https.HttpsUtils
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import com.qcloud.qclib.AppManager
import com.qcloud.qclib.FrameConfig
import com.qcloud.qclib.utils.SharedUtil
import com.qcloud.suyuan.BuildConfig
import com.qcloud.suyuan.R
import com.qcloud.suyuan.utils.FileLoggingTree
import com.qcloud.suyuan.utils.NFCHelper
import com.tencent.bugly.crashreport.CrashReport
import io.realm.Realm
import okhttp3.OkHttpClient
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

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

        initOkGo()

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
    }

    /**
     * 初始化网络请求
     */
    private fun initOkGo() {
        val builder = OkHttpClient.Builder()
        // log相关
        val loggingInterceptor = HttpLoggingInterceptor("OkGo")
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY) //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO)    //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor)  //添加OkGo默认debug日志

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MICROSECONDS)       //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)      //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)    //全局的连接超时时间

        //自动管E理cookie（或者叫session的保持）
        builder.cookieJar(CookieJarImpl(DBCookieStore(this)))
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
        builder.hostnameVerifier(SafeHostnameVerifier())

        //信任所有证书,不安全有风险
        val sslparams = HttpsUtils.getSslSocketFactory()
        builder.sslSocketFactory(sslparams.sSLSocketFactory, sslparams.trustManager)

        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE).retryCount = 0
    }

    private inner class SafeHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String, session: SSLSession): Boolean {
            //验证主机名是否匹配
            return true
        }
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