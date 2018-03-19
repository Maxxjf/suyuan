package com.qcloud.suyuan.enums

/**
 * Description:
 * Author: gaobaiqiang
 * 2018/3/19 下午9:15.
 */
enum class SearchType constructor(var key: Int, var title: String) {
    searchSuyuan(1, "扫溯源码搜索"),
    searchBatch(2, "扫批次条形码搜索"),
    searchProduct(3, "扫产品条形码搜索");

    companion object {
        fun getTitle(key: Int): String {
            return when (key) {
                1 -> searchSuyuan.title
                2 -> searchBatch.title
                3 -> searchProduct.title
                else -> searchSuyuan.title
            }
        }
    }
}