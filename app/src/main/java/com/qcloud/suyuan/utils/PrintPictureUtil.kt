package com.qcloud.suyuan.utils

import android.graphics.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

/**
 * 类说明：打印图片工具类
 * Author: Kuzan
 * Date: 2018/3/26 20:35.
 */
class PrintPictureUtil private constructor() {
    private var mCanvas: Canvas? = null
    private val mPaint: Paint = Paint()
    private var mBitmap: Bitmap? = null
    private var bitbuf: ByteArray? = null

    var mWidth: Int = 0
        private set
    var mLength: Int = 0

    /**
     * 初始化画布
     * */
    fun initCanvas(w: Int) {
        val h = 10 * w
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)
        mCanvas!!.drawColor(-1)
        mWidth = w
        bitbuf = ByteArray(mWidth / 8)
    }

    /**
     * 初始化画布
     * */
    fun initCanvas(w: Int, h: Int) {
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)
        mCanvas!!.drawColor(-1)
        mWidth = w
        bitbuf = ByteArray(mWidth / 8)
    }

    /**
     * 初始化画布
     * */
    fun initCanvas(w: Int, h: Int, bitmap: Bitmap) {
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(this.mBitmap!!)
        mCanvas!!.drawColor(-1)
        mWidth = w

        mCanvas!!.drawBitmap(bitmap, 0f, 0f, null as Paint?)
        mLength = bitmap.height

        bitbuf = ByteArray(mWidth / 8)
    }

    /**
     * 初始化画笔
     * */
    fun initPaint() {
        mPaint.isAntiAlias = true
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.STROKE
    }

    /**
     * 画图片
     * */
    fun drawImage(x: Float, y: Float, path: String) {
        try {
            val value = BitmapFactory.decodeFile(path)
            mCanvas?.drawBitmap(value, x, y, null as Paint?)
            if (mLength < y + value.height) {
                mLength = (y + value.height).toInt()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 画图片
     * */
    fun drawImage(x: Float, y: Float, bitmap: Bitmap) {
        try {
            mCanvas?.drawBitmap(bitmap, x, y, null as Paint?)
            if (mLength < y + bitmap.height) {
                mLength = (y + bitmap.height).toInt()
            }
        } catch (bitmap_ex: Exception) {
            bitmap_ex.printStackTrace()
        }
    }

    /**
     * 画文件图片
     * */
    fun printPng(filePath: String) {
        val f = File(filePath)
        var fos: FileOutputStream?
        val nbm = Bitmap.createBitmap(mBitmap!!, 0, 0, mWidth, mLength)

        try {
            fos = FileOutputStream(f)
            nbm.compress(Bitmap.CompressFormat.PNG, 50, fos)
        } catch (var5: FileNotFoundException) {
            var5.printStackTrace()
        }
    }

    /**
     * 打印图片
     * */
    fun printDraw(): ByteArray? {
        if (mLength == 0) {
            return null
        } else {
            val nbm = Bitmap.createBitmap(mBitmap, 0, 0, mWidth, mLength)
            val imgbuf = ByteArray(mWidth / 8 * mLength)
            var s = 0

            for (i in 0 until mLength) {
                var t = 0
                while (t < mWidth / 8) {
                    val c0 = nbm.getPixel(t * 8 + 0, i)
                    val p0: Byte = if (c0 == -1) 0 else 1
                    val c1 = nbm.getPixel(t * 8 + 1, i)
                    val p1: Byte = if (c1 == -1) 0 else 1

                    val c2 = nbm.getPixel(t * 8 + 2, i)
                    val p2: Byte = if (c2 == -1) 0 else 1

                    val c3 = nbm.getPixel(t * 8 + 3, i)
                    val p3: Byte = if (c3 == -1) 0 else 1

                    val c4 = nbm.getPixel(t * 8 + 4, i)
                    val p4: Byte = if (c4 == -1) 0 else 1

                    val c5 = nbm.getPixel(t * 8 + 5, i)
                    val p5: Byte = if (c5 == -1) 0 else 1

                    val c6 = nbm.getPixel(t * 8 + 6, i)
                    val p6: Byte = if (c6 == -1) 0 else 1

                    val c7 = nbm.getPixel(t * 8 + 7, i)
                    val p7: Byte = if (c7 == -1) 0 else 1

                    val value = p0 * 128 + p1 * 64 + p2 * 32 + p3 * 16 + p4 * 8 + p5 * 4 + p6 * 2 + p7.toInt()
                    bitbuf!![t] = value.toByte()
                    ++t
                }

                t = 0
                while (t < mWidth / 8) {
                    imgbuf[s] = this.bitbuf!![t]
                    ++s
                    ++t
                }
            }

            return imgbuf
        }
    }

    object PrintPictureHolder {
        var instance: PrintPictureUtil = PrintPictureUtil()
    }

    companion object {
        val instance: PrintPictureUtil
            get() = PrintPictureHolder.instance
    }
}