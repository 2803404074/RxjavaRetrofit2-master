package com.dabangvr.live.utils;

import com.dbvr.baselibrary.model.WeekDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataUtil {
    public static String getDateForNow(String mType){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mType);// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取一周的时间
     * @return
     */
    public static List<WeekDay> getWeekDay() {
        Calendar calendar = Calendar.getInstance();
        // 获取本周的第一天
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        List<WeekDay> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek + i);
            WeekDay weekDay = new WeekDay();
            // 获取星期的显示名称，例如：周一、星期一、Monday等等
            weekDay.week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.CHINA);
            weekDay.day = new SimpleDateFormat("dd").format(calendar.getTime());
            if (weekDay.day.equals(getDateForNow("dd"))){
                weekDay.day = "今";
                weekDay.setShow(true);
            }
            list.add(weekDay);
        }
        return list;
    }
}
