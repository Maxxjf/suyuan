package com.qcloud.suyuan.realm

import android.annotation.SuppressLint
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import timber.log.Timber
import java.util.*

/**
 * 类说明：realm数据处理
 * Author: Kuzan
 * Date: 2018/3/12 16:36.
 */
class RealmHelper {

    init {
        initRealm()
    }

    /**
     * 初始化Realm
     */
    private fun initRealm() {
        val configuration = RealmConfiguration.Builder()
                .name(DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(Companion.REALM_VERSION)
                .build()

        try {
            mRealm = Realm.getInstance(configuration)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 新增与更新
     *
     * @param bean 继承RealmObject的实体类
     */
    fun <T : RealmObject> addOrUpdateBean(bean: T) {
        mRealm?.executeTransactionAsync(
                { realm -> realm.copyToRealmOrUpdate(bean) },
                { Timber.e("添加或更新成功") }
        ) { error -> Timber.e("添加或更新失败: $error") }
    }

    /**
     * 删除对象(表)
     *
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     */
    fun <T : RealmObject> deleteBeanById(clazz: Class<T>, fieldName: String, id: Long) {
        if (mRealm == null) {
            Timber.e("realm is null")
            return
        }

        val bean = mRealm!!.where(clazz).equalTo(fieldName, id).findFirst()

        if (bean != null) {
            mRealm!!.beginTransaction()
            bean.deleteFromRealm()
            mRealm!!.commitTransaction()
        }
    }

    /**
     * 删除对象(表)
     *
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     */
    fun <T : RealmObject> deleteBeanById(clazz: Class<T>, fieldName: String, id: String) {
        if (mRealm == null) {
            Timber.e("realm is null")
            return
        }

        val bean = mRealm!!.where(clazz).equalTo(fieldName, id).findFirst()

        if (bean != null) {
            mRealm!!.beginTransaction()
            bean.deleteFromRealm()
            mRealm!!.commitTransaction()
        }
    }

    /**
     * 查找所有
     *
     * @param clazz 继承RealmObject的实体类
     *
     * @return 对象列表
     */
    fun <T : RealmObject> queryBeans(clazz: Class<T>): List<T> {
        if (mRealm == null) {
            Timber.e("realm is null")
            return ArrayList()
        }
        val list = mRealm!!.where(clazz).findAll()

        return mRealm!!.copyFromRealm(list)
    }

    /**
     * 查找
     *
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     *
     * @return 查找的对象
     */
    fun <T : RealmObject> queryBeanById(clazz: Class<T>, fieldName: String, id: Long): T? {
        if (mRealm == null) {
            Timber.e("realm is null")
            return null
        }
        return mRealm!!.where(clazz).equalTo(fieldName, id).findFirst()
    }

    /**
     * 查找
     *
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     *
     * @return 查找的对象
     */
    fun <T : RealmObject> queryBeanById(clazz: Class<T>, fieldName: String, id: String): T? {
        if (mRealm == null) {
            Timber.e("realm is null")
            return null
        }
        return mRealm!!.where(clazz).equalTo(fieldName, id).findFirst()
    }

    /**
     * 查找
     *
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param value 数据库对应的值
     */
    fun <T : RealmObject> queryBeanByValue(clazz: Class<T>, fieldName: String, value: String): T? {
        if (mRealm == null) {
            Timber.e("realm is null")
            return null
        }
        return mRealm!!.where(clazz).equalTo(fieldName, value).findFirst()
    }

    /**
     * 查找列表
     *
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param value 数据库对应的值
     *
     * @return 含有value的列表
     */
    fun <T : RealmObject> queryListByValue(clazz: Class<T>, fieldName: String, value: String): List<T> {
        if (mRealm == null) {
            Timber.e("realm is null")
            return ArrayList()
        }
        val list = mRealm!!.where(clazz).equalTo(fieldName, value).findAll()
        return mRealm!!.copyFromRealm(list)
    }

    /**
     * 查找所有列表
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param value 数据库对应的值
     *
     * @return 含有value的列表
     */
    fun <T : RealmObject> queryListByValue(clazz: Class<T>, fieldName: String, value: Boolean): List<T> {
        if (mRealm == null) {
            Timber.e("realm is null")
            return ArrayList()
        }
        val list = mRealm!!.where(clazz).equalTo(fieldName, value).findAll()
        return mRealm!!.copyFromRealm(list)
    }

    /**
     * 查找所有列表
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param value 数据库对应的值
     *
     * @return 含有value的列表
     */
    fun <T : RealmObject> queryListByValue(clazz: Class<T>, fieldName: String, value: Int): List<T> {
        if (mRealm == null) {
            Timber.e("realm is null")
            return ArrayList()
        }
        val list = mRealm!!.where(clazz).equalTo(fieldName, value).findAll()
        return mRealm!!.copyFromRealm(list)
    }

    /**
     * 查找所有列表
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param value 数据库对应的值
     *
     * @return 含有value的列表
     */
    fun <T : RealmObject> queryListByValue(clazz: Class<T>, fieldName: String, value: Long): List<T> {
        if (mRealm == null) {
            Timber.e("realm is null")
            return ArrayList()
        }
        val list = mRealm!!.where(clazz).equalTo(fieldName, value).findAll()
        return mRealm!!.copyFromRealm(list)
    }

    /**
     * 关闭Realm
     */
    fun closeRealm() {
        Companion.mRealm?.let {
            Companion.mRealm?.close()
            Companion.mRealm = null
        }
        mHelper?.let {
            mHelper = null
        }
    }

    /**
     * 静态内部类
     * 一个ClassLoader下同一个类只会加载一次，保证了并发时不会得到不同的对象
     * */
    object RealmHolder {
        var instance: RealmHelper = RealmHelper()
    }

    companion object {
        /**Realm名称*/
        private val DB_NAME = "Suyuan.realm"
        /**Realm版本 */
        private val REALM_VERSION: Long = 1

        private var mHelper: RealmHelper? = null
        @SuppressLint("StaticFieldLeak")
        private var mRealm: Realm? = null     // mRealm可能为空，注意判断

        /**
         * 实现懒加载
         * 在调用getInstance()方法时才会去初始化mInstance
         */
        val instance: RealmHelper
            get() = RealmHolder.instance
    }
}