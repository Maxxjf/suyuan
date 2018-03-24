package com.qcloud.suyuan.utils

import android.graphics.Bitmap
import android.support.annotation.NonNull
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

/**
 * Description: 条形码管理工具
 * Author: gaobaiqiang
 * 2018/3/24 上午10:19.
 */
object BarCodeUtil {

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param width 二维码宽度
     * @param height 二维码高度
     * */
    fun createQrCode(@NonNull content: String, width: Int, height: Int): Bitmap? {
        if (width == 0 || height == 0) {
            return null
        }
        val writer = MultiFormatWriter()

        return try {
            val matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height)
            val encoder = BarcodeEncoder()
            encoder.createBitmap(matrix)
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 生成条形码
     *
     * @param content 内容
     * @param width 条形码宽度
     * @param height 条形码高度
     * */
    fun createBarCode(@NonNull content: String, width: Int, height: Int): Bitmap? {
        if (width == 0 || height == 0) {
            return null
        }
        val writer = MultiFormatWriter()

        return try {
            val matrix = writer.encode(content, BarcodeFormat.CODE_39, width, height)
            val encoder = BarcodeEncoder()
            encoder.createBitmap(matrix)
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}