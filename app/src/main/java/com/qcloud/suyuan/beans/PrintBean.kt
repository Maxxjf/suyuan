package com.qcloud.suyuan.beans

import android.support.annotation.DrawableRes
import com.qcloud.qclib.utils.StringUtil

/**
 * 类说明：打印内容
 * Author: Kuzan
 * Date: 2018/3/27 9:43.
 */
class PrintBean {
    var type: Int = 0   // 类型 0小票文本 1条形码 2图片
    var barCode: String? = null     // 条形码
        get() = if (StringUtil.isBlank(field)) "" else field
    @DrawableRes        // 打印图片
    var imageRes: Int = 0
    var content: PrintContentBean? = null
}