package com.xdsjs.save.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 关于时间操作的工具类
 * Created by xdsjs on 2015/10/14.
 */
public class TimeUtils {

    /**
     * 获取当天的开始时间
     */
    public static String getTimeOfDay() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return String.valueOf((Date) currentDate.getTime().clone());
    }

    /**
     * 获取当前周的第一天的开始时间
     */
    public static String getFirstDayTimeOfWeek() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return String.valueOf(((Date) currentDate.getTime()).getTime());
    }

    /**
     * 获取当前月的第一天的开始时间
     */
    public static String getFirstDayTimeOfMonth() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_MONTH, 0);
        return String.valueOf(((Date) currentDate.getTime()).getTime());
    }

    /**
     * 获取当前年的第一天的开始时间
     */
    public static String getFirstDayTimeOfYear() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_YEAR, 0);
        return String.valueOf(((Date) currentDate.getTime()).getTime());
    }

    public static String getPrettyTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        return sdf2.format(date);
    }
}
