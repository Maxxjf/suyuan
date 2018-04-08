package com.qcloud.suyuan.net

import com.qcloud.suyuan.constant.UrlConstants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * 类说明：文件有关
 * Author: Kuzan
 * Date: 2018/4/8 10:56.
 */
interface IFileApi {
    /** 首页报表 */
    @GET(UrlConstants.DOWNLOAD_APP)
    fun downloadApk(@QueryMap map: HashMap<String, Any>): Call<ResponseBody>
}