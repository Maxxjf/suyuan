package com.qcloud.suyuan.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.qcloud.suyuan.R
import com.qcloud.suyuan.ui.goods.widget.StockWarnFragment
import com.qcloud.suyuan.ui.goods.widget.ValidWarnFragment
import com.shizhefei.view.indicator.IndicatorViewPager

/**
 * Description: 告警切换适配器
 * Author: gaobaiqiang
 * 2018/3/20 下午9:11.
 */
class WarnViewPagerAdapter(context: Context, fragmentManager: FragmentManager): IndicatorViewPager.IndicatorFragmentPagerAdapter(fragmentManager) {

    private val inflater = LayoutInflater.from(context)
    val list: MutableList<String> = ArrayList()

    private var stockFragment: StockWarnFragment? = null
    private var validFragment: ValidWarnFragment? = null

    override fun getCount(): Int {
        return list.size
    }

    override fun getViewForTab(position: Int, convertView: View?, container: ViewGroup): View {
        val value = list[position]
        val rootView = convertView ?: inflater.inflate(R.layout.item_of_warn_view_pager, container, false)
        val textView: TextView = rootView as TextView
        textView.text = value

        return rootView
    }

    override fun getFragmentForPage(position: Int): Fragment {
        return when (position) {
            1 -> {
                // 有效期告警
                if (validFragment == null) {
                    validFragment = ValidWarnFragment()
                }
                validFragment!!
            }
            else -> {
                // 库存告警
                if (stockFragment == null) {
                    stockFragment = StockWarnFragment()
                }
                stockFragment!!
            }
        }
    }

    fun replaceList(list: List<String>?) {
        if (list != null && list.isNotEmpty()) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }
}