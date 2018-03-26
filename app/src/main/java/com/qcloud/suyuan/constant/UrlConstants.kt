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
    /**退出登录*/
    const val LOGOUT = URL_PREFIX_APP + "logout"
    /**获取验证码**/
    const val GET_CODE = URL_PREFIX_APP + "getCode"
    /**修改密码**/
    const val FORGET_PASSWORD = URL_PREFIX_APP + "forgetPassword"

    /** 首页数据 */
    const val MAIN_FORM = URL_PREFIX_APP + "index/indexData"
    /**获取库存告警列表*/
    const val GET_STOCK_WARN_LIST = URL_PREFIX_APP + "index/stockList"
    /**获取有效期告警列表*/
    const val GET_VALID_WARN_LIST = URL_PREFIX_APP + "index/indateList"

    /**扫码查询*/
    const val SCAN_CODE = URL_PREFIX_APP + "salesReturn/scanCode"
    /**退货*/
    const val SALES_RETURN = URL_PREFIX_APP + "salesReturn/salesReturn"
    /**获取退货记录列表*/
    const val GET_RETURNED_RECORD_LIST = URL_PREFIX_APP + "salesReturn/salesReturnList"

    /**获取库存(产品)列表*/
    const val GET_STOCK_LIST = URL_PREFIX_APP + "stock/list"
    /**获取商品详情*/
    const val GET_PRODUCT_DETAILS = URL_PREFIX_APP + "stock/detail"
    /**修改价格*/
    const val EDIT_PRICE = URL_PREFIX_APP + "stock/editPrice"
    /**调整警告线*/
    const val EDIT_CORDON = URL_PREFIX_APP + "stock/editCardon"

    /**赊账列表**/
    const val GET_CREDIT_LIST= URL_PREFIX_APP+"onCredit/list"
    /**赊账详细**/
    const val GET_CREDIT_INFO= URL_PREFIX_APP+"onCredit/info"


}