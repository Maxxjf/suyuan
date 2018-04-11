package com.qcloud.suyuan.ui.store.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.qclib.utils.StringUtil
import com.qcloud.suyuan.beans.CreateProductBean
import com.qcloud.suyuan.beans.EmptyReturnBean
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
                    if (t.list != null) {
                        mView?.replaceClassify(t.list!!)
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

    /**
     * 判断条形码是否重复
     *
     * @param id 产品id
     * @param barCode 产品条形码
     * @param name 产品名称
     * @param specification 规格
     * @param millId 厂家id
     * */
    override fun isBarCodeRepeat(id: String?, barCode: String, name: String, specification: String, millId: String) {
        mModel.isBarCodeRepeat(id, barCode, name, specification, millId, object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                if (t!= null) {
                    mView?.isBarCodeRepeat(t.isRepeat, message ?: "产品条码或名称已存在，请检验一下")
                } else {
                    mView?.loadErr(message ?: "请求出错了", true)
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }
        })
    }
}