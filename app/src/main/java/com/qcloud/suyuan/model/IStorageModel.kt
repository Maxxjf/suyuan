package com.qcloud.suyuan.model

import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.EmptyReturnBean

/**
 * 类说明：库存有关
 * Author: Kuzan
 * Date: 2018/3/27 15:18.
 */
interface IStorageModel {
    /**
     * 有效期告警列表撤消入库接口
     * */
    fun outStorageInValidWarn(id: String, number: Int, callback: DataCallback<EmptyReturnBean>)
}