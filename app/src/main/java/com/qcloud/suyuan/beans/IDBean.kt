package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.constant.AppConstants
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * 类说明：身份证数据
 * Author: Kuzan
 * Date: 2018/3/26 15:27.
 */
open class IDBean: RealmObject() {
    // 身份证号
    @PrimaryKey
    var idCode: String? = null
    var id: Long = -1L
    // 名称
    var name: String? = null
    // 姓别
    var sex: String? = null
    var gender: Int = 1
        get() = if (sex.equals("男")) 1 else 0
    // 民族
    var nation: String? = null
    // 出生日期
    var birthday: String? = null
    var birthdayFormat: String? = null
        get() {
            return if (StringUtil.isNotBlank(birthday) && birthday!!.length == 8) {
                birthday!!.substring(0, 4) + " 年 " + birthday!!.substring(4, 6) + " 月 " + birthday!!.substring(6, 8) + " 日"
            } else {
                ""
            }
        }
    // 出生年
    var year: String? = null
    // 出生月
    var month: String? = null
    // 出生日
    var day: String? = null
    // 地址
    var address: String? = null
    // 签发机关
    var department: String? = null
    // 有效期开始时间
    var startDate: String? = null
    var startDateFormat: String? = null
        get() {
            return if (StringUtil.isNotBlank(startDate) && startDate!!.length == 8) {
                startDate!!.substring(0, 4) + "." + startDate!!.substring(4, 6) + "." + startDate!!.substring(6, 8)
            } else {
                ""
            }
        }
    // 有效期结束时间
    var endDate: String? = null
    var endDateFormat: String? = null
        get() {
            return if (StringUtil.isNotBlank(endDate) && endDate!!.length == 8) {
                endDate!!.substring(0, 4) + "." + endDate!!.substring(4, 6) + "." + endDate!!.substring(6, 8)
            } else {
                ""
            }
        }
    // 头像路径，数据解析成功会自动保存到sd卡
    var userImg: String = AppConstants.SDPATH + "/wltlib/zp.bmp"
    var userImgBase64: String? = null
    // 用户电话
    var mobile: String? = null

    override fun toString(): String {
        return "IDBean(idCode=$idCode, name=$name, sex=$sex, nation=$nation, birthday=$birthday, year=$year, month=$month, day=$day, address=$address, department=$department, startDate=$startDate, endDate=$endDate, userImg='$userImg', mobile=$mobile)"
    }
}