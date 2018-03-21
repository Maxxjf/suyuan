package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.ReturnGoodsListAdapter
import com.qcloud.suyuan.adapters.ReturnedReceiptAdapter
import com.qcloud.suyuan.base.SwipeBaseActivity
import com.qcloud.suyuan.beans.GoodsBean
import com.qcloud.suyuan.ui.goods.presenter.impl.IReturnedPersenterImpl
import com.qcloud.suyuan.ui.goods.view.IReturnedView
import kotlinx.android.synthetic.main.activity_returned.*
import timber.log.Timber

/**
 * 类型：ReturnedActivity
 * Author: iceberg
 * Date: 2018/3/19.
 * 退货
 */
class ReturnedActivity :SwipeBaseActivity<IReturnedView,IReturnedPersenterImpl>(),IReturnedView{
    private var goodsAdapter: ReturnGoodsListAdapter? = null
    private var receiptAdapter: ReturnedReceiptAdapter?= null
    override val layoutId: Int
        get() = R.layout.activity_returned

    override fun initPresenter(): IReturnedPersenterImpl? = IReturnedPersenterImpl()

    override fun initViewAndData() {
        initView();

    }

    private fun initView() {
        goodsAdapter= ReturnGoodsListAdapter(this)
        receiptAdapter= ReturnedReceiptAdapter(this)
        rv_return_goods_list.layoutManager = LinearLayoutManager(this)
        rv_receipt.layoutManager=LinearLayoutManager(this)
        rv_return_goods_list.adapter=goodsAdapter
        rv_receipt.adapter=receiptAdapter
        getListData()
    }

    private fun getListData() {
        mPresenter?.getList()
    }
    override fun replaceReceiptList(beans:List<GoodsBean>) {

        if (isRunning){
            if (beans!=null){
                Timber.e("列表：${beans.get(0)}")
                Timber.e("isNUll？：${goodsAdapter}")
                goodsAdapter?.replaceList(beans)
                receiptAdapter?.replaceList(beans)
            }
        }
    }
    companion object {
        fun openActivity(context: Context){
            val intent=Intent(context,ReturnedActivity::class.java)
            context.startActivity(intent)
        }
    }
}