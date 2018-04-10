package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类说明：批次码产品信息
 * Author: Kuzan
 * Date: 2018/4/7 10:11.
 */
class BatchMerchandiseBean {
    var id: String = "0"                // 商品id
    var name: String? = null            // 商品名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var barcode: String? = null         // 产品批次码
        get() = if (StringUtil.isBlank(field)) "" else field
    var classifyName: String? = null    // 分类名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var toxicity: String? = null        // 毒性
        get() = if (StringUtil.isBlank(field)) "" else field
    var unit: String? = null            // 单位
        get() = if (StringUtil.isBlank(field)) "" else field
    var specification: String? = null   // 产品规格
        get() = if (StringUtil.isBlank(field)) "" else field
    var millAddress: String? = null     // 厂家地址
        get() = if (StringUtil.isBlank(field)) "" else field
    var millName: String? = null        // 厂家名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var licenseCard: String? = null		// 生产许可证
        get() = if (StringUtil.isBlank(field)) "" else field
    var registerCard: String? = null    // 登记证号
        get() = if (StringUtil.isBlank(field)) "" else field
    var standardCard: String? = null    // 标准证号
        get() = if (StringUtil.isBlank(field)) "" else field

}