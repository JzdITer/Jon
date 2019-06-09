package com.jzd.android.jon.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// TODO 优化
public class JTime
{
    /**
     * 默认的日期格式 "yyyy-MM-dd HH:mm:ss"
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * HH:mm:ss
     */
    public static final String TIME_PATTERN = "HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String ALARM_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 得到简单的24小时制的时间字符串 2016-3-3 17:56:00
     *
     * @return
     */
    public static String get24Time()
    {
        return format();
    }

    /**
     * 是否是同一天
     */
    public static boolean isSameDay(String time1, String pattern1, String time2, String pattern2)
    {
        Date date1 = parseDate(time1, pattern1);
        Date date2 = parseDate(time2, pattern2);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
        {
            return c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
        }
        return false;
    }

    /**
     * 判断是否是同一周 未完成!!!
     */
    public static boolean isSameWeek(String time1, String pattern1, String time2, String pattern2)
    {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(parseDate(time1, pattern1));
        c1.setFirstDayOfWeek(Calendar.MONDAY);// 设每周第一天为星期一
        Calendar c2 = Calendar.getInstance();
        c2.setTime(parseDate(time2, pattern2));
        c2.setFirstDayOfWeek(Calendar.MONDAY);

        if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
        {
            return c1.get(Calendar.WEEK_OF_YEAR) == c2.get(Calendar.WEEK_OF_YEAR);
        }
        return false;
    }

    /**
     * 是否是同一月
     */
    public static boolean isSameMonth(String time1, String pattern1, String time2, String pattern2)
    {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(parseDate(time1, pattern1));
        Calendar c2 = Calendar.getInstance();
        c2.setTime(parseDate(time2, pattern2));

        if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
        {
            return c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
        }
        return false;
    }

