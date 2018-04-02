package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.CreateProductSubmitBean
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.ProductAttrBean
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.store.presenter.ICreateProductIIPresenter
import com.qcloud.suyuan.ui.store.view.ICreateProductIIView

/**
 * Description: 创建产品第二步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:55.
 */
class CreateProductIIPresenterImpl: BasePresenter<ICreateProductIIView>(), ICreateProductIIPresenter {
    private val mModel: IGoodsModel = GoodsModelImpl()

    /**
     * 创建产品下一步
     *
     * @param id 产品id
     * @param classifyId 产品分类id
     * */
    override fun createProductNext(id: String?, classifyId: String) {
        mModel.createProductNext(id, classifyId, object : DataCallback<ReturnDataBean<ProductAttrBean>> {
            override fun onSuccess(t: ReturnDataBean<ProductAttrBean>?, message: String?) {
                if (t?.list != null) {
                    mView?.replaceList(t.list!!)
                } else {
                    mView?.loadErr(message ?: "获取创建产品属性出错", true)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }
        })
    }

    /**
     * 创建产品
     *
     * @param bean 提交创建产品
     * */
    override fun add(bean: CreateProductSubmitBean) {
        mModel.add(bean, object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                mView?.createSuccess()
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }
        })
    }
}