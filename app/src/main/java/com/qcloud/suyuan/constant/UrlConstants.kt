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

    /**
     * 用户有关
     * */
    /**登录**/
    const val LOGIN = URL_PREFIX_APP + "login"
    /**退出登录*/
    const val LOGOUT = URL_PREFIX_APP + "logout"
    /**获取验证码**/
    const val GET_CODE = URL_PREFIX_APP + "getCode"
    /**修改密码**/
    const val FORGET_PASSWORD = URL_PREFIX_APP + "forgetPassword"

    /**
     * 首页数据有关
     * */
    /** 首页数据 */
    const val MAIN_FORM = URL_PREFIX_APP + "index/indexData"
    /**获取库存告警列表*/
    const val GET_STOCK_WARN_LIST = URL_PREFIX_APP + "index/stockList"
    /**获取有效期告警列表*/
    const val GET_VALID_WARN_LIST = URL_PREFIX_APP + "index/indateList"
    /**搜索批次码*/
    const val BATCH_SEARCH = URL_PREFIX_APP + "index/searchByBatchcode"
    /**搜索产品条形码*/
    const val BAR_CODE_SEARCH = URL_PREFIX_APP + "index/searchByBarcode"
    /**检查是否有新版本*/
    const val CHECK_VERSION = URL_PREFIX_APP + "index/checkVersion"
    /**下载新版本*/
    const val DOWNLOAD_APP = URL_PREFIX_APP + "index/downloadApp"

    /**销售报表*/
    const val  GET_SALE_STATEMENT = URL_PREFIX_APP+"statement/statement"

    /**
     * 退货有关
     * */
    /**扫码查询*/
    const val SCAN_CODE = URL_PREFIX_APP + "salesReturn/scanCode"
    /**退货*/
    const val SALES_RETURN = URL_PREFIX_APP + "salesReturn/salesReturn"
    /**获取退货记录列表*/
    const val GET_RETURNED_RECORD_LIST = URL_PREFIX_APP + "salesReturn/salesReturnList"

    /**
     * 库存有关
     * */
    /**获取产品分类列表*/
    const val GET_CLASSIFY_LIST = URL_PREFIX_APP + "stock/classifyList"
    /**获取库存(产品)列表*/
    const val GET_STOCK_LIST = URL_PREFIX_APP + "stock/list"
    /**获取商品详情*/
    const val GET_PRODUCT_DETAILS = URL_PREFIX_APP + "stock/detail"
    /**修改价格*/
    const val EDIT_PRICE = URL_PREFIX_APP + "stock/editPrice"
    /**调整警告线*/
    const val EDIT_CORDON = URL_PREFIX_APP + "stock/editCardon"

    /**
     * 赊账有关
     * */
    /**赊账列表**/
    const val GET_CREDIT_LIST= URL_PREFIX_APP+"onCredit/list"
    /**赊账详细**/
    const val GET_CREDIT_INFO= URL_PREFIX_APP+"onCredit/info"
    /**赊账还款**/
    const val REPAYMENT= URL_PREFIX_APP+"onCredit/repayment"

    /**
     * 进货入库有关
     * */
    /**搜索产品入库*/
    const val STORAGE_SEARCH = URL_PREFIX_APP + "storage/search"
    /**产品入库*/
    const val STORAGE_SAVE = URL_PREFIX_APP + "storage/save"
    /**有效期告警列表撤消入库接口*/
    const val OUT_STORAGE_IN_VALID_WARN = URL_PREFIX_APP + "storage/output"
    /**出库商品查询*/
    const val OUT_STORAGE_SEARCH = URL_PREFIX_APP + "storage/searchByCode"

    /**
     * 销售有关
     * */
    /**销售列表**/
    const val SALE_LIST= URL_PREFIX_APP+"sale/list"
    /**销售详情**/
    const val SALE_INFO= URL_PREFIX_APP+"sale/info"
    /**卖货搜索*/
    const val SALE_SEARCH_PRODUCT = URL_PREFIX_APP + "sale/search"
    /**结算*/
    const val SALE_SETTLEMENT = URL_PREFIX_APP + "sale/settlement"

    /**
     * 门店有关
     * */
    /**门店信息*/
    const val STORE_INFO = URL_PREFIX_APP + "store/getInfo"
    /**我的门店-修改密码*/
    const val STORE_EDIT_PASSWORD = URL_PREFIX_APP + "store/editPassword"
    /**我的门店-修改信息*/
    const val STORE_EDIT_INFO = URL_PREFIX_APP + "store/editStore"
    /**供应商列表*/
    const val SUPPLIER_LIST = URL_PREFIX_APP + "store/supplierList"
    /**供应商保存或更改*/
    const val SUPPLIER_SAVE_UPDATE = URL_PREFIX_APP + "store/supplierSaveUpdate"

    /**
     * 产品有关
     * */
    /**创建修改私有产品*/
    const val EDIT_MY_PRODUCT = URL_PREFIX_APP + "goods/toAdd"
    /**获取生产厂家*/
    const val GET_FACTORY = URL_PREFIX_APP + "goods/getFactory"
    /**检验条码是否重复*/
    const val IS_BAR_CODE_REPEAT = URL_PREFIX_APP + "goods/isRepeat"
    /**创建修改私有产品-下一步*/
    const val CREATE_PRODUCT_NEXT = URL_PREFIX_APP + "goods/toNextStep"
    /**保存私有产品*/
    const val ADD_PRODUCT = URL_PREFIX_APP + "goods/add"

    /**
     * 溯源有关
     * */
    /**溯源记录*/
    const val SUYUAN_LIST = URL_PREFIX_APP + "traceability/list"
    /**溯源详情*/
    const val SUYUAN_SEARCH = URL_PREFIX_APP + "traceability/info"

}