package com.qcloud.suyuan.widgets.dialog

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.qcloud.qclib.toast.QToast
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.R

/**
 * 类说明：打印机提示
 * Author: Kuzan
 * Date: 2018/3/27 9:34.
 */
@SuppressLint("Registered")
class PrinterDialog: Service(), View.OnClickListener {
    private var mWakeLock: PowerManager.WakeLock? = null

    private var mView: View? = null

    private var mWindowManager: WindowManager? = null
    private var mContext: Context? = null

    private var mTip: String? = null

    @SuppressLint("WakelockTimeout")
    override fun onCreate() {
        super.onCreate()
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, PrinterDialog::class.java.name)
        mWakeLock?.acquire()
    }

    override fun onBind(intent: Intent): IBinder? = null

    @TargetApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mContext = applicationContext
        mTip = intent.getStringExtra("TIP")
        if (StringUtil.isNotBlank(mTip)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    showPopupWindow()
                } else {
                    QToast.show(mContext!!, mTip)
                }
            } else {
                showPopupWindow()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 显示弹出框
     */
    private fun showPopupWindow() {
        // 先隐藏掉之前的，取最新的消息
        hidePopupWindow()

        // 获取WindowManager
        mWindowManager = mContext!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val params = WindowManager.LayoutParams()
        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        // 设置flag
        val flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        params.flags = flags
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT

        val size = Point()
        mWindowManager!!.defaultDisplay.getSize(size)
        val w = size.x
        params.width = w * 7 / 16
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.CENTER

        initView()

        mWindowManager!!.addView(mView, params)
    }

    /**
     * 隐藏弹出框
     */
    private fun hidePopupWindow() {
        if (mView != null && mWindowManager != null) {
            mWindowManager?.removeView(mView)
            mView = null
        }
    }

    /**
     * 初始化布局
     */
    private fun initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_print_tip, null, false)

        val mTvTip = mView?.findViewById<TextView>(R.id.tv_tip)
        val mBtnCancel = mView?.findViewById<ImageView>(R.id.btn_close)
        val mBtnConfirm = mView?.findViewById<Button>(R.id.btn_confirm)

        if (StringUtil.isNotBlank(mTip)) {
            mTvTip?.text = mTip
        }

        mBtnCancel?.setOnClickListener(this)
        mBtnConfirm?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        hidePopupWindow()
    }

    override fun onDestroy() {
        if (mWakeLock != null) {
            mWakeLock!!.release()
            mWakeLock = null
        }
        super.onDestroy()
    }

    companion object {
        fun openService(context: Context?, tip: String) {
            val intent = Intent(context, PrinterDialog::class.java)
            intent.putExtra("TIP", tip)
            context?.startService(intent)
        }

        fun closeService(context: Context?) {
            context?.stopService(Intent(context, PrinterDialog::class.java))
        }
    }
}