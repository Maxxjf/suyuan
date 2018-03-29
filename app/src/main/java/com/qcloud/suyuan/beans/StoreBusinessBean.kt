package com.qcloud.suyuan.beans

/**
 * 类说明：门店证件信息
 * Author: Kuzan
 * Date: 2018/3/29 16:54.
 */
class StoreBusinessBean {
    var beginTime: String? = null       // 许可证开始日期
    var endTime: String? = null         // 许可证结束日期
    var id: String = "0"                // 经营资质ID
    var image: String? = null           // 图片
    var storeId: String? = null         // 门店ID
    var type: Int = 1                   // 类型：1是营业执照，2农药经营许可
    var createTime: String? = null      // 创建时间
    var isRemove: Int = 0
    var createUser: String? = null      // 创建用户
    var updateTime: String? = null
}