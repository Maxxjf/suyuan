package com.qcloud.suyuan.beans

/**
 * Description: 产品属性
 * Author: gaobaiqiang
 * 2018/3/30 下午5:36.
 */
class ProductAttrBean {
    var attributeName: ProductAttrNameBean? = null              // 属性，列表的标题显示内容
    var attributeValues: List<ProductAttrValueBean>? = null     // 当属性为下拉框时的集合
    var attrType: List<String>? = null                          // 当属性为表格时的表头
    var value: String? = null                                   // 当属性为输入框或下拉框时的默认值(修改的时候有用到)
    //var attrList: List<>                                      // 当属性为表格时的表格数据(修改的时候有用到)
}