package com.chunhe.custom.framework.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
    /**
     * 1秒钟的毫秒数
     */
    public static final long MILLIS_PER_SECOND = 1000;
    /**
     * 1分钟的毫秒数
     */
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    /**
     * 1小时的毫秒数
     */
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    /**
     * 1天的毫秒数
     */
    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

    public static final String FORMAT_DATE = "yyyy-MM-dd";

    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss SSS",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss SSS"};


    /**
     * 获取与当前时间偏差一定时间段的时间
     *
     * @param offsetMilliseconds 偏差的毫秒
     */
    public static Date getDate(long offsetMilliseconds) {
        return getDate(new Date(), offsetMilliseconds);
    }

    /**
     * 获取和当传入时间偏差一定时间段的时间
     *
     * @param date               基础时间
     * @param offsetMilliseconds 偏差的毫秒
     */
    public static Date getDate(Date date, long offsetMilliseconds) {
        return new Date(date.getTime() + offsetMilliseconds);
    }


    /**
     * 格式化日期为字符串
     *
     * @param date    目标时间
     * @param pattern 格式字符数组，只用第一个，空数组默认格式为"yyyy-MM-dd HH:mm:ss"
     */
    public static String formatDate(Date date, String... pattern) {
        return formatDate(date, 0, pattern);
    }

    /**
     * 得到日期字符串
     *
     * @param date               目标时间
     * @param offsetMilliseconds 偏差的毫秒
     */
    public static String formatDate(Date date, long offsetMilliseconds, String... pattern) {
        if (pattern == null || pattern.length == 0)
            pattern = new String[]{"yyyy-MM-dd HH:mm:ss"};
        return format(new Date(date.getTime() + offsetMilliseconds), pattern[0]);
    }

    /**
     * 格式化时间为字符串
     *
     * @param date    时间
     * @param pattern 格式
     */
    private static String format(Date date, String pattern) {
        return FormatCache.getInstance(pattern).format(date);
    }

    /**
     * 得到日期字符串
     */
    public static String getDate(Date... date) {
        return formatDate(getCalendarByDate(date).getTime(), "yyyy-MM-dd");
    }

    /**
     * 得到时间字符串
     */
    public static String getTime(Date... date) {
        return formatDate(getCalendarByDate(date).getTime(), "HH:mm:ss");
    }

    /**
     * 得到日期和时间字符串
     */
    public static String getDateTime(Date... date) {
        return formatDate(getCalendarByDate(date).getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 返回日期的星期字符串
     */
    public static String getWeek(Date... date) {
        return formatDate(getCalendarByDate(date).getTime(), "E");
    }

    /**
     * 返回日期的天
     */
    public static int getDay(Date... date) {
        return getCalendarByDate(date).get(Calendar.DATE);
    }

    /**
     * 返回日期的月份，1-12
     */
    public static int getMonth(Date... date) {
        return getCalendarByDate(date).get(Calendar.MONTH) + 1;
    }

    /**
     * 返回日期的年
     */
    public static int getYear(Date... date) {
        return getCalendarByDate(date).get(Calendar.YEAR);
    }

    /**
     * 日期型字符串转化为日期
     */
    public static Date parseDate(String dateStr, String... patterns) {
        if (patterns == null || patterns.length == 0)
            patterns = parsePatterns;
        for (String pattern : patterns) {
            ParsePosition pos = new ParsePosition(0);
            Date date = FormatCache.getInstance(pattern).parse(dateStr, pos);
            if (pos.getIndex() == dateStr.length())
                return date;
        }
        return null;
    }

    /**
     * 获取java.sql.Date
     */
    public static java.sql.Date getSqlDate(Date... date) {
        if (date == null || date.length == 0)
            date = new Date[]{new Date()};
        return new java.sql.Date(date[0].getTime());
    }

    /**
     * 比较两个时间是否是同一天
     */
    public static boolean sameSqlDate(Date t1, Date t2) {
        return format(t1, "yyyy-MM-dd").equals(format(t2, "yyyy-MM-dd"));
    }

    /**
     * 得到目标时间当天最开始时刻
     */
    public static Date getDateStart(Date... date) {
        Calendar calendar = getCalendarByDate(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到目标时间当天最后时刻
     */
    public static Date getDateEnd(Date... date) {
        Calendar calendar = getCalendarByDate(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
//        return getDate(getDateStart(getDate(getCalendarByDate(date).getTime(), MILLIS_PER_DAY)), -1);
    }

    /**
     * 生成一个Calendar
     *
     * @param date 要设置的时间，为空则设置当前时间
     */
    private static Calendar getCalendarByDate(Date... date) {
        if (date == null || date.length == 0)
            date = new Date[]{new Date()};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date[0]);
        return calendar;
    }

    /**
     * 得到目标时间当周的第一天，周日
     */
    public static Date getWeakStart(Date... date) {
        Calendar calendar = getCalendarByDate(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return calendar.getTime();
    }

    /**
     * 得到目标时间当周的最后一天，周六
     */
    public static Date getWeakEnd(Date... date) {
        Calendar calendar = getCalendarByDate(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return calendar.getTime();
    }

    /**
     * 得到目标时间当月的第一天
     */
    public static Date getMonthStart(Date... date) {
        Calendar calendar = getCalendarByDate(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 得到目标时间当月的最后一天
     */
    public static Date getMonthEnd(Date... date) {
        Calendar calendar = getCalendarByDate(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.roll(Calendar.DAY_OF_MONTH, false);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     */
    public static long diffDay(Date date, Date anotherDate) {
        return Math.abs((getDateStart(date).getTime() - getDateStart(anotherDate).getTime())) / MILLIS_PER_DAY;
    }

    /**
     * 计算两个日期之间相差的年数
     */
    public static int diffYear(Date date, Date anotherDate) {
        return Math.abs(getYear(date) - getYear(anotherDate));
    }

    /**
     * 前者等于后者
     */
    public static boolean compareEqual(Date t1, Date t2) {
        return t1.getTime() == t2.getTime();
    }

    /**
     * 前者大于后者
     */
    public static boolean compareGreater(Date t1, Date t2) {
        return t1.getTime() > t2.getTime();
    }

    /**
     * 前者大于或等于后者
     */
    public static boolean compareGreaterOrEqual(Date t1, Date t2) {
        return t1.getTime() >= t2.getTime();
    }

    /**
     * 前者小于后者
     */
    public static boolean compareLess(Date t1, Date t2) {
        return t1.getTime() < t2.getTime();
    }

    /**
     * 前者小于或等于后者
     */
    public static boolean compareLessOrEqual(Date t1, Date t2) {
        return t1.getTime() <= t2.getTime();
    }

    /**
     * 从sqlDate中取得某天对应的时间
     */
    public static Date getDateFromSqlTime(Date date, java.sql.Time sqlTime) {
        return parseDate(formatDate(date, "yyyy-MM-dd") + " " + formatDate(sqlTime, "HH:mm:ss"));
    }

    /**
     * 根据生日获取星座
     */
    public static String getConstellation(Date date) {
        int month = getMonth(date);
        int day = getDay(date);
        String s = "魔羯水瓶双鱼白羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
        int[] dayArr = {20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
        int start = month * 2 + (day >= dayArr[month - 1] ? 0 : -2);
        return s.substring(start, start + 2) + "座";
    }

    /**
     * 格式工厂缓存
     */
    static class FormatCache {
        private static Map<String, SimpleDateFormat> formatCache = new HashMap<>();

        public static SimpleDateFormat getInstance(String pattern) {
            if (!formatCache.containsKey(pattern))
                formatCache.put(pattern, new SimpleDateFormat(pattern));
            return formatCache.get(pattern);
        }
    }


    /**
     * @author:fengzhiyuan
     * @date:2020年10月30日13:26:19
     * @description: 以传入时间的日期为主，推算X月（如往前3个月，-3；往后3个月，3）
     * @param: date
     * @param: month
     */
    public static Date getLastMonth(Date date, Integer month) {
        Calendar calendar = getCalendarByDate(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
        return calendar.getTime();
    }

    /**
     * @author:fengzhiyuan
     * @date:2020年7月20日15:59:38
     * @description: 以传入时间的日期为主，推算X天（如往前3天，-3；往后3天，3）
     * @param: date
     * @param: day
     */
    public static Date getLastDay(Date date, Integer day) {
        Calendar calendar = getCalendarByDate(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
        return calendar.getTime();
    }

    /**
     * @author:fengzhiyuan
     * @date:2020年8月27日11:27:18
     * @description: 以传入时间的日期为主，推算X小时（如往前3小时，-3；往后3小时，3）
     * @param: date
     * @param: hour
     */
    public static Date getLastHour(Date date, Integer hour) {
        Calendar calendar = getCalendarByDate(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        return calendar.getTime();
    }


}