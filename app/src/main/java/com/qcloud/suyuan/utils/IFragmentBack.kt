package com.qcloud.suyuan.utils

import com.qcloud.suyuan.base.BaseFragment


/**
 * 类说明：点击Fragment返回键
 *      凡Activity里嵌套fragment的都要继承该接口
 * Author: Kuzan
 * Date: 2018/3/12 15:42.
 */
interface IFragmentBack {
    fun initSelectFragment(fragment: BaseFragment<*, *>)
}