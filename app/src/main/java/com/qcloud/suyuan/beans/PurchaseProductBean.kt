package com.qcloud.suyuan.beans

import com.qcloud.qclib.enums.DateStyleEnum
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.qclib.utils.StringUtil
import java.io.Serializable

/**
 * Description: 入库产品
 * Author: gaobaiqiang
 * 2018/4/8 下午6:26.
 */
class PurchaseProductBean: Serializable {
    var id: String? = null  // id
    var name: String? = null    // 商品名称
        get() = if (StringUtil.isBlank(field)) "--" else field
    var image: String? = null       // 商品图片(入库使用)
    var storageNum: Int = 0
    var storageNumStr: String = "0"
        get() = storageNum.toString()
    var code: String? = null     // 产品条形条码
        get() = if (StringUtil.isBlank(field)) "--" else field
    var storageTime: String? = null
    var specification: String? = null   // 规格
        get() = if (StringUtil.isBlank(field)) "--" else field
    var unit: String? = null    // 单位
        get() = if (StringUtil.isBlank(field)) "" else field
    var classifyName: String? = null    // 分类名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var stock: Int = 0      // 当前库存
    var stockStr: String = "0"
        get() = stock.toString()
    var millName: String? = null    // 厂家名称
        get() = if (StringUtil.isBlank(field)) "--" else field
    var expDate: String? = null
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var endDate: String? = null
        get() {
            return if (StringUtil.isBlank(field)) {
                ""
            } else {
                DateUtil.transformDate(field!!, DateStyleEnum.YYYY_MM_DD.value)
            }
        }
    var surplusNum: Int = 0
    var surplusNumStr: String = "0"
        get() = surplusNum.toString()
    var stockPrice: Double = 0.00  // 零售价
    var stockPriceStr: String = "0.00"
        get() = stockPrice.toString()
    var recordId: String? = null
    override fun toString(): String {
        return "PurchaseProductBean(id=$id, image=$image, storageNum=$storageNum, storageTime=$storageTime, stock=$stock, expDate=$expDate, endDate=$endDate, surplusNum=$surplusNum, stockPrice=$stockPrice, recordId=$recordId)"
    }

}