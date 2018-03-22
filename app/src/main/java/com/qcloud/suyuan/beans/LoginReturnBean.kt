package com.qcloud.suyuan.beans

/**
 * 类型：LoginReturnBean
 * Author: iceberg
 * Date: 2018/3/19.
 * 登录返回
 */
class LoginReturnBean {
    var loginState: Int = 0     // 0失败 1成功
    var token: String = ""
    var user: UserBean? = null
}