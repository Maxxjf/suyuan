package com.qcloud.suyuan.beans

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

/**
 * 类说明：创建产品
 * Author: Kuzan
 * Date: 2018/4/2 9:51.
 */
open class CreateProductSubmitBean: RealmObject(), Serializable {
    @PrimaryKey
    var createUserId: String = "0"  // 创建用户者id
    // goods
    var goodsId: String = ""             // 产品id，新建的时候传null或""或不传
    var barCode: String = ""        // 产品条码,最大长度20字符
    var classifyId: String = ""     // 产品分类id
    var classifyName: String = ""   // 产品分类
    var millId: String = ""         // 产品厂家id
    var millName: String = ""       // 产品厂家
    var millAddress: String = ""    // 厂家地址
    var price: Double = 0.00        // 零售价

    // info
    var infoId: String = ""         // 产品明细id
    var name: String = ""           // 产品名称,最大长度30字符
    var image: String = ""          // 产品图片
    var specification: String = ""  // 规格,最大长度20字符
    var unit: String = ""           // 单位
    var registerCard: String = ""   // 登记证号,最大长度20字符
    var startTime: String = ""      // 登记证有效期开始
    var endTime: String = ""        // 登记证有效期结束
    var licenseCard: String = ""    // 生产许可证
    var standardCard: String = ""   // 产品标准证号,最大长度20字符
    var details: String = ""        // 产品明细,最大2000字符
    var remark: String = ""         // 备注
    var attrId: String = ""         // 属性，多个属性名用,号隔开
    var attrValues: String = ""     // 属性对应的属性值,多条数据之间用";@;"分隔，每条数据每列之间用"# @ #"隔开,多个属性用,号隔开

    override fun toString(): String {
        return "CreateProductSubmitBean(createUserId='$createUserId', goodsId='$goodsId', barCode='$barCode', classifyId='$classifyId', classifyName='$classifyName', millId='$millId', millName='$millName', millAddress=$millAddress, infoId='$infoId', name='$name', image='$image', specification='$specification', unit='$unit', registerCard='$registerCard', startTime='$startTime', endTime='$endTime', licenseCard='$licenseCard', standardCard='$standardCard', details='$details', remark='$remark', attrId='$attrId', attrValues='$attrValues')"
    }
}