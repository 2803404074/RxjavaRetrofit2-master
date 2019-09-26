package com.dbvr.baselibrary.utils;

public class DateUtils {
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
