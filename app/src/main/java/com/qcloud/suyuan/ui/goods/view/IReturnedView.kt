package com.qcloud.suyuan.ui.goods.view

import com.qcloud.suyuan.beans.GoodsBean

/**
 * 类型：IReturnedView
 * Author: iceberg
 * Date: 2018/3/19.
 * 退货
 */
interface IReturnedView {
    fun replaceReceiptList(mList:List<GoodsBean>);
}