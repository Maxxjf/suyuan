package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 商品明细
 * Author: gaobaiqiang
 * 2018/3/24 下午11:42.
 */
class ProductInfoBean {
    var details: String? = null         // 商品详情
        get() = if (StringUtil.isEmpty(field)) "" else field
    var imageUrl: String? = null        // 商品图片
    var licenseCard: String? = null     // 生产许可证号
        get() = if (StringUtil.isBlank(field)) "" else field
    var name: String? = null            // 商品名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var registerCard: String? = null    // 登记证号
        get() = if (StringUtil.isBlank(field)) "" else field
    var specification: String? = null   // 商品规格
        get() = if (StringUtil.isBlank(field)) "" else field
    var standardCard: String? = null    // 产品标准证号
        get() = if (StringUtil.isBlank(field)) "" else field
    var unit: String? = null            // 单位
        get() = if (StringUtil.isBlank(field)) "" else field
}