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
         * batchNum : 测试内容5pv7
         * goodsName : 测试内容1h38
         * specification : 测试内容n7r3
         * traceabilityCode : 测试内容buqk
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
         * amount : 74281
         * discount : 24115
         * id : 测试内容6j3d
         * realPay : 38080
         * serialNumber : 测试内容52va
         * time : 测试内容7u1b
         */

        var amount: Int = 0
        var discount: Int = 0
        var id: String? = null
        var realPay: Int = 0
        var serialNumber: String? = null
        var time: String? = null
        override fun toString(): String {
            return "SaleSerialBean(amount=$amount, discount=$discount, id=$id, realPay=$realPay, serialNumber=$serialNumber, time=$time)"
        }

    }

    class InfoListBean {
        /**
         * goodsName : 测试内容hox0
         * goodsNum : 81386
         * price : 81572
         */

        var goodsName: String? = null
        var goodsNum: Int = 0
        var price: Int = 0
        var specification: String = ""
        override fun toString(): String {
            return "InfoListBean(goodsName=$goodsName, goodsNum=$goodsNum, price=$price, specification='$specification')"
        }

    }

    override fun toString(): String {
        return "ScanCodeBean(merchandise=$merchandise, saleSerial=$saleSerial, infoList=$infoList)"
    }


}
