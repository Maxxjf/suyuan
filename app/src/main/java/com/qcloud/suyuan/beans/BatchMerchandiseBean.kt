package com.qcloud.suyuan.beans

/**
 * 类说明：批次码产品信息
 * Author: Kuzan
 * Date: 2018/4/7 10:11.
 */
class BatchMerchandiseBean {
    var id: String = "0"                // 商品id
    var name: String? = null            // 商品名称
    var barCode: String? = null         // 产品批次码
    var classifyName: String? = null    // 分类名称
    var toxicity: String? = null        // 毒性
    var unit: String? = null            // 单位
    var specification: String? = null   // 产品规格
    var millAddress: String? = null     // 厂家地址
    var millName: String? = null        // 厂家名称
    var licenseCard: String? = null		// 生产许可证
    var registerCard: String? = null    // 登记证号
    var standardCard: String? = null    // 标准证号

}