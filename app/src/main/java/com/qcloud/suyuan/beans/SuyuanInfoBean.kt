package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * Description: 溯源详情
 * Author: gaobaiqiang
 * 2018/4/3 下午9:20.
 */
class SuyuanInfoBean {
    var id: String? = null              // 溯源记录id
    var codeUrl: String? = null         // 二维码路径
        get() = if (StringUtil.isBlank(field)) "" else field
    var count: Int = 0                  // 查询次数
    var saleInfoId: String? = null      // 销售流水明细ID
    var isDelete: Int = 0               // 是否删除（0：否；1：是)
    var traceabilityCode: String? = null// 溯源码

    var goodId: String? = null          // 商品ID
    var goodsImage: String? = null      // 商品图片
    var goodsName: String? = null       // 商品名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var goodsRemark: String? = null     // 商品备注
    var activeIngredient: String? = null// 有效成分
    var activeIngredientContent: String? = null     // 有效成分含量
    var agentiaType: String? = null     // 剂型
    var attrName: String? = null        // 商品属性名，多个属性名称用 , 隔开
    var attrValues: String? = null      // 属性值ID,属性值 多个值用","号隔开，多条数据之间用";@;"分隔，每条数据每列之间用"# @ #
    var barCode: String? = null         // 商品条形码
        get() = if (StringUtil.isBlank(field)) "" else field
    var content: String? = null         // 总含量
    var classifyName: String? = null    // 商品分类名
        get() = if (StringUtil.isBlank(field)) "" else field
    var specification: String? = null   // 商品规格
        get() = if (StringUtil.isBlank(field)) "" else field
    var virulence: String? = null       // 毒性
        get() = if (StringUtil.isBlank(field)) "" else field
    var millAddress: String? = null     // 厂家地址
        get() = if (StringUtil.isBlank(field)) "" else field
    var millName: String? = null        // 厂家名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var outTime: String? = null         // 出厂日期
        get() = if (StringUtil.isBlank(field)) "" else field
    var stockDate: String? = null       // 进货时间
        get() = if (StringUtil.isBlank(field)) "" else field
    var disableTime: String? = null     // 有效截止日期
        get() = if (StringUtil.isBlank(field)) "" else field

    var purchaser: String? = null       // 购买者姓名
        get() = if (StringUtil.isBlank(field)) "" else field
    var purchaserAddress: String? = null// 购买者地址
        get() = if (StringUtil.isBlank(field)) "" else field
    var purchaserCard: String? = null   // 购买者身份证
        get() = if (StringUtil.isBlank(field)) "" else field
    var purchaserImage: String? = null  // 购买者图片
    var purchaserPhone: String? = null  // 购买者电话
        get() = if (StringUtil.isBlank(field)) "" else field
    var gender: String? = null          // 购买者性别
    var purchaserTime: String? = null   // 购买时间
        get() = if (StringUtil.isBlank(field)) "" else field
    var purchaserUse: String? = null    // 购买使用用途
        get() = if (StringUtil.isBlank(field)) "" else field
    var remark: String? = null          // 购买者备注
        get() = if (StringUtil.isBlank(field)) "" else field

    var registerCard: String? = null    // 登记证号
        get() = if (StringUtil.isBlank(field)) "" else field
    var startTime: String? = null       // 登记证开始时间
        get() = if (StringUtil.isBlank(field)) "" else field
    var endTime: String? = null         // 登记证结束时间
        get() = if (StringUtil.isBlank(field)) "" else field
    var standardCard: String? = null    // 产品标准证号
        get() = if (StringUtil.isBlank(field)) "" else field
    var licenseCard: String? = null     // 生产许可证号
        get() = if (StringUtil.isBlank(field)) "" else field

    var shopId: String? = null          // 销售门店Id
    var storeAddress: String? = null    // 门店地址
        get() = if (StringUtil.isBlank(field)) "" else field
    var storeCode: String? = null       // 门店编码
        get() = if (StringUtil.isBlank(field)) "" else field
    var storeName: String? = null       // 门店名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var shopkeeperName: String? = null  // 门店主名称
        get() = if (StringUtil.isBlank(field)) "" else field
    var storePhone: String? = null      // 门店电话
        get() = if (StringUtil.isBlank(field)) "" else field
    var provinceCode: String? = null    // 门店省编码
    var cityCode: String? = null        // 门店市编码
    var districtCode: String? = null    // 门店地区编码
    var supplier: String? = null        // 供应商名称
}