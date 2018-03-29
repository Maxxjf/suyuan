package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Description: 用户信息
 * Author: gaobaiqiang
 * 2018/3/22 下午2:38.
 */
open class UserBean: RealmObject() {
    @PrimaryKey
    var saveId: String = "suyuan_user"  // 保证库里只存一个登录用户
    var id: String = "-1"   // 用户id
    var mobile: String? = null  // 手机号
        get() = if (StringUtil.isBlank(field)) "" else field
    private var name: String? = null    // 用户名称
        get() = if(StringUtil.isBlank(field)) "" else field
    var nickname: String? = null    // 昵称
        get() = if (StringUtil.isBlank(field)) "" else field
    private var idCard: String? = null  // 身份证号
        get() = if(StringUtil.isBlank(field)) "" else field
    private var pwd: String? = null // 密码
    private var userNumber: String? = null  // 员工号
        get() = if(StringUtil.isBlank(field)) "" else field
    private var headImage: String? = null   // 头像
    private var email: String? = null       // 邮件
        get() = if(StringUtil.isBlank(field)) "" else field
    private var lastTime: String? = null    // 最后一次登录时间
        get() = if(StringUtil.isBlank(field)) "" else field
    private var createTime: String? = null  // 创建时间
        get() = if(StringUtil.isBlank(field)) "" else field
    private var provinceCode: String? = null    // 省代码
        get() = if(StringUtil.isBlank(field)) "" else field
    private var cityCode: String? = null    // 市代码
        get() = if(StringUtil.isBlank(field)) "" else field
    private var districtCode: String? = null    // 区代码
        get() = if(StringUtil.isBlank(field)) "" else field
    private var isRemove: Int = 0
    private var state: Int = 0

    override fun toString(): String {
        return "UserBean(id='$id', pwd=$pwd, headImage=$headImage, isRemove=$isRemove, state=$state)"
    }
}