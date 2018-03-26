package com.qcloud.suyuan.utils

import android.content.Context
import android.support.annotation.NonNull
import timber.log.Timber
import java.io.*

/**
 * Description: NFC身份证识别工具类
 * Author: gaobaiqiang
 * 2018/3/25 下午10:03.
 */
object NFCUtil {
    /**
     * 发送命令
     * */
    fun doExec(cmd: String): String {
        var process: Process?
        var dos: DataOutputStream? = null

        Timber.e("exec command: $cmd")
        try {
            process = Runtime.getRuntime().exec("su")
            dos = DataOutputStream(process.outputStream)
            dos.writeBytes(cmd + "\n")
            dos.writeBytes("exit\n")
            dos.flush()
            process.waitFor()
        } catch (e: Exception) {
            Timber.d("Unexpected error - Here is what I know: ${e.message}")
        } finally {
            dos.let {
                dos?.close()
            }
        }
        return cmd
    }

    /**
     * 获取assess的资源
     * */
    fun copyAssets(@NonNull context: Context, assetDir: String, dir: String) {
        val files: Array<String>
        try {
            files = context.resources.assets.list(assetDir)
        } catch (e1: IOException) {
            return
        }

        val mWorkingPath = File(dir)
        if (!mWorkingPath.exists()) {
            mWorkingPath.mkdirs()
        }

        for (i in files.indices) {
            try {
                val fileName = files[i]
                // we make sure file name not contains '.' to be a folder.
                if (!fileName.contains(".")) {
                    if (assetDir.isEmpty()) {
                        copyAssets(context, fileName, "$dir$fileName/")
                    } else {
                        copyAssets(context, "$assetDir/$fileName", "$dir$fileName/")
                    }
                    continue
                }
                val outFile = File(mWorkingPath, fileName)
                if (outFile.exists()) {
                    outFile.delete()
                }
                var iStream: InputStream = if (assetDir.isNotEmpty()) {
                    context.assets.open("$assetDir/$fileName")
                } else {
                    context.assets.open(fileName)
                }
                val out = FileOutputStream(outFile)

                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int = iStream.read(buf)
                while (len > 0) {
                    out.write(buf, 0, len)
                    len = iStream.read(buf)
                }
                iStream.close()
                out.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}