    /**
     * @param millis     long型时间
     * @param outPattern 输出时间格式
     * @return pattern 星期几
     */
    public static String formatWeekDate(long millis, String outPattern)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String descp = getWeekDescp(week);
        String date = "";
        date = format(new Date(millis), outPattern) + " " + descp;
        return date;
    }

    /**
     * 星期几
     *
     * @param millis
     * @return
     */
    public static String formatWeek(long millis)
    {
        String week = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        week = getWeekDescp(calendar.get(Calendar.DAY_OF_WEEK));
        return week;
    }

    /**
     * 星期几
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String formatWeek(String time, String pattern)
    {
        String week = "";
        Calendar calendar = Calendar.getInstance();
        Date date = parseDate(time, pattern);
        if(date == null)
        {
            return "";
        }
        calendar.setTime(date);
        week = getWeekDescp(calendar.get(Calendar.DAY_OF_WEEK));
        return week;
    }

    /**
     * @param time       时间
     * @param inPattern  输入的时间格式
     * @param outPattern 输出的时间格式
     * @return
     */
    public static String formatWeekDate(String time, String inPattern, String outPattern)
    {
        Calendar calendar = Calendar.getInstance();
        Date date = parseDate(time, inPattern);
        if(date == null)
        {
            return "";
        }
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String descp = getWeekDescp(week);
        String result = "";
        result = format(date, outPattern) + " " + descp;
        return result;
    }

    private static String getWeekDescp(int week)
    {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        if(week <= 0 || week >= 8)
        {
            return "";
        }
        return weeks[week - 1];
    }

    /**
     * 将字符串转化成date 格式:{@link #DEFAULT_PATTERN}
     *
     * @param time
     * @return may be null!!!
     * @throws ParseException
     */
    public static Date parseDate(String time)
    {
        return parseDate(time, DEFAULT_PATTERN);
    }

    /**
     * 将字符串转化为date
     *
     * @param time
     * @param pattern
     * @return may be null!!!
     * @throws ParseException
     */
    public static Date parseDate(String time, String pattern)
    {
        return parseDate(time, Locale.getDefault(), pattern);
    }

    /**
     * 将字符串转化为date ~ may be null!!!
     *
     * @param time
     * @param locale
     * @param pattern
     * @return may be null!!!
     * @throws Exception
     */
    public static Date parseDate(String time, Locale locale, String pattern)
    {
        try
        {
            return new SimpleDateFormat(pattern, locale).parse(time);
        } catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过日历得到Date
     *
     * @param year
     * @param month     base-0
     * @param day
     * @param hourOfDay
     * @param minute
     * @param second
     * @return
     */
    public static Date getDateFromCalendar(int year, int month, int day, int hourOfDay, int minute, int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hourOfDay, minute, second);
        return calendar.getTime();
    }

    public static Date getDateFromCalendar(long millis)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 讲当前时间转化为默认的时间格式
     *
     * @return
     */
    public static String format()
    {
        return format(new Date(System.currentTimeMillis()));
    }

    public static String format(String pattern)
    {
        return format(new Date(System.currentTimeMillis()), pattern);
    }

    /**
     * 将Date转化为String
     *
     * @param date
     * @return
     */
    public static String format(Date date)
    {
        return format(date, DEFAULT_PATTERN);
    }

    /**
     * 将Date转化为String
     *
     * @param date
     * @return
     */
    public static String format(Date date, String pattern)
    {
        return format(date, pattern, Locale.getDefault());
    }

    /**
     * 将beforeTime转化成afterPattern格式的时间串
     *
     * @param beforeTime    转化之前的时间
     * @param beforePattern 转化之前的格式
     * @param afterPattern  转化之后的格式
     * @return
     */
    public static String format(String beforeTime, String beforePattern, String afterPattern)
    {
        String result = "";
        Date beforeDate = parseDate(beforeTime, beforePattern);
        if(beforeDate != null)
        {
            result = format(beforeDate, afterPattern);
        }
        return result;
    }

    /**
     * 将Date转化为String
     *
     * @param date
     * @return
     */
    public static String format(Date date, String pattern, Locale locale)
    {
        return date == null ? "" : new SimpleDateFormat(pattern, locale).format(date);
    }

    public static String format(Calendar calendar)
    {
        return format(calendar.getTime(), DEFAULT_PATTERN);
    }

    public static String format(Calendar calendar, String pattern)
    {
        return format(calendar.getTime(), pattern, Locale.getDefault());
    }

    public static String format(Calendar calendar, String pattern, Locale locale)
    {
        return format(calendar.getTime(), pattern, locale);
    }

    /**
     * 增加对应时间
     *
     * @param year
     * @param month  base 0
     * @param day
     * @param hour
     * @param minute
     * @return
     */
    public static String addTime(int year, int month, int day, int hour, int minute)
    {
        return addTime(DEFAULT_PATTERN, year, month, day, hour, minute);
    }

    public static String addTime(String pattern, int year, int month, int day, int hour, int minute)
    {
        return addTime(format(pattern), pattern, year, month, day, hour, minute);
    }

    /**
     * 增加对应的时间
     *
     * @param time
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @return
     */
    public static String addTime(String time, String pattern, int year, int month, int day, int hour, int minute)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(time, pattern));

        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        return format(calendar.getTime(), pattern);
    }

    public static String addTime(String time, String beforePattern, String afterPattern, int year, int month, int day, int hour, int minute,
                                 int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(time, beforePattern));

        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
        return format(calendar.getTime(), afterPattern);
    }

    /**
     * 比较一个日期是否在一个日期之后
     *
     * @param startTime
     * @param startTimePattern
     * @param endTime
     * @param endTimePatter
     * @return
     */
    public static boolean after(String startTime, String startTimePattern, String endTime, String endTimePatter)
    {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(parseDate(startTime, startTimePattern));
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(parseDate(endTime, endTimePatter));

        return startCalendar.after(endCalendar);
    }

    /**
     * 比较一个日期是否在一个日期之前
     *
     * @param startTime
     * @param startTimePattern
     * @param endTime
     * @param endTimePatter
     * @return
     */
    public static boolean before(String startTime, String startTimePattern, String endTime, String endTimePatter)
    {
        return after(endTime, endTimePatter, startTime, startTimePattern);
    }

    /**
     * 设置Calendar
     */
    public static Calendar initCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar;
    }

    /**
     * 设置Calendar
     */
    public static Calendar initCalendar(String time,String patter)
    {
        Date date = parseDate(time,patter);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
