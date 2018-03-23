package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 用户信息
 * Author: gaobaiqiang
 * 2018/3/22 下午2:38.
 */
class UserBean {

    /**
     * lastTime : 2018-03-22 17:35:05
     * districtCode : 440402000000
     * idCard : null
     * provinceCode : 440000000000
     * cityCode : 440400000000
     * headImage : null
     * mobile : 15656565656
     * createTime : 2017-11-02 15:05:58
     * name : dorry
     * nickname : 东利
     * isRemove : 0
     * id : 374604013921894400
     * state : 1
     * pwd : e10adc3949ba59abbe56e057f20f883e
     * userNumber : null
     * email : null
     */
    var id: Long = -1L
    var mobile: String? = null  // 手机号
        get() = if (StringUtil.isBlank(field)) "" else field
    var nickname: String? = null    // 昵称
        get() = if (StringUtil.isBlank(field)) "" else field
    private var lastTime: String? = null
        get() = if(StringUtil.isBlank(lastTime))"" else field
    private var districtCode: String? = null
    private var idCard: String? = null
    private var provinceCode: String? = null
    private var cityCode: String? = null
    private var headImage: String? = null
    private var createTime: String? = null
    private var name: String? = null
    private var isRemove: Boolean = false
    private var state: Int = 0
    private var pwd: String? = null
    private var userNumber: String? = null
    private var email: String? = null
    override fun toString(): String {
        return "UserBean(id=$id, districtCode=$districtCode, idCard=$idCard, provinceCode=$provinceCode, cityCode=$cityCode, headImage=$headImage, createTime=$createTime, name=$name, isRemove=$isRemove, state=$state, pwd=$pwd, userNumber=$userNumber, email=$email)"
    }


}