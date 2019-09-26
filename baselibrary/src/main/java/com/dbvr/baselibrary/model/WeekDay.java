package com.dbvr.baselibrary.model;

public class WeekDay {
    /** 星期的显示名称*/
    public String week;
    /** 对应的日期*/
    public String day;

    private boolean isShow;

    public String getWeek() {
        return week;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "WeekDay{" +
                "week='" + week + '\'' +
                ", day='" + day + '\'' +
                '}';
    }
}
