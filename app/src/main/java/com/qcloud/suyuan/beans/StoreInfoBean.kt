package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * 类说明：门店基本信息
 * Author: Kuzan
 * Date: 2018/3/29 16:49.
 */
open class StoreInfoBean: RealmObject() {
    @PrimaryKey
    var saveId: String = "suyuan_store"     // 保证库里只存一个门店
    var id: String = "0"            // 门店ID
    var address: String? = null     // 门店地址
        get() = if (StringUtil.isBlank(field)) "" else field
    var image: String? = null       // 门店图片
        get() = if (StringUtil.isBlank(field)) "" else field
    var latitude: Double = 0.000000// 纬度
    var longitude: Double = 0.000000// 经度
    var name: String? = null        // 门店名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var phone: String? = null       // 联系电话
        get() = if (StringUtil.isBlank(field)) "" else field
    var shopkeeperName: String? = null  // 店主名
        get() = if (StringUtil.isBlank(field)) "" else field
    var storeCode: String? = null       // 门店编号
        get() = if (StringUtil.isBlank(field)) "" else field
    var provinceCode: String? = null    // 省号
        get() = if (StringUtil.isBlank(field)) "" else field
    var cityCode: String? = null        // 市号
        get() = if (StringUtil.isBlank(field)) "" else field
    var districtCode: String? = null    // 区号
        get() = if (StringUtil.isBlank(field)) "" else field
    var roleId: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var updateUser: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var updateTime: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var userId: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var mangementTypeId: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var createTime: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var isRemove: Int = 0
    var createUser: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    var status: Int = 0
    var areaName:String?=null   //归属区域
        get() = if (StringUtil.isBlank(field)) "" else field
    var userName:String?=null   //登录账号
        get() = if (StringUtil.isBlank(field)) "" else field
    override fun toString(): String {
        return "StoreInfoBean(saveId='$saveId', id='$id', latitude=$latitude, longitude=$longitude, isRemove=$isRemove, createUser=$createUser, status=$status, areaName=$areaName, userName=$userName)"
    }

}