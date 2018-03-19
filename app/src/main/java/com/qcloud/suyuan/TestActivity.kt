package com.qcloud.suyuan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.qcloud.qclib.utils.ScreenUtil
import timber.log.Timber

class TestActivity : AppCompatActivity() {

    var height: Int = 0
    var width: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        height = ScreenUtil.getScreenHeight(this)
        width = ScreenUtil.getScreenWidth(this)
        Timber.e("该设备的高为${height}，宽为${width}")
    }

}
