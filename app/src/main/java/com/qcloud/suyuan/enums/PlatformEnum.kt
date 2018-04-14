package com.qcloud.suyuan.enums

/**
 * 类说明：平台请求数据
 * Author: Kuzan
 * Date: 2018/4/11 10:32.
 */
enum class PlatformEnum constructor(var key: Int, var value: String) {
    isPrivate(0, "私有产品"),
    isPlatform(1, "平台产品"),
    isAll(-1, "全部");

    companion object {
        fun getKey(value: String): Int {
            return when (value) {
                "私有产品" -> isPrivate.key
                "平台产品" -> isPlatform.key
                else -> isAll.key
            }
        }
    }
}