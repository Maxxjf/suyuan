package com.qcloud.suyuan.beans

/**
 * 类型：s
 * Author: iceberg
 * Date: 2018/3/16.
 * 用户实体
 */


class UserBean {
    /**
     * loginState : 1
     * token : 3dce1f21-32ce-4a6b-802f-1c769525b408
     * user : {"cityCode":"440400000000","createTime":"测试内容1o23","districtCode":"440402000000","email":"测试内容v10i","headImage":"测试内容2wlb","id":"374604013921894400","idCard":"测试内容wlx3","isRemove":0,"lastTime":"测试内容1w8n","mobile":"15656565656","name":"dorry","nickname":"测试内容l70j","provinceCode":"440000000000","pwd":"e10adc3949ba59abbe56e057f20f883e","state":1,"userNumber":"测试内容3b47"}
     */

    var loginState: Int = 0
    var token: String? = null
    var user: UserBean? = null

    class UserBean {
        /**
         * cityCode : 440400000000
         * createTime : 测试内容1o23
         * districtCode : 440402000000
         * email : 测试内容v10i
         * headImage : 测试内容2wlb
         * id : 374604013921894400
         * idCard : 测试内容wlx3
         * isRemove : 0
         * lastTime : 测试内容1w8n
         * mobile : 15656565656
         * name : dorry
         * nickname : 测试内容l70j
         * provinceCode : 440000000000
         * pwd : e10adc3949ba59abbe56e057f20f883e
         * state : 1
         * userNumber : 测试内容3b47
         */

        var cityCode: String? = null
        var createTime: String? = null
        var districtCode: String? = null
        var email: String? = null
        var headImage: String? = null
        var id: String? = null
        var idCard: String? = null
        var isRemove: Int = 0
        var lastTime: String? = null
        var mobile: String? = null
        var name: String? = null
        var nickname: String? = null
        var provinceCode: String? = null
        var pwd: String? = null
        var state: Int = 0
        var userNumber: String? = null
    }
}
