package com.qcloud.suyuan.utils

import com.qcloud.suyuan.beans.PrintBean
import com.qcloud.suyuan.beans.PrintContentBean
import com.qcloud.suyuan.beans.TicketBean
import com.qcloud.suyuan.enums.PayMethod
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * 类说明：打印小票工具
 * Author: Kuzan
 * Date: 2018/3/29 17:43.
 */
object TicketUtil {
    /**
     * 打印小票
     * */
    fun printTicket(ticket: TicketBean?) {
        if (ticket != null) {
            Observable.create<PrintContentBean> {
                var contentBean = PrintContentBean()
                contentBean.content = "收银小票"
                contentBean.alignIndex = 1
                contentBean.isDouble = 1
                it.onNext(contentBean)

                contentBean = PrintContentBean()
                contentBean.content = "门店名称：${ticket.storeName}"
                contentBean.alignIndex = 0
                it.onNext(contentBean)

                contentBean = PrintContentBean()
                contentBean.content = "订单编号：${ticket.orderNo}"
                contentBean.alignIndex = 0
                it.onNext(contentBean)

                contentBean = PrintContentBean()
                contentBean.content = "下单时间：${ticket.orderTime}\n"
                contentBean.alignIndex = 0
                it.onNext(contentBean)

                contentBean = PrintContentBean()
                contentBean.content = "--------------------------------\n"
                contentBean.alignIndex = 0
                it.onNext(contentBean)

//                contentBean = PrintContentBean()
//                contentBean.content = "名称               单价    数量 "
//                contentBean.alignIndex = 0
//                it.onNext(contentBean)

                for (product in ticket.list) {
                    contentBean = PrintContentBean()
                    contentBean.content = "${product.name}*${product.priceStr}*${product.numberStr}"
                    Timber.e("content = ${contentBean.content}")
                    contentBean.alignIndex = 0
                    it.onNext(contentBean)
                }

                contentBean = PrintContentBean()
                contentBean.content = "\n--------------------------------\n"
                contentBean.alignIndex = 0
                it.onNext(contentBean)

                contentBean = PrintContentBean()
                contentBean.content = "商品总数：${ticket.totalNumber}件  合计：${ticket.totalAccountStr}"
                contentBean.alignIndex = 0
                it.onNext(contentBean)

                contentBean = PrintContentBean()
                contentBean.content = "支付方式：${ticket.payMethodName}  优惠：${ticket.discountStr}"
                contentBean.alignIndex = 0
                it.onNext(contentBean)

                contentBean = PrintContentBean()
                if (ticket.payMethod == PayMethod.payCredit.key) {
                    contentBean.content = "赊账：${ticket.realPayStr}"
                } else {
                    contentBean.content = "实收：${ticket.realPayStr}  找零：${ticket.giveMoneyStr}"
                }
                contentBean.alignIndex = 0
                it.onNext(contentBean)

                contentBean = PrintContentBean()
                contentBean.content = "谢谢惠顾！"
                contentBean.alignIndex = 1
                contentBean.isDouble = 1
                contentBean.isWalk = 1
                it.onNext(contentBean)

                it.onComplete()
            }.map {
                val printBean = PrintBean()
                printBean.type = 0
                printBean.content = it
                printBean
            }.observeOn(Schedulers.io())
                    .subscribe({
                        PrintHelper.instance.printData(it)
                    }, {
                        Timber.e("PrintError: ${it.message}")
                    }, {
                        Timber.e("print complete")
                    })

        }
    }
}