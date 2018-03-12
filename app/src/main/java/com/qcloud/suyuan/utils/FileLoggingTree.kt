package com.qcloud.suyuan.utils

import android.util.Log
import com.qcloud.qclib.utils.FileUtil
import com.qcloud.qclib.utils.StringUtil
import timber.log.Timber
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * 类说明：保存日志文档
 * Author: Kuzan
 * Date: 2018/3/12 15:03.
 */
class FileLoggingTree: Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
        val cacheDiaPath = FileUtil.getSdCardPath()
        if (StringUtil.isBlank(cacheDiaPath)) {
            return
        }
        val file = File(cacheDiaPath + "/log.txt")
        Log.v("dyp", "file.path:" + file.absolutePath + ",message:" + message)
        val writer: FileWriter
        var bufferedWriter: BufferedWriter? = null
        try {
            writer = FileWriter(file)
            bufferedWriter = BufferedWriter(writer)
            bufferedWriter.write(message)
            bufferedWriter.flush()

        } catch (e: IOException) {
            Log.v("dyp", "存储文件失败")
            e.printStackTrace()
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }
}