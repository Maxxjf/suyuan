package com.qcloud.suyuan.widgets.dialog

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.support.annotation.NonNull
import android.view.Gravity
import android.view.View
import android.widget.Button
import com.qcloud.qclib.utils.DateUtil
import com.qcloud.qclib.utils.ScreenUtil
import com.qcloud.suyuan.R
import com.qcloud.suyuan.widgets.customview.DatePickerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 类说明：自定义日期选择器
 * Author: Kuzan
 * Date: 2018/4/12 18:35.
 */
class DatePicker(@NonNull private val mContext: Context) {

    private var datePickerDialog: Dialog? = null
    private var pvYear: DatePickerView? = null
    private var pvMonth: DatePickerView? = null
    private var pvDay: DatePickerView? = null
    private var btnCancel: Button? = null
    private var btnConfirm: Button? = null

    private var year: MutableList<String> = ArrayList()
    private var month: MutableList<String> = ArrayList()
    private var day: MutableList<String> = ArrayList()
    private var startYear: Int = 0
    private var startMonth: Int = 0
    private var startDay: Int = 0
    private var endYear: Int = 0
    private var endMonth: Int = 0
    private var endDay: Int = 0
    private var spanYear: Boolean = false
    private var spanMon: Boolean = false
    private var spanDay: Boolean = false
    private var selectedCalender: Calendar = Calendar.getInstance()
    private var startCalendar: Calendar = Calendar.getInstance()
    private var endCalendar: Calendar = Calendar.getInstance()

    var onDateSelectListener: OnDateSelectListener? = null

    init {
        // 设置开始时间和结束时间
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        try {
            startCalendar.time = sdf.parse("1990-01-01")
            endCalendar.time = sdf.parse("2050-12-31")
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        initDialog()
        initView()
    }

    private fun initDialog() {
        if (datePickerDialog == null) {
            datePickerDialog = Dialog(mContext, R.style.InputDialog)
            datePickerDialog?.setContentView(R.layout.dialog_date_picker)
            datePickerDialog?.setCancelable(true)
            val window = datePickerDialog!!.window
            val lp = window!!.attributes
            lp.width = ScreenUtil.getScreenWidth(mContext) * 7 / 16 //设置宽度
            lp.gravity = Gravity.CENTER
            window.attributes = lp
        }
    }

    private fun initView() {
        pvYear = datePickerDialog?.findViewById(R.id.pv_year)
        pvMonth = datePickerDialog?.findViewById(R.id.pv_month)
        pvDay = datePickerDialog?.findViewById(R.id.pv_day)
        btnCancel = datePickerDialog?.findViewById(R.id.btn_cancel)
        btnConfirm = datePickerDialog?.findViewById(R.id.btn_ok)

        btnCancel?.setOnClickListener { datePickerDialog!!.dismiss() }

        btnConfirm?.setOnClickListener {
            onDateSelectListener?.onSelect(selectedCalender)
            datePickerDialog!!.dismiss()
        }

        initParameter()
        initTimer()
        addListener()
        setSelectedTime(DateUtil.getCurrTime("yyyy-MM-dd"))
    }

    private fun initParameter() {
        startYear = startCalendar.get(Calendar.YEAR)
        startMonth = startCalendar.get(Calendar.MONTH) + 1
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH)
        endYear = endCalendar.get(Calendar.YEAR)
        endMonth = endCalendar.get(Calendar.MONTH) + 1
        endDay = endCalendar.get(Calendar.DAY_OF_MONTH)
        spanYear = startYear != endYear
        spanMon = !spanYear && startMonth != endMonth
        spanDay = !spanMon && startDay != endDay
        selectedCalender.time = startCalendar.time
    }

    private fun initTimer() {
        initArrayList()
        when {
            spanYear -> {
                for (i in startYear..endYear) {
                    year.add(i.toString())
                }
                for (i in startMonth..MAX_MONTH) {
                    month.add(formatTimeUnit(i))
                }
                for (i in startDay..startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    day.add(formatTimeUnit(i))
                }

            }
            spanMon -> {
                year.add(startYear.toString())
                for (i in startMonth..endMonth) {
                    month.add(formatTimeUnit(i))
                }
                for (i in startDay..startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    day.add(formatTimeUnit(i))
                }
            }
            spanDay -> {
                year.add(startYear.toString())
                month.add(formatTimeUnit(startMonth))
                for (i in startDay..endDay) {
                    day.add(formatTimeUnit(i))
                }

            }
        }
        loadComponent()
    }

    /**
     * 将“0-9”转换为“00-09”
     */
    private fun formatTimeUnit(unit: Int): String {
        return if (unit < 10) "0" + unit.toString() else unit.toString()
    }

    private fun initArrayList() {
        year.clear()
        month.clear()
        day.clear()
    }

