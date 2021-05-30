package com.chunhe.custom.pc.util;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zcw
 * @Title: 获取时间
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2019/7/2311:06
 */
public class DateUtil {


    private static final  Calendar calendar = Calendar.getInstance();

    private static final String format = "yyyy-MM-dd HH:mm:ss";

    public static final String dateFormat = "yyyy-MM-dd";

    /**
     * 日期转字符类型 yyyy-MM-dd HH:mm:ss 格式
     * @param date 日期
     * @return
     */
    public static String dateToStr(Date date){
        return dateToStrCustom(date, format);
    }

    /**
     * 字符转日期类型
     * str 格式:yyyy-MM-dd HH:mm:ss
     * @param str 字符日期
     * @return
     */
    public static Date strToDate(String str){
        return strToDateCustom(str,format);
    }

    /**
     * 自定义字符转日期
     * @param str 字符日期
     * @param format 转换的格式
     * @return
     */
    public static Date strToDateCustom(String str, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 自定义日期转字符
     * @param date 日期
     * @param format 转换类型
     * @return
     */
    public static String dateToStrCustom(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 转换时间类型
     * 如：2017-20-04 ，2017-20-04 10
     * @param date 转换的时间
     * @param format 转换的格式
     * @return
     */
    public static Date dateToFormat(Date date,String format){
        String str = dateToStrCustom(date,format);
        return strToDateCustom(str,format);
    }

    /**
     * 获取当前时间,format 格式
     * @param format 转换的格式
     * @return
     */
    public static Date dateToFormat(String format){
        return dateToFormat(new Date(),format);
    }

    /**
     *  获取当前时间后 minute 的时间
     * @param minute 分钟
     * @return
     */
    public static Date getAfterMinuteTime(int minute){
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, +minute);
       return calendar.getTime();
    }

    /**
     *  获取当前时间后 minute 的时间
     * @param minute 分钟
     * @return
     */
    public static Date getAfterMinuteTime(String minute){
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, +Integer.valueOf(minute));
        return calendar.getTime();
    }

    /**
     * 获取当前时间前 minute 的时间
     * @param minute 分钟
     * @return
     */
    public static Date getFrontMinuteTime(int minute){
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -minute);
        return calendar.getTime();
    }

    /**
     * 获取当前时间前 minute 的时间
     * @param minute 分钟
     * @return
     */
    public static Date getFrontMinuteTime(String minute){
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -Integer.valueOf(minute));
        return calendar.getTime();
    }



    /**
     *  获取今日的 指定小时
     *  2099-10-10 hour:00:00
     * @param hour 小时
     * @return
     */
    public static Date hourAppoint(Integer hour){
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     *  获取今日的 指定小时
     *  2099-10-10 hour:00:00
     * @param hour 小时
     * @return
     */
    public static Date  hourAppoint(String hour){
        return hourAppoint(Integer.valueOf(hour));
    }



    /**
     *  根据当前日期获想要的日期
     *  day 为正数者增加相应的天数
     *  day 为负数则减去相应的天数
     * @param day 天数
     * @return
     */
    public static Date dayAppoint(Integer day){
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     *  根据当前日期获想要的日期
     *  day 为正数者增加相应的天数
     *  day 为负数则减去相应的天数
     * @param day 天数
     * @return
     */
    public static Date dayAppoint(String day) {
        return dayAppoint(Integer.valueOf(day));
    }

    /**
     *  当前时间根据day获取日期，根据format返回格式
     * @param day 天数
     * @param format 格式
     * @return
     */
    public static Date dayAppointFormat(String day,String format){
        return dateToFormat(dayAppoint(day),format);
    }

    /**
     *  当前时间根据day获取日期，根据format返回格式
     * @param day 天数
     * @param format 格式
     * @return
     */
    public static Date dayAppointFormat(Integer day,String format){
        return dateToFormat(dayAppoint(day),format);
    }

    /**
     * 获取下一个周一
     * @param day 所要减去的天数
     * @return
     */
    public static Date getNextMonday(String day) {
        return getNextMonday(Integer.valueOf(day));
    }

    /**
     * 获取下一个周一
     * @param day 所要减去的天数
     * @return
     */
    public static Date getNextMonday(Integer day){
        calendar.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - currentDay);
        calendar.add(Calendar.DATE, 7);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }


    /**
     *  判断该日期是不是有效日期
     *  给当前日期添加day去比较是否大于 date 大于则true
     * @param date 被判断的日期
     * @param day 指定当前日期锁添加的时间
     * @return
     */
    public static boolean isEffectiveDate(Date date,Integer day){
        String str = dateToStrCustom(date,"yyyy-MM-dd");
        date = strToDateCustom(str,"yyyy-MM-dd");
        Date date1 = dayAppoint(day);
        return date.getTime() <= date1.getTime();
    }


    /**
     *  判断该日期是不是有效日期
     *  给当前日期添加day去比较是否大于 date 大于则true
     * @param date 被判断的日期
     * @param day 指定当前日期锁添加的时间
     * @return
     */
    public static boolean isEffectiveDate(Date date,String day){
        return isEffectiveDate(date,Integer.valueOf(day));
    }



    @Test
    public void test(){

    }





}
