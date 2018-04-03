package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil
import java.io.Serializable

/**
 * Description: 产品
 * Author: gaobaiqiang
 * 2018/3/23 下午7:39.
 */
class ProductBean : Serializable {
    var id: String? = null  // id
    var name: String? = null    // 商品名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var imageUrl: String? = null    // 商品图片
    var image: String? = null       // 商品图片(入库使用)
    var retailPrice: Double = 0.00  // 零售价
    var retailPriceStr: String = "0.00"
        get() = retailPrice.toString()
    var classifyName: String? = null    // 分类名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var stock: Int = 0      // 当前库存
    var stockStr: String = "0"
        get() = stock.toString()
    var amount: Int = 0     // 总库存
    var amountStr: String = "0"
        get() = amount.toString()
    var barCode: String? = null     // 产品条形条码
        get() = if (StringUtil.isBlank(field)) "" else field
    var cordon: Int = 0     // 库存警告线
    var cordonStr: String = "0"
        get() = cordon.toString()
    var millName: String? = null    // 厂家名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var specification: String? = null   // 规格
        get() = if (StringUtil.isBlank(field)) "" else field
    var unit: String? = null    // 单位
        get() = if (StringUtil.isBlank(field)) "" else field
    var createDate: String? = null  // 最新入库时间
        get() = if (StringUtil.isBlank(field)) "" else field
    var isPlatform: Int = 0 // 是否平台商品 1是 0不是
    var operaName: String? = null   // 操作人

}