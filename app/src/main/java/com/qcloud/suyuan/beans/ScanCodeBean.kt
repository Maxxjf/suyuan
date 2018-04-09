package com.qcloud.suyuan.beans

import com.qcloud.qclib.utils.StringUtil

/**
 * 类型：ScanCodeBean
 * Author: iceberg
 * Date: 2018/3/23.
 * 退货时返回销售明细，商品信息
 */
class ScanCodeBean {

    var merchandise: MerchandiseBean? = null //商品
    var saleSerial: SaleSerialBean? = null   //销售对象
    var infoList: List<InfoListBean>? = null  //销售明细集合

    class MerchandiseBean {
        /**
         * batchNum : 批次
         * goodsName : 产品名称
         * specification : 规格
         * traceabilityCode :溯码源
         * traceabilityId：溯源记录id
         */

        var batchNum: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var goodsName: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var specification: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var traceabilityCode: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var traceabilityId: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        override fun toString(): String {
            return "MerchandiseBean(batchNum=$batchNum, goodsName=$goodsName, specification=$specification, traceabilityCode=$traceabilityCode, traceabilityId=$traceabilityId)"
        }

    }

    class SaleSerialBean {
        /**
         * amount : 合计
         * discount : 优惠
         * id : 销售单id
         * realPay : 实收
         * serialNumber : ；流水号
         * createDate : 时间
         */

        var amount: Double = 0.0
        var discount: Double = 0.0
        var id: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var realPay: Double = 0.0
        var serialNumber: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var createDate: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        override fun toString(): String {
            return "SaleSerialBean(amount=$amount, discount=$discount, id=$id, realPay=$realPay, serialNumber=$serialNumber, createDate=$createDate)"
        }

    }

    class InfoListBean {
        /**
         * goodsName : 名称
         * goodsNum : 数量
         * price : 单价
         * specification : 规则
         */

        var goodsName: String? = null
            get() = if (StringUtil.isBlank(field)) "" else field
        var goodsNum: Int = 0
        var price: Double = 0.0
        var specification: String = ""
            get() = if (StringUtil.isBlank(field)) "" else field
        override fun toString(): String {
            return "InfoListBean(goodsName=$goodsName, goodsNum=$goodsNum, price=$price, specification='$specification')"
        }

    }

    override fun toString(): String {
        return "ScanCodeBean(merchandise=$merchandise, saleSerial=$saleSerial, infoList=$infoList)"
    }


}
