package com.qcloud.suyuan.beans

import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.qclib.utils.StringUtil

/**
 * 类说明：产品入库信息
 * Author: Kuzan
 * Date: 2018/4/7 10:17.
 */
class BatchRecordBean {
    var createDate: String? = null      // 入库时间
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var updateDate: String? = null      // 更新时间
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var supplierId: String? = null      // 商家id
    var batchNum: String? = null        // 批次码
    var goodsId: String? = null         // 商品id
    var goodsNum: Int = 0               // 入库数量
    var goodsNumStr: String = "0"
        get() = goodsNum.toString()
    var id: String? = null              // 入库记录id
    var shopId: String? = null          // 门店id
    var price: Double = 0.00            // 进货价
    var priceStr: String = "0.00"
        get() = price.toString()
    var productionDate: String? = null  // 生产日期
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var stopDate: String? = null        // 截止日期
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var validDate: String? = null
        get() = productionDate + "至" + stopDate
    var surplusNum: Int = 0	            // 当前批次库存
    var surplusNumStr: String = "0"
        get() = surplusNum.toString()
    var operaName: String? = null       // 操作人
    var state: Int = 0
}