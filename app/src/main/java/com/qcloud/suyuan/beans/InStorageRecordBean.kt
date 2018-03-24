package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 入库记录
 * Author: gaobaiqiang
 * 2018/3/24 下午3:38.
 */
class InStorageRecordBean {
    var id: String? = null          // 入库记录id
    var batchNum: String? = null    // 入库批次
        get() = if (StringUtil.isBlank(field)) "" else field
    var createDate: String? = null  // 入库时间
        get() = if (StringUtil.isBlank(field)) "" else field
    var goodsNum: Int = 0           // 入库数量
    var operaName: String? = null   // 操作人
        get() = if (StringUtil.isBlank(field)) "" else field
    var price: Double = 0.00	    // 进货价
    var stopDate: String? = null    // 有效截止时间
        get() = if (StringUtil.isBlank(field)) "" else field
    var surplusNum: Int = 0         // 当前剩余库存
}