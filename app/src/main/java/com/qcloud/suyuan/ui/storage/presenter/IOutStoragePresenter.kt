package com.qcloud.suyuan.ui.storage.presenter

/**
 * Description: 撤消入库
 * Author: gaobaiqiang
 * 2018/3/15 上午12:45.
 */
interface IOutStoragePresenter {
    fun search(keyword: String)
    fun outStorage(id: String, number: Int)
}