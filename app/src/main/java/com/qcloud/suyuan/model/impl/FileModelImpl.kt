package com.qcloud.suyuan.model.impl

import com.qcloud.qclib.callback.DownloadCallback
import com.qcloud.qclib.network.BaseApi
import com.qcloud.qclib.network.FrameRequest
import com.qcloud.suyuan.model.IFileModel
import com.qcloud.suyuan.net.IFileApi

/**
 * 类说明：文件有关
 * Author: Kuzan
 * Date: 2018/4/8 15:51.
 */
class FileModelImpl: IFileModel {

    /**
     * 下载apk
     *
     * @param fileName 文件名称 suyuan_V1.0.0.apk
     * */
    override fun downloadApk(fileName: String, callback: DownloadCallback) {
        val params = FrameRequest.getAppParams()

        val api: IFileApi = FrameRequest.instance.createDownloadRequest(IFileApi::class.java)
        BaseApi.disposeDownload(api.downloadApk(params), fileName, callback)
    }
}