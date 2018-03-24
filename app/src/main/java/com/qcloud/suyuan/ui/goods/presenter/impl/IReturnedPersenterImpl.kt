package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.EmptyResBean
import com.qcloud.suyuan.beans.ScanCodeBean
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.goods.presenter.IReturnedPresenter
import com.qcloud.suyuan.ui.goods.view.IReturnedView

/**
 * 类型：IReturnedPersenterImpl
 * Author: iceberg
 * Date: 2018/3/19.
 * 退货
 */
class IReturnedPersenterImpl : BasePresenter<IReturnedView>(),IReturnedPresenter {
    private var model:IGoodsModel=GoodsModelImpl()
    private var saleId=""//销售单id (首次为空,从第二次开始传)
    override fun loadData( code:String) {
      model.ScanCode(code,saleId,object :DataCallback<ScanCodeBean>{
        override fun onSuccess(t: ScanCodeBean?, message: String?) {
          if (t != null) {
            mView?.replaceList(t.infoList,false)
            mView?.addListAtEnd(t.merchandise,false)
            saleId= t.saleSerial!!.id!!
          }
        }

        override fun onError(status: Int, message: String) {
          mView?.loadErr(message)
        }
      })
    }
    override fun salesReturn( money:String,traceabilityIdStr:String) {
        model.SalesReturn(money,traceabilityIdStr,object :DataCallback<EmptyResBean>{
            override fun onSuccess(t: EmptyResBean?, message: String?) {

            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }
        })
    }
}