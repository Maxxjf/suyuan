package com.qcloud.suyuan.widgets.pop

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import com.qcloud.qclib.base.BasePopupWindow
import com.qcloud.suyuan.R
import com.qcloud.suyuan.adapters.DropDownAdapter

/**
 * Description: 下拉带按钮弹窗
 * Author: gaobaiqiang
 * 2018/3/22 下午11:18.
 */
class DropDownBtnPop(mContext: Context, val list: List<String>, xWidth: Int) : BasePopupWindow(mContext) {
    private var list_value: RecyclerView? = null
    private var mAdapter: DropDownAdapter? = null

    var onItemClickListener: OnItemClickListener? = null

    override val viewId: Int
        get() = R.layout.pop_drop_down_with_btn

    override val animId: Int
        get() = com.qcloud.qclib.R.style.AnimationPopupWindow_up_to_bottom

    init {
        width = xWidth
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        initView()
    }

    private fun initView() {
        list_value = mView?.findViewById(R.id.list_value)
        list_value?.layoutManager = LinearLayoutManager(mContext)

        mAdapter = DropDownAdapter(mContext)
        list_value?.adapter = mAdapter
        mAdapter?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val value = mAdapter?.mList?.get(position) ?: ""
            onItemClickListener?.onItemClick(position, value)
            dismiss()
        }
        mAdapter?.replaceList(list)

        val btn_add = mView?.findViewById<LinearLayout>(R.id.btn_add)
        btn_add?.setOnClickListener {
            onPopWindowViewClick?.onViewClick(it)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, value: String)
    }
}