package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 用户信息
 * Author: gaobaiqiang
 * 2018/3/22 下午2:38.
 */
class UserBean {
    var id: Long = -1L
    var mobile: String? = null  // 手机号
        get() = if (StringUtil.isBlank(field)) "" else field
    var nickname: String? = null    // 昵称
        get() = if (StringUtil.isBlank(field)) "" else field
}