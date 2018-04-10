package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.beans.EmptyReturnBean
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


    //订单查询
    override fun loadData( code:String,saleId:String) {
      model.scanCode(code,saleId,object :DataCallback<ScanCodeBean>{
        override fun onSuccess(t: ScanCodeBean?, message: String?) {
          if (t != null) {
              mView?.loadDataSuccess(t)
          }
        }

        override fun onError(status: Int, message: String) {
          mView?.loadErr(message)
        }
      })
    }

    //提交退货
    override fun salesReturn( money:String,list:List<ScanCodeBean.MerchandiseBean>) {
        //1.将溯源码ID提出来
        var strList=ArrayList<String>()
        for (index in list.indices){
            strList.add(list[index].traceabilityId!!)
        }
        //2.溯源码ID将以，隔开，合并成一个字符串
        var  traceabilityIdStr=StringUtil.combineList(strList)
        model.salesReturn(money,traceabilityIdStr,object :DataCallback<EmptyReturnBean>{
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                if (message != null) {
                    mView?.loadErr(message)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }
        })
    }
}