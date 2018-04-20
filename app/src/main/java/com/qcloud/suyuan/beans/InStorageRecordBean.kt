package com.qcloud.suyuan.beans

import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 入库记录
 * Author: gaobaiqiang
 * 2018/3/24 下午3:38.
 */
class InStorageRecordBean {
    var id: String? = null          // 入库记录id
    var shopId: String? = null
    var goodId: String? = null
    var supplierId: String? = null
    var batchNum: String? = null    // 入库批次
        get() = if (StringUtil.isBlank(field)) "--" else field
    var createDate: String? = null  // 入库时间
        get() {
            return if (StringUtil.isBlank(field)) {
                "--"
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var goodsNum: Int = 0           // 入库数量
    var goodsNumStr: String = "0"
        get() = goodsNum.toString()
    var operaName: String? = null   // 操作人
        get() = if (StringUtil.isBlank(field)) "--" else field
    var price: Double = 0.00	    // 进货价
    var stopDate: String? = null    // 有效截止时间
        get() {
            return if (StringUtil.isBlank(field)) {
                "--"
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var surplusNum: Int = 0         // 当前剩余库存
    var surplusNumStr: String = "0"
        get() = surplusNum.toString()
    var updateDate: String? = null
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var productionDate: String? = null  // 生产日期
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var state: Int = 0
    override fun toString(): String {
        return "InStorageRecordBean(id=$id, shopId=$shopId, goodId=$goodId, supplierId=$supplierId, goodsNum=$goodsNum, price=$price, surplusNum=$surplusNum, state=$state)"
    }

}