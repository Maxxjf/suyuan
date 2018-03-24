package com.qcloud.suyuan.widgets.customview

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import java.util.*

/**
 * 类介绍：日期选择器
 * Author: iceberg
 * Date: 2018/3/23.
 */
class DatePickerButton @JvmOverloads constructor(
        private val mContext: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : AppCompatTextView(mContext, attrs, defStyleAttr) {

    // 日期选择器
    private var datePicker: DatePickerDialog? = null
    var onDateChangeListener: OnDateChangeListener? = null

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    private var yearFormat: String = "-"
    private var monthFormat: String = "-"
    private var dayFormat: String = ""

    init {
        initView()
    }

    /**
     * 初始化布局
     * */
    private fun initView() {
        //初始化年月日
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DAY_OF_MONTH)
        showDate(year, month, day)

        datePicker = DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            showDate(year, month + 1, day)
            onDateChangeListener?.onDateChange(year, month+1, day, formatDate(year, month+1, day))
        }, year, month, day)

        setOnClickListener {
            datePicker?.show()
        }
    }

    /**
     * 显示日期
     * */
    @SuppressLint("SetTextI18n")
    fun showDate(year: Int, month: Int, day: Int) {
        this.year = year
        this.month = month
        this.day = day
        text = "$year$yearFormat$month$monthFormat$day$dayFormat"
    }

    /**
     * 设置日期格式
     * */
    fun setDateFormate(yearFormat: String = "-", monthFormat: String = "", dayFormat: String = "") {
        this.yearFormat = yearFormat
        this.monthFormat = monthFormat
        this.dayFormat = dayFormat
    }

    /**
     * 获取日期
     * */
    private fun formatDate(year: Int, month: Int, day: Int): String = "$year$yearFormat$month$monthFormat$day$dayFormat"

    interface OnDateChangeListener {
        fun onDateChange(year: Int, mouth: Int, day: Int, dateStr: String)
    }
}