package com.qcloud.suyuan.ui.goods.widget

import android.content.Context
import android.content.Intent
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.SwipeBaseActivity
import com.qcloud.suyuan.ui.goods.presenter.impl.IReturnedPersenterImpl
import com.qcloud.suyuan.ui.goods.view.IReturnedView

/**
 * 类型：ReturnedActivity
 * Author: iceberg
 * Date: 2018/3/19.
 * 退货
 */
class ReturnedActivity :SwipeBaseActivity<IReturnedView,IReturnedPersenterImpl>(),IReturnedView{
    override val layoutId: Int
        get() = R.layout.activity_returned

    override fun initPresenter(): IReturnedPersenterImpl? = IReturnedPersenterImpl()

    override fun initViewAndData() {

    }
    companion object {
        fun openActivity(context: Context){
            val intent=Intent(context,ReturnedActivity::class.java)
            context.startActivity(intent)
        }
    }
}