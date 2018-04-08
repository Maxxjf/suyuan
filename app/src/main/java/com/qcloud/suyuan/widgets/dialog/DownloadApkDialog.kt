package com.qcloud.suyuan.widgets.dialog

import android.annotation.SuppressLint
import android.content.Context
import com.qcloud.qclib.callback.DownloadCallback
import com.qcloud.qclib.utils.IntentUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import com.qcloud.suyuan.model.impl.FileModelImpl
import kotlinx.android.synthetic.main.dialog_download_apk.*
import timber.log.Timber
import java.io.File

/**
 * 类说明：apk下载弹窗
 * Author: Kuzan
 * Date: 2018/4/8 15:38.
 */
class DownloadApkDialog constructor(context: Context) : BaseDialog(context) {

    override val viewId: Int
        get() = R.layout.dialog_download_apk

    init {
        setCancelable(false)
    }

    fun downloadApk(fileName: String) {
        FileModelImpl().downloadApk("$fileName.apk", object : DownloadCallback {
            override fun onAccept(acceptStr: String) {
                Timber.e("onAccept: $acceptStr")
            }

            @SuppressLint("SetTextI18n")
            override fun onProgress(progress: Long, total: Long) {
                val percent = progress * 100 / total
                pb_download.max = total.toInt()
                pb_download.progress = progress.toInt()
                tv_percent.text = "$percent%"
                tv_progress.text = "$progress KB/$total KB"
            }

            override fun onError(errMsg: String) {
                Timber.e("onError: $errMsg")
            }

            override fun onComplete(completeMsg: String) {
                Timber.e("onComplete: $completeMsg")
            }

            override fun onSuccess(file: File) {
                Timber.e("onSuccess: ${file.name}")
                val install = IntentUtil.getInstallAppIntent(mContext, file, "com.qcloud.suyuan")
                if (install != null) {
                    mContext.startActivity(install)
                }
            }
        })
    }
}