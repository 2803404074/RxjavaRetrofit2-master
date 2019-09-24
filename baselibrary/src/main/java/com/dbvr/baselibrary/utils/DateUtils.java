package com.dbvr.baselibrary.utils;

import com.dbvr.baselibrary.model.DayBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateUtils {
    public static List<DayBean> getWeekData() {
        List<DayBean> data = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd");
        Calendar c = Calendar.getInstance();
        //获取当天星期几
        int mWay = c.get(Calendar.DAY_OF_WEEK);
        //将时间退到周日
        for (int i = 0; i < mWay; i++) {
            c.add(Calendar.DATE, -1);
        }
        //得到本周时间
        for (int i = 0; i < 7; i++) {
            c.add(Calendar.DATE, 1);
            data.add(new DayBean(format.format(c.getTime()), getWeekDay(c.get(Calendar.DAY_OF_WEEK), mWay),
                    c.get(Calendar.DAY_OF_WEEK) == mWay ? true : false));
        }
        return data;
    }

    private static String getWeekDay(int day, int today) {
        String s = null;switch (day) {
            case 1:
                s = "日";
                break;
            case 2:
                s = "一";
                break;
            case 3:
                s = "二";
                break;
            case 4:
                s = "三";
                break;
            case 5:
                s = "四";
                break;
            case 6:
                s = "五";
                break;
            case 7:
                s = "六";
                break;
        }
        if (day == today)
            s = "今";
        return s;
    }
}
