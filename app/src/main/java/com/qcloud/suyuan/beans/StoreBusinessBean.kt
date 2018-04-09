package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类说明：门店证件信息
 * Author: Kuzan
 * Date: 2018/3/29 16:54.
 */
class StoreBusinessBean {
    var beginTime: String? = null       // 许可证开始日期
        get() = if (StringUtil.isBlank(field)) "" else field
    var endTime: String? = null         // 许可证结束日期
        get() = if (StringUtil.isBlank(field)) "" else field
    var id: String = "0"                // 经营资质ID
        get() = if (StringUtil.isBlank(field)) "" else field
    var image: String? = null           // 图片
        get() = if (StringUtil.isBlank(field)) "" else field
    var storeId: String? = null         // 门店ID
        get() = if (StringUtil.isBlank(field)) "" else field
    var type: Int = 1                   // 类型：1是营业执照，2农药经营许可
    var createTime: String? = null      // 创建时间
        get() = if (StringUtil.isBlank(field)) "" else field
    var isRemove: Int = 0
    var createUser: String? = null      // 创建用户
        get() = if (StringUtil.isBlank(field)) "" else field
    var updateTime: String? = null
        get() = if (StringUtil.isBlank(field)) "" else field
    override fun toString(): String {
        return "StoreBusinessBean(beginTime=$beginTime, endTime=$endTime, id='$id', image=$image, storeId=$storeId, type=$type, createTime=$createTime, isRemove=$isRemove, createUser=$createUser, updateTime=$updateTime)"
    }

}