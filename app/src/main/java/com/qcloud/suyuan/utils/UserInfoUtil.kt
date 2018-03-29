package com.qcloud.suyuan.utils

import com.qcloud.qclib.callback.DataCallback
import com.qcloud.suyuan.beans.StoreBean
import com.qcloud.suyuan.beans.StoreInfoBean
import com.qcloud.suyuan.beans.UserBean
import com.qcloud.suyuan.model.impl.StoreModelImpl
import com.qcloud.suyuan.realm.RealmHelper
import timber.log.Timber

/**
 * Description: 用户工具类
 * Author: gaobaiqiang
 * 2018/3/22 下午2:43.
 */
object UserInfoUtil {
    /**
     * 保存用户信息
     * */
    fun saveUser(userBean: UserBean?) {
        if (userBean != null) {
            RealmHelper.instance.addOrUpdateBean(userBean)
        }
    }

    /**
     * 获取用户
     * */
    fun getUser(): UserBean? {
        return RealmHelper.instance.queryBeanById(UserBean::class.java, "saveId", "suyuan_user")
    }

    /**
     * 删除用户
     * */
    fun delUser() {
        return RealmHelper.instance.deleteBeanById(UserBean::class.java, "saveId", "suyuan_user")
    }

    /**
     * 保存门店信息
     * */
    fun saveStore(storeBean: StoreInfoBean?) {
        if (storeBean != null) {
            RealmHelper.instance.addOrUpdateBean(storeBean)
        }
    }

    /**
     * 获取门店
     * */
    fun getStore(): StoreInfoBean? {
        return RealmHelper.instance.queryBeanById(StoreInfoBean::class.java, "saveId", "suyuan_store")
    }

    /**
     * 删除门店
     * */
    fun delStore() {
        return RealmHelper.instance.deleteBeanById(StoreInfoBean::class.java, "saveId", "suyuan_store")
    }

    /**
     * 获取门店信息
     * */
    fun loadStore() {
        StoreModelImpl().getInfo(object : DataCallback<StoreBean> {
            override fun onSuccess(t: StoreBean?, message: String?) {
                if (t?.store != null) {
                    saveStore(t.store)
                }
            }

            override fun onError(status: Int, message: String) {
                Timber.e("loadStore failure : $message")
            }
        })
    }
}