package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 条形码详情
 * Author: gaobaiqiang
 * 2018/4/4 上午11:44.
 */
class BarCodeDetailsBean {
    var id: String = "0"                // 商品ID
    var name: String? = null            // 商品名
        get() = if (StringUtil.isBlank(field)) "" else field
    var barcode: String? = null         // 条形码
        get() = if (StringUtil.isBlank(field)) "" else field
    var classifyName: String? = null    // 分类名
        get() = if (StringUtil.isBlank(field)) "" else field
    var content: String? = null         // 详情内容
    var imageUrl: String? = null        // 图片地址
    var specification: String? = null   // 规格
        get() = if (StringUtil.isBlank(field)) "" else field
    var toxicity: String? = null        // 毒性
        get() = if (StringUtil.isBlank(field)) "" else field
    var unit: String? = null            // 单位
        get() = if (StringUtil.isBlank(field)) "" else field
    var millAddress: String? = null     // 厂家地址
        get() = if (StringUtil.isBlank(field)) "" else field
    var millName: String? = null        // 厂家名
        get() = if (StringUtil.isBlank(field)) "" else field
    var registerCard: String? = null    // 登记证号
    var licenseCard: String? = null     // 生产许可证号
    var standardCard: String? = null    // 产品标准证号

}