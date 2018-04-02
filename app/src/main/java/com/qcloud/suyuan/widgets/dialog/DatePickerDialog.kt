package com.qcloud.suyuan.widgets.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_date_picker.*


/**
 * Description: 日期选择
 * Author: iceberg
 * 2018/3/23 下午3:41.
 */
class DatePickerDialog constructor(context: Context) : BaseDialog(context), View.OnClickListener, CalendarView.OnYearChangeListener, CalendarView.OnDateSelectedListener, CalendarView.OnMonthChangeListener, CalendarView.OnDateLongClickListener {

    var onDateSelectListener: OnDateSelectedListener? = null
    private var calendar: Calendar? = null
    var mYear: Int = 0
    private var lp: ViewGroup.LayoutParams? = null

    override val viewId: Int
        get() = R.layout.dialog_date_picker

    init {
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        tv_month_day.setOnClickListener({ _ ->
            calendar_view.showYearSelectLayout(mYear)
            tv_lunar.visibility = View.GONE
            tv_year.visibility = View.GONE
            btn_ok.visibility = View.GONE

            lp = calendar_view.layoutParams
            lp?.width = ViewGroup.LayoutParams.WRAP_CONTENT
            lp?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            calendar_view.layoutParams = lp
            tv_month_day.text = mYear.toString()
        })

        fl_current.setOnClickListener{ calendar_view.scrollToCurrent() }
        calendar_view.setOnYearChangeListener(this)
        calendar_view.setOnDateSelectedListener(this)
        calendar_view.setOnMonthChangeListener(this)
        calendar_view.setOnDateLongClickListener(this)

        tv_year.text = calendar_view.curYear.toString()
        mYear = calendar_view.curYear
        tv_month_day.text = "${calendar_view.curMonth}月${calendar_view.curDay}日"
        tv_lunar.text = "今日"
        tv_current_day.text = calendar_view.curDay.toString()
        btn_ok.setOnClickListener{ onConfirmClick() }
        btn_cancel.setOnClickListener { dismiss() }
    }

    /**
     * 点击确认选择
     * */
    private fun onConfirmClick() {
        if (calendar == null) {
            return
        }
        onDateSelectListener?.dateSelected(calendar)
        dismiss()
    }

    override fun onClick(v: View) {
        calendar_view.scrollToCurrent()
    }

    override fun onYearChange(year: Int) {
        tv_month_day.text = "$year"
    }

    override fun onDateLongClick(calendar: Calendar?) {

    }

    override fun onMonthChange(year: Int, month: Int) {

    }

    override fun onDateSelected(calendar: Calendar?, isClick: Boolean) {
        tv_lunar.visibility = View.VISIBLE
        tv_year.visibility = View.VISIBLE
        btn_ok.visibility = View.VISIBLE

        lp = calendar_view.layoutParams
        lp?.width = ViewGroup.LayoutParams.WRAP_CONTENT
        lp?.height = mContext.resources.getDimensionPixelSize(R.dimen.date_picker_height)
        calendar_view.layoutParams = lp

        if (calendar != null) {
            tv_year.text = calendar.year.toString()
            tv_lunar.text = calendar.lunar
            mYear = calendar.year
            tv_month_day.text = "${calendar.month}月${calendar.day}日"
        }
        this.calendar = calendar
    }

    private fun getCalendarText(calendar: Calendar): String {
        return String.format("新历%s \n 农历%s \n 公历节日：%s \n 农历节日：%s \n 节气：%s \n 是否闰月：%s",
                calendar.month.toString() + "月" + calendar.day + "日",
                calendar.lunarCakendar.month.toString() + "月" + calendar.lunarCakendar.day + "日",
                if (TextUtils.isEmpty(calendar.gregorianFestival)) "无" else calendar.gregorianFestival,
                if (TextUtils.isEmpty(calendar.traditionFestival)) "无" else calendar.traditionFestival,
                if (TextUtils.isEmpty(calendar.solarTerm)) "无" else calendar.solarTerm,
                if (calendar.leapMonth == 0) "否" else String.format("闰%s月", calendar.leapMonth))
    }

    /**
     * 日期选择
     * */
    interface OnDateSelectedListener {
        fun dateSelected(calendar: Calendar?)
    }
}