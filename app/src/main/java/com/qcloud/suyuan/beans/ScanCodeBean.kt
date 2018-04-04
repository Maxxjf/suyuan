package com.qcloud.suyuan.beans

/**
 * 类型：ScanCodeBean
 * Author: iceberg
 * Date: 2018/3/23.
 * 退货时返回销售明细，商品信息
 */
class ScanCodeBean {

    /**
     * infoList : [{"goodsName":"测试内容hox0","goodsNum":81386,"price":81572}]
     * merchandise : {"batchNum":"测试内容5pv7","goodsName":"测试内容1h38","specification":"测试内容n7r3","traceabilityCode":"测试内容buqk"}
     * saleSerial : {"amount":74281,"discount":24115,"id":"测试内容6j3d","realPay":38080,"serialNumber":"测试内容52va","time":"测试内容7u1b"}
     */

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
        var goodsName: String? = null
        var specification: String? = null
        var traceabilityCode: String? = null
        var traceabilityId: String? = null
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
         * time : 时间
         */

        var amount: Double = 0.0
        var discount: Double = 0.0
        var id: String? = null
        var realPay: Double = 0.0
        var serialNumber: String? = null
        var time: String? = null
        override fun toString(): String {
            return "SaleSerialBean(amount=$amount, discount=$discount, id=$id, realPay=$realPay, serialNumber=$serialNumber, time=$time)"
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
        var goodsNum: Int = 0
        var price: Double = 0.0
        var specification: String = ""
        override fun toString(): String {
            return "InfoListBean(goodsName=$goodsName, goodsNum=$goodsNum, price=$price, specification='$specification')"
        }

    }

    override fun toString(): String {
        return "ScanCodeBean(merchandise=$merchandise, saleSerial=$saleSerial, infoList=$infoList)"
    }


}
