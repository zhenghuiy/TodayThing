package com.zhenghuiyan.todaything.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhenghuiyan on 2015/1/28.
 */
public class DateUtil {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private DateUtil() {}

    public static String dateToString(Date d) {
        return dateFormat.format(d);
    }

    public static Date parseDate(String s) {
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getDayOfWeek(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c.get(Calendar.DAY_OF_WEEK);
    }
}
