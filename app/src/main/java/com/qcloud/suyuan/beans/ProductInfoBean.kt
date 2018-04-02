package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 商品明细
 * Author: gaobaiqiang
 * 2018/3/24 下午11:42.
 */
class ProductInfoBean {
    var id: String? = null
    var name: String? = null            // 商品名称
        get() = if (StringUtil.isEmpty(field)) "" else field
    var details: String? = null         // 商品详情
        get() = if (StringUtil.isEmpty(field)) "" else field
    var content: String? = null
        get() = if (StringUtil.isEmpty(field)) "" else field
    var image: String? = null           // 商品图片
    var licenseCard: String? = null     // 生产许可证号
        get() = if (StringUtil.isBlank(field)) "" else field
    var registerCard: String? = null    // 登记证号
        get() = if (StringUtil.isBlank(field)) "" else field
    var startTime: String? = null           // 登记证开始时间
    var endTime: String? = null             // 登记证结束时间
    var specification: String? = null   // 商品规格
        get() = if (StringUtil.isBlank(field)) "" else field
    var standardCard: String? = null    // 产品标准证号
        get() = if (StringUtil.isBlank(field)) "" else field
    var unit: String? = null            // 单位
        get() = if (StringUtil.isBlank(field)) "" else field
    var merchandiseId: String? = null
    var dosageForm: String? = null
    var remark: String? = null
    var activeprinciple: String? = null
    var attributeNameId: String? = null     // 产品属性名称id
    var attributeValueId: String? = null    // 产品属性对应值
    var basicParam: String? = null
    var ratio: String? = null
}