package com.qcloud.suyuan.widgets.pop

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.AdapterView
import com.qcloud.qclib.base.BasePopupWindow
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.DropDownAdapter

/**
 * Description: 下拉弹窗
 * Author: gaobaiqiang
 * 2018/3/22 下午11:18.
 */
class DropDownPop(mContext: Context) : BasePopupWindow(mContext) {
    private var list_value: RecyclerView? = null
    private var mAdapter: DropDownAdapter? = null

    var onItemClickListener: OnItemClickListener? = null

    override val viewId: Int
        get() = R.layout.pop_drop_down

    override val animId: Int
        get() = com.qcloud.qclib.R.style.AnimationPopupWindow_up_to_bottom

    override fun initAfterViews() {
        list_value = mView.findViewById(R.id.list_value)
        list_value?.layoutManager = LinearLayoutManager(mContext)

        mAdapter = DropDownAdapter(mContext)
        list_value?.adapter = mAdapter
        mAdapter?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val value = mAdapter?.mList?.get(position) ?: ""
            onItemClickListener?.onItemClick(position, value)
        }
    }

    fun replaceList(list: List<String>) {
        if (list.isNotEmpty()) {
            mAdapter?.replaceList(list)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, value: String)
    }
}