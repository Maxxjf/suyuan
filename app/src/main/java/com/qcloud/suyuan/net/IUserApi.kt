package com.qcloud.jiahua.net

import com.qcloud.qclib.network.ReturnBean
import com.qcloud.suyuan.beans.UserBean
import com.qcloud.suyuan.constant.UrlConstants
import retrofit2.http.POST
import retrofit2.http.QueryMap
import rx.Observable
import java.util.*

/**
 * 类说明：用户有关
 * Author: Kuzan
 * Date: 2017/6/6 15:19.
 */
interface IUserApi {
    /**
     * 登录
     * */
    @POST(UrlConstants.LOGIN)
    fun login(@QueryMap map: HashMap<String, Any>): Observable<ReturnBean<UserBean>>

}