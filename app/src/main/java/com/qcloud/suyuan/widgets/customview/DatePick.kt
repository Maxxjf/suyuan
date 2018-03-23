package com.qcloud.suyuan.widgets.customview

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.View.OnClickListener
import android.widget.DatePicker
import android.widget.TextView
import java.util.*

@Suppress("DEPRECATION")
/**
 * 类型：DatePick
 * Author: iceberg
 * Date: 2018/3/23.
 *
 */
class DatePick @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var datePicker: DatePickerDialog? = null
    private var listener: OnDateChangeListener? = null
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0

    init {
        //初始化年月日
        var calendar = Calendar.getInstance()
        showDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))
        datePicker = DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                showDate(p1, p2 + 1, p3)
                listener?.onDateChange(p1, p2, p3)
            }
        }, year, month, day)
        setOnClickListener(OnClickListener { v ->
            datePicker!!.show()
        })
    }


    fun showDate(year: Int, month: Int, day: Int) {
        this.year = year
        this.month = month
        this.day = day
        setText("${year}-${month}-${day}")
    }

    fun setOnDateChangeListener(listener: OnDateChangeListener) {
        this.listener = listener
    }

    interface OnDateChangeListener {
        fun onDateChange(year: Int, mouth: Int, day: Int)
    }

}