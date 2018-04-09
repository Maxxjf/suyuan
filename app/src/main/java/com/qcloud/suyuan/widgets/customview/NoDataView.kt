package com.qcloud.suyuan.widgets.customview

import android.content.Context
import android.util.AttributeSet
import com.qcloud.qclib.utils.ApiReplaceUtil
import com.qcloud.qclib.widget.customview.BaseEmptyView
import com.qcloud.suyuan.R

/**
 * Description: 空布局
 * Author: gaobaiqiang
 * 2018/3/21 下午5:25.
 */
class NoDataView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BaseEmptyView(context, attrs, defStyleAttr) {

    override val imageIcon: Int
        get() = R.drawable.bmp_list_empty

    override val tipColor: Int
        get() = ApiReplaceUtil.getColor(mContext, R.color.colorSubTitle)

    override val tipSize: Float
        get() = mContext.resources.getDimension(R.dimen.textSize)

    override val defaultTip: String
        get() = mContext.resources.getString(R.string.tip_no_any_data)

    override val padTop: Int
        get() = 20
}