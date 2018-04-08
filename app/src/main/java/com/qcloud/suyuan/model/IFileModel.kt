package com.qcloud.suyuan.model

import com.qcloud.qclib.callback.DownloadCallback

/**
 * 类说明：文件有关
 * Author: Kuzan
 * Date: 2018/4/8 15:50.
 */
interface IFileModel {
    /**下载apk*/
    fun downloadApk(fileName: String, callback: DownloadCallback)
}