package com.qcloud.suyuan.beans

/**
 * Description: 首页报表
 * Author: gaobaiqiang
 * 2018/3/19 下午7:19.
 */
class MainFormBean {
    var alarm: MainAlarmBean? = null    // 告警提示
    var todayBusiness: TodayFormBean? = null    // 今日营业

    override fun toString(): String {
        return "MainFormBean(alarm=$alarm, todayBusiness=$todayBusiness)"
    }
}