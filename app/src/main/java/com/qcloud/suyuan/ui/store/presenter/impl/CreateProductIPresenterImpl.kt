package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.beans.CreateProductBean
import com.qcloud.suyuan.beans.ProductMillBean
import com.qcloud.suyuan.model.IGoodsModel
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.store.presenter.ICreateProductIPresenter
import com.qcloud.suyuan.ui.store.view.ICreateProductIView

/**
 * Description: 创建产品第一步
 * Author: gaobaiqiang
 * 2018/3/30 下午1:53.
 */
class CreateProductIPresenterImpl: BasePresenter<ICreateProductIView>(), ICreateProductIPresenter {

    private val mModel: IGoodsModel = GoodsModelImpl()

    /**
     * 创建修改私有产品
     *
     * @param id 商品id,用于修改
     * */
    override fun loadProduct(id: String?) {
        mModel.editMyProduct(id, object : DataCallback<CreateProductBean> {
            override fun onSuccess(t: CreateProductBean?, message: String?) {
                if (t != null) {
                    mView?.refreshData(t)
                    if (t.categoryAll != null) {
                        mView?.replaceClassify(t.categoryAll!!)
                    }
                } else {
                    if (StringUtil.isBlank(id) || StringUtil.isEquals(id, "0") || StringUtil.isEquals(id, "-1")) {
                        mView?.loadErr(message ?: "加载出错", false)
                    } else {
                        mView?.loadErr(message ?: "加载出错", true)
                    }
                }
            }

            override fun onError(status: Int, message: String) {
                if (StringUtil.isBlank(id) || StringUtil.isEquals(id, "0") || StringUtil.isEquals(id, "-1")) {
                    mView?.loadErr(message, false)
                } else {
                    mView?.loadErr(message, true)
                }
            }
        })
    }

    /**
     * 获取生产厂家
     *
     * @param classifyId 分类id
     * */
    override fun loadFactory(classifyId: String) {
        mModel.getFactoryByClassify(classifyId, object : DataCallback<ReturnDataBean<ProductMillBean>> {
            override fun onSuccess(t: ReturnDataBean<ProductMillBean>?, message: String?) {
                if (t?.list != null) {
                    mView?.replaceMill(t.list!!)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }
        })
    }
}