package com.qcloud.suyuan.net

import com.qcloud.qclib.beans.BaseResponse
import com.qcloud.suyuan.beans.MainFormBean
import com.qcloud.suyuan.constant.UrlConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Description: 报表有关
 * Author: gaobaiqiang
 * 2018/3/19 下午7:18.
 */
interface IFormApi {
    /** 首页报表 */
    @GET(UrlConstants.MAIN_FORM)
    fun getMainForm(@QueryMap map: HashMap<String, Any>): Observable<BaseResponse<MainFormBean>>
}