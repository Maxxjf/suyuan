package com.qcloud.suyuan.constant

/**
 * 类说明：接口Url常量
 * Author: Kuzan
 * Date: 2018/3/12 15:18.
 */
object UrlConstants {
    /**app请求前缀 */
    const val URL_PREFIX = "fep/"
    /**带app的url前缀 */
    const val URL_PREFIX_APP = URL_PREFIX + "app/"

    /**登录**/
    const val LOGIN = URL_PREFIX_APP + "login"

    /** 首页数据 */
    const val MAIN_FORM = URL_PREFIX_APP + "index/indexData"
    /**获取库存告警列表*/
    const val GET_STOCK_WARN_LIST = URL_PREFIX_APP + "index/stockList"
    /**获取有效期告警列表*/
    const val GET_VALID_WARN_LIST = URL_PREFIX_APP + "index/indateList"
}