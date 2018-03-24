package com.qcloud.suyuan.beans

/**
 * Description: 产品返回
 * Author: gaobaiqiang
 * 2018/3/24 下午11:13.
 */
class ProductReturnBean {
    var classifyList: List<ProductClassifyBean>? = null     // 分类集合
    var list: List<ProductBean>? = null     // 产品列表
    var totalRow: Int = 0   // 总条数
}