    private fun loadComponent() {
        pvYear?.setData(year)
        pvMonth?.setData(month)
        pvDay?.setData(day)
        pvYear?.setSelected(0)
        pvMonth?.setSelected(0)
        pvDay?.setSelected(0)
        executeScroll()
    }

    private fun addListener() {
        pvYear?.onSelectListener = object : DatePickerView.OnSelectListener {
            override fun onSelect(text: String) {
                selectedCalender.set(Calendar.YEAR, Integer.parseInt(text))
                monthChange()
            }
        }

        pvMonth?.onSelectListener = object : DatePickerView.OnSelectListener {
            override fun onSelect(text: String) {
                selectedCalender.set(Calendar.DAY_OF_MONTH, 1)
                selectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1)
                dayChange()
            }
        }

        pvDay?.onSelectListener = object : DatePickerView.OnSelectListener {
            override fun onSelect(text: String) {
                selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text))
            }
        }
    }

    private fun monthChange() {
        month.clear()
        val selectedYear = selectedCalender.get(Calendar.YEAR)
        when (selectedYear) {
            startYear -> for (i in startMonth..MAX_MONTH) {
                month.add(formatTimeUnit(i))
            }
            endYear -> for (i in 1..endMonth) {
                month.add(formatTimeUnit(i))
            }
            else -> for (i in 1..MAX_MONTH) {
                month.add(formatTimeUnit(i))
            }
        }
        selectedCalender.set(Calendar.MONTH, Integer.parseInt(month[0]) - 1)
        pvMonth?.setData(month)
        pvMonth?.setSelected(0)
        executeAnimator(pvMonth)

        pvMonth?.postDelayed({ dayChange() }, 100)
    }

    private fun dayChange() {
        day.clear()
        val selectedYear = selectedCalender.get(Calendar.YEAR)
        val selectedMonth = selectedCalender.get(Calendar.MONTH) + 1
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (i in startDay..selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day.add(formatTimeUnit(i))
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (i in 1..endDay) {
                day.add(formatTimeUnit(i))
            }
        } else {
            for (i in 1..selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day.add(formatTimeUnit(i))
            }
        }
        selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day[0]))
        pvDay?.setData(day)
        pvDay?.setSelected(0)
        executeAnimator(pvDay)
    }

    private fun executeAnimator(view: View?) {
        val pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f)
        val pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f)
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start()
    }

    private fun executeScroll() {
        pvYear?.canScroll = year.size > 1
        pvMonth?.canScroll = month.size > 1
        pvDay?.canScroll = day.size > 1
    }

    fun show() {
        datePickerDialog?.show()
    }

    fun dismiss() {
        datePickerDialog.let {
            if (datePickerDialog != null && datePickerDialog!!.isShowing) {
                datePickerDialog?.dismiss()
            }
        }
    }

    /**
     * 设置日期控件是否可以循环滚动
     */
    fun setIsLoop(isLoop: Boolean) {
        pvYear?.isLoop = isLoop
        pvMonth?.isLoop = isLoop
        pvDay?.isLoop = isLoop
    }

    /**
     * 设置日期控件默认选中的时间，时间格式为yyyy-MM-dd
     */
    fun setSelectedTime(time: String) {
        val str = time.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val dateStr = str[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        pvYear?.setSelected(dateStr[0])
        selectedCalender.set(Calendar.YEAR, Integer.parseInt(dateStr[0]))

        month.clear()
        val selectedYear = selectedCalender.get(Calendar.YEAR)
        when (selectedYear) {
            startYear -> for (i in startMonth..MAX_MONTH) {
                month.add(formatTimeUnit(i))
            }
            endYear -> for (i in 1..endMonth) {
                month.add(formatTimeUnit(i))
            }
            else -> for (i in 1..MAX_MONTH) {
                month.add(formatTimeUnit(i))
            }
        }
        pvMonth?.setData(month)
        pvMonth?.setSelected(dateStr[1])
        selectedCalender.set(Calendar.MONTH, Integer.parseInt(dateStr[1]) - 1)
        executeAnimator(pvMonth)

        day.clear()
        val selectedMonth = selectedCalender.get(Calendar.MONTH) + 1
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (i in startDay..selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day.add(formatTimeUnit(i))
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (i in 1..endDay) {
                day.add(formatTimeUnit(i))
            }
        } else {
            for (i in 1..selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day.add(formatTimeUnit(i))
            }
        }
        pvDay?.setData(day)
        pvDay?.setSelected(dateStr[2])
        selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr[2]))
        executeAnimator(pvDay)
        executeScroll()
    }

    /**
     * 定义结果回调接口
     */
    interface OnDateSelectListener {
        fun onSelect(time: Calendar)
    }

    companion object {

        private val MAX_MINUTE = 59
        private val MAX_HOUR = 23
        private val MAX_MONTH = 12
    }

}
