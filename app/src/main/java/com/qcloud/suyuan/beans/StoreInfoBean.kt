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
    var image: String? = null       // 门店图片
    var latitude: Double = 0.000000// 纬度
    var longitude: Double = 0.000000// 经度
    var name: String? = null        // 门店名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var phone: String? = null       // 联系电话
    var shopkeeperName: String? = null  // 店主名
    var storeCode: String? = null       // 门店编号
    var provinceCode: String? = null    // 省号
    var cityCode: String? = null        // 市号
    var districtCode: String? = null    // 区号
    var roleId: String? = null
    var updateUser: String? = null
    var updateTime: String? = null
    var userId: String? = null
    var mangementTypeId: String? = null
    var createTime: String? = null
    var isRemove: Int = 0
    var createUser: String? = null
    var status: Int = 0
}