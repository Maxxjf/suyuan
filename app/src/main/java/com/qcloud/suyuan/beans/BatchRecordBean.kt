package com.qcloud.suyuan.beans

/**
 * 类说明：产品入库信息
 * Author: Kuzan
 * Date: 2018/4/7 10:17.
 */
class BatchRecordBean {
    var createDate: String? = null      // 入库时间
    var goodsNum: Int = 0               // 入库数量
    var goodsNumStr: String = "0"
        get() = goodsNumStr.toString()
    var id: String? = null              // 入库记录id
    var price: Double = 0.00            // 进货价
    var priceStr: String = "0.00"
        get() = price.toString()
    var productionDate: String? = null  // 生产日期
    var stopDate: String? = null        // 截止日期
    var validDate: String? = null
        get() = productionDate + "至" + stopDate
    var surplusNum: Int = 0	            // 当前批次库存
    var surplusNumStr: String = "0"
        get() = surplusNum.toString()
}