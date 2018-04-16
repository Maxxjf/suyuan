package com.qcloud.suyuan.constant

import android.os.Environment

/**
 * 类说明：应用常量
 * Author: Kuzan
 * Date: 2018/3/12 15:17.
 */
object AppConstants {
    /**列表分页size大小 */
    const val PAGE_SIZE = 10

    val loadColors = intArrayOf(0x7fff0000, 0x700ff000, 0x7f0000ff)

    // sd卡路径
    val SDPATH = Environment.getExternalStorageDirectory().path

    /**账号key值 */
    const val account = "KEY_ACCOUNT"
    /**密码key值 */
    const val password = "KEY_PASSWORD"
    /**记得密码是否选中 */
    const val isCheck = "KEY_FORGET_PASSWORD_IS_CHECK"

    /**身份证识别服务器地址和端口*/
    // "47.94.199.36"; // "192.168.1.217"; // "192.168.0.218";
    const val ID_VERIFY_SERVER_IP = "101.201.41.36"
    // 1010; // 12351; // 12337;
    const val ID_VERIFY_SERVER_PORT = 9001
    /**身份识别路径*/
    const val CIDSTRING = "/dev/ttyS3"
    /**身份识别串口*/
    const val BAUDRATT = 115200


}