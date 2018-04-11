package com.qcloud.suyuan.ui.goods.presenter.impl

import com.qcloud.qclib.base.BasePresenter
import com.qcloud.qclib.beans.ReturnDataBean
import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.EmptyReturnBean
import com.qcloud.suyuan.beans.ProductBean
import com.qcloud.suyuan.constant.AppConstants
import com.qcloud.suyuan.enums.PlatformEnum
import com.qcloud.suyuan.model.impl.GoodsModelImpl
import com.qcloud.suyuan.ui.goods.presenter.IModifyPricePresenter
import com.qcloud.suyuan.ui.goods.view.IModifyPriceView

/**
 * Description: 修改售价
 * Author: gaobaiqiang
 * 2018/3/15 上午12:36.
 */
class ModifyPricePresenterImpl: BasePresenter<IModifyPriceView>(), IModifyPricePresenter {

    val mModel = GoodsModelImpl()

    /**
     * 获取产品列表
     *
     * @param classifyId 分类id
     * */
    override fun loadData(pageNo: Int, classifyId: String?, keyword: String?) {
        mModel.list(pageNo, AppConstants.PAGE_SIZE, classifyId, PlatformEnum.isAll.key, keyword, object : DataCallback<ReturnDataBean<ProductBean>> {
            override fun onSuccess(t: ReturnDataBean<ProductBean>?, message: String?) {
                if (t != null) {
                    if (t.list != null) {
                        if (pageNo == 1) {
                            mView?.replaceList(t.list, t.isNext(AppConstants.PAGE_SIZE))
                        } else {
                            mView?.addListAtEnd(t.list, t.isNext(AppConstants.PAGE_SIZE))
                        }
                    } else {
                        if (pageNo == 1) {
                            mView?.showEmptyView()
                        } else {
                            mView?.loadErr("暂无更多数据", true)
                        }
                    }
                }
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message, true)
            }
        })
    }

    /**
     * 修改库存警告线
     *
     * @param id 产品id
     * @param cordon 库存警告线
     * */
    override fun modifyWarnLine(id: String, cordon: Int) {
        mModel.editWarnLine(id, cordon, object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                mView?.modifyStockWarnSuccess()
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }
        })
    }

    /**
     * 修改价格
     *
     * @param id 产品id
     * @param price 建议零售价
     * */
    override fun modifyPrice(id: String, price: Double) {
        mModel.editPrice(id, price, object : DataCallback<EmptyReturnBean> {
            override fun onSuccess(t: EmptyReturnBean?, message: String?) {
                mView?.modifyPriceSuccess()
            }

            override fun onError(status: Int, message: String) {
                mView?.loadErr(message)
            }
        })
    }
}