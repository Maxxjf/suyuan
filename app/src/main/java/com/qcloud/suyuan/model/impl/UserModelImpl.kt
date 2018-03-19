//package com.qcloud.suyuan.model.impl
//
//import com.qcloud.jiahua.base.BaseApplication
//import com.qcloud.jiahua.beans.*
//import com.qcloud.jiahua.model.IUserModel
//import com.qcloud.jiahua.net.IUserApi
//import com.qicloud.library.network.BaseAPI
//import com.qicloud.library.network.DataCallback
//import com.qicloud.library.network.ReturnDataBean
//import java.util.*
//
///**
// * 类说明：用户有关
// * Author: Kuzan
// * Date: 2017/6/6 14:35.
// */
//class UserModelImpl : IUserModel {
//    var mApi: IUserApi? = null
//    var map: HashMap<String, Any> = HashMap()
//
//    init {
//        mApi = BaseApplication.client?.createService(IUserApi::class.java)
//        map = BaseAPI.getDefaultMap()
//    }
//
//    /**
//     * 用户登录
//     *
//     * @param loginAccount    用户账号
//     * @param password  账号密码
//     * @param callback
//     *
//     * @time 2017/6/6 15:13
//     */
//    override fun login(loginAccount: String, password: String, callback: DataCallback<LoginBean>) {
//        map = BaseAPI.getAppMap()
//        map.put("loginAccount", loginAccount)
//        map.put("password", password)
//
//        BaseAPI.dispose(mApi?.login(map), callback)
//    }
//
//    /**
//     * 获取验证码
//     *
//     * @param mobile    手机号
//     * @param callback
//     *
//     * @time 2017/6/7 10:07
//     */
//    override fun getCode(mobile: String, callback: DataCallback<ReturnEmptyBean>) {
//        map = BaseAPI.getAppMap()
//        map.put("mobile", mobile)
//
//        BaseAPI.dispose(mApi?.getCode(map), callback)
//    }
//
//    /**
//     * 重置密码
//     *
//     * @param mobile    手机号
//     * @param code      验证码
//     * @param password  新密码
//     * @param callback
//     *
//     * @time 2017/6/7 10:07
//     */
//    override fun resetPassword(mobile: String, code: String, password: String, callback: DataCallback<ReturnEmptyBean>) {
//        map = BaseAPI.getAppMap()
//        map.put("mobile", mobile)
//        map.put("code", code)
//        map.put("password", password)
//
//        BaseAPI.dispose(mApi?.resetPassword(map), callback)
//    }
//
//    /**
//     * 获取用户信息
//     *
//     * @param callback
//     *
//     * @time 2017/6/7 14:39
//     */
//    override fun loadUserInfo(callback: DataCallback<ReturnObjectBean<UserBean>>) {
//        map = BaseAPI.getAppMap()
//
//        BaseAPI.dispose(mApi?.getUserInfo(map), callback)
//    }
//
//    /**
//     * 退出登录
//     *
//     * @param callback
//     *
//     * @time 2017/6/7 17:38
//     */
//    override fun logout(callback: DataCallback<ReturnEmptyBean>) {
//        map = BaseAPI.getAppMap()
//
//        BaseAPI.dispose(mApi?.logout(map), callback)
//    }
//
//    override fun changePassword(oldPwd: String, newPwd: String, callback: DataCallback<ReturnEmptyBean>) {
//        map = BaseAPI.getAppMap()
//        map.put("oldPwd", oldPwd)
//        map.put("newPwd", newPwd)
//
//        BaseAPI.dispose(mApi?.changePassword(map), callback)
//    }
//
//    /**
//     * 获取ws信息
//     *
//     * @time 2017/7/31 15:04
//     */
//    override fun getWsInfo(callback: DataCallback<WsInfoBean>) {
//        map = BaseAPI.getAppMap()
//
//        BaseAPI.dispose(mApi?.getWsInfo(map), callback)
//    }
//
//    /**
//     * 获取加油站员工列表
//     *
//     * @time 2017/9/18 20:09
//     */
//    override fun getStaffList(callback: DataCallback<ReturnDataBean<StaffBean>>) {
//        map = BaseAPI.getAppMap()
//
//        BaseAPI.dispose(mApi?.getStaffList(map), callback)
//    }
//
//    /**
//     * 设置员工推送
//     *
//     * @time 2017/9/18 20:09
//     */
//    override fun submitPushSet(ids: List<Long>, callback: DataCallback<ReturnEmptyBean>) {
//        // IdentityHashMap可以输入相同的key
//        var myMap: IdentityHashMap<String, Any> = IdentityHashMap()
//
//        map = BaseAPI.getAppMap()
//        myMap.put("qc_client_type", map["qc_client_type"])
//        myMap.put("qc_app_sign", map["qc_app_sign"])
//        myMap.put("qc_app_token", map["qc_app_token"])
//        myMap.put("qc_app_str", map["qc_app_str"])
//        myMap.put("format", map["format"])
//
//        if (ids.isNotEmpty()) {
//            for (id in ids) {
//                val sb: StringBuffer = StringBuffer()
//                sb.append("userIds")
//
//                myMap.put(String(sb), id)
//            }
//        }
//
//        BaseAPI.dispose(mApi?.submitPushSet(myMap), callback)
//    }
//}