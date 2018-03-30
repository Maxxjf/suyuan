package com.qcloud.suyuan.widgets.pop

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.AdapterView
import com.qcloud.qclib.base.BasePopupWindow
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.DropDownAdapter
import com.qcloud.suyuan.beans.ProductClassifyBean

/**
 * Description: 下拉弹窗
 * Author: gaobaiqiang
 * 2018/3/22 下午11:18.
 */
class DropDownPop(mContext: Context, val mList: List<*>, xWidth: Int) : BasePopupWindow(mContext) {
    private var list_value: RecyclerView? = null
    private var mAdapter: DropDownAdapter? = null

    var onItemClickListener: OnItemClickListener? = null

    override val viewId: Int
        get() = R.layout.pop_drop_down

    override val animId: Int
        get() = com.qcloud.qclib.R.style.AnimationPopupWindow_up_to_bottom

    init {
        width = xWidth
        height = mContext.resources.getDimensionPixelSize(R.dimen.margin_40)
        initView()
    }

    private fun initView() {
        list_value = mView?.findViewById(R.id.list_value)
        list_value?.layoutManager = LinearLayoutManager(mContext)

        mAdapter = DropDownAdapter(mContext)
        list_value?.adapter = mAdapter
        mAdapter?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val value = mList[position]
            onItemClickListener?.onItemClick(position, value)
            dismiss()
        }
        mAdapter?.replaceList(disposeData())
    }

    /**
     * 解析数据
     * */
    private fun disposeData(): List<String> {
        val list: MutableList<String> = ArrayList()
        for (bean in mList) {
            if (bean is ProductClassifyBean) {
                list.add(bean.name)
            } else if (bean is String) {
                list.add(bean)
            }
        }
        return list
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, value: Any?)
    }
}