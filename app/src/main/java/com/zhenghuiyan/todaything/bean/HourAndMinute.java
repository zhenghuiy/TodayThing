package com.zhenghuiyan.todaything.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by zhenghuiyan on 2015/1/27.
 */
public class HourAndMinute implements Comparable<HourAndMinute>, Serializable{
    private int hour;

    private int minute;

    public HourAndMinute() {}

    public HourAndMinute(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }

        if (s.contains(":")) {
            String[] strs = s.split(":");
            setHour(Integer.parseInt(strs[0]));
            setMinute(Integer.parseInt(strs[1]));
        } else {
            String[] strs = {s.substring(0, 2), s.substring(2, 4)};
            setHour(Integer.parseInt(strs[0]));
            setMinute(Integer.parseInt(strs[1]));
        }
    }

    public String toString(boolean isClockMode) {
        StringBuilder builder = new StringBuilder();

        builder.append(format(hour));
        if (isClockMode) {
            builder.append(":");
        }
        builder.append(format(minute));

        return builder.toString();
    }

    private String format(int i) {
        return String.format("%02d", i);
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public int compareTo(HourAndMinute another) {
        if (hour != another.hour) {
            return hour - another.hour;
        }

        return minute - another.minute;
    }
}
