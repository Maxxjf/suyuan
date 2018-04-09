package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类型：出库的产品信息
 * Author: iceberg
 * Date: 2018/3/30  15:41
 */
class OutStorageBean {

    /**
     */

    var info: InfoBean? = null

    class InfoBean {
        /**
         * classifyName : 分类名
         * endDate : 有效期截止日期
         * expDate : 生成日期
         * id : 商品ID
         * image : 图片地址
         * millName : 厂家名
         * name : 商品名
         * recordId : 入库批次ID
         * specification : 规格
         * stockPrice : 进货价
         * storageNum : 进货数量
         * storageTime : 进货时间
         * surplusNum : 批次剩余库存
         * unit : 单位
         */
        var classifyName: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var endDate: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var expDate: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var id: String =""
            get() = if (StringUtil.isBlank(field)) "" else field
        var image: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var millName: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var name: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var recordId: String = ""
            get() = if (StringUtil.isBlank(field)) "" else field
        var specification: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var stockPrice: Double = 0.00
        var storageNum: Int = 0
        var storageTime: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var surplusNum: Int = 0
        var unit: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        override fun toString(): String {
            return "InfoBean(classifyName=$classifyName, endDate=$endDate, expDate=$expDate, id=$id, image=$image, millName=$millName, name=$name, recordId=$recordId, specification=$specification, stockPrice=$stockPrice, storageNum=$storageNum, storageTime=$storageTime, surplusNum=$surplusNum, unit=$unit)"
        }

    }

    override fun toString(): String {
        return "OutStorageBean(info=$info)"
    }
}
