package com.qcloud.suyuan.ui.goods.view

import android.widget.TextView
import com.qcloud.qclib.base.BaseView
import com.qcloud.suyuan.beans.InStorageBean
import org.jetbrains.annotations.NotNull

/**
 * Description: 确认入库
 * Author: gaobaiqiang
 * 2018/3/23 下午9:25.
 */
interface IPurchaseDetailsView: BaseView {
    fun onAddSupplierClick()

    fun onConfirmClick()

    fun onClearClick()

    fun onStockNumberClick()

    fun onPriceClick()

    fun showInput(@NotNull view: TextView)

    fun saveSuccess(bean: InStorageBean)
}