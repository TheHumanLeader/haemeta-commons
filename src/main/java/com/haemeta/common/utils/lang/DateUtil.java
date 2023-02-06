package com.haemeta.common.utils.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 2018年2月29日14:33:01 SJF
 * 我的 DateUtil 好多都能该呢，但是我懒得改
 */
public class DateUtil {

    private static final ZoneId defaultZoneId;
    private static final ZoneOffset defaultZoneOffset;

    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String HH_mm_ss = "HH:mm:ss";


    /**
     * 全局时区设置为中国上海
     */
    static {
//        System.out.println(toString("yyyy年MM月dd日 HH:mm:ss",new Date()));

        //"America/Chicago", "Asia/Shanghai"
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(timeZone);

        defaultZoneId = ZoneOffset.systemDefault();
        defaultZoneOffset = OffsetDateTime.now().getOffset();

//        TimeZone timeZone = TimeZone.getTimeZone("Pacific/Guadalcanal");
//        System.out.println(toString("yyyy年MM月dd日 HH:mm:ss",new Date()));


    }

    /**
     *  Date转String（顺势更改格式）;
     * @param yyyy_MM_dd_HH_mm_ss "yyyy-MM-dd HH:mm:ss"
     * @param date  时间
     * @return
     */
    public static String toString(String yyyy_MM_dd_HH_mm_ss, Date date){
        SimpleDateFormat format;
        try {
            yyyy_MM_dd_HH_mm_ss = null == yyyy_MM_dd_HH_mm_ss ?
                    DateUtil.yyyy_MM_dd_HH_mm_ss
                    :
                    yyyy_MM_dd_HH_mm_ss.equals("") ? DateUtil.yyyy_MM_dd_HH_mm_ss:yyyy_MM_dd_HH_mm_ss;

            format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
            return format.format(date);
        }catch (Exception ex){
            return new SimpleDateFormat(DateUtil.yyyy_MM_dd_HH_mm_ss).format(date);
        }finally {
            format = null;
        }
    }

    public static Calendar getCalendar(){
        return Calendar.getInstance();
    }

    public static Date toDate(String yyyy_MM_dd_HH_mm_ss, String dateStr){
        yyyy_MM_dd_HH_mm_ss = null == yyyy_MM_dd_HH_mm_ss ? "yyyy-MM-dd HH:mm:ss":yyyy_MM_dd_HH_mm_ss.equals("") ? "yyyy-MM-dd HH:mm:ss":yyyy_MM_dd_HH_mm_ss;
        SimpleDateFormat format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);

        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getCurrentDateStr(){
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date getDayStart(Date date){
        Calendar calendar = getCalendar();
        calendar.set(Calendar.MONTH,date.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH,date.getDate());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     *
     * @param date
     * @param offset 大于0 ，则想未来前进
     * @return
     */
    public static Date getDayStart(Date date,int offset){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,date.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH,date.getDate() + offset);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getDayEnd(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,date.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH,date.getDate());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getDayEnd(Date date,int offset){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,date.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH,date.getDate() + offset);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取本周第一天
     * @return
     */
    public static Date getWeekStart(){
        Date day = new Date();
        //一周开始和结束的差
        //当前是星期三
        //2021年12月15日 星期三
        //3
        //int dayWeek = day.getDay();
        // 7 - 3 = 4
        //int weekStartEndDiff = 7 - day.getDay() ;
        //当月的第几天
        //15
        //int dayMonth = day.getDate();
        //15 - 3 = 12 2021年12月12日是 星期七
        //15 -3 + 1 = 13 才是我们要的星期一
        //int firstDayWeek = dayMonth - dayWeek + 1;

        Calendar weekStart = Calendar.getInstance();
        weekStart.set(Calendar.DAY_OF_MONTH,day.getDate() - day.getDay() + 1);
        weekStart.set(Calendar.HOUR_OF_DAY, 00);
        weekStart.set(Calendar.MINUTE, 00);
        weekStart.set(Calendar.SECOND, 00);
        weekStart.set(Calendar.MILLISECOND, 000);
        return weekStart.getTime();
    }

    /**
     * 获取当前周  前几周  或 后几周
     * @param preOrLetter  前几周 就是 正数，后几周 负数
     * @return
     */
    public static Date getWeekStart(Integer preOrLetter){
        Date day = new Date();
        //一周开始和结束的差
        //当前是星期三
        //2021年12月15日 星期三
        //3
        //int dayWeek = day.getDay();
        //当月的第几天
        //15
        //int dayMonth = day.getDate();
        //15 - 3 = 12 2021年12月12日是 星期七
        //15 -3 + 1 = 13 才是我们要的星期一
        //int firstDayWeek = dayMonth - dayWeek + 1;

        //前一周 是 6号
        //13 - 7 = 6
        //所以 前几周就是
        //13 - 7 * 几

        Calendar weekStart = Calendar.getInstance();
        weekStart.set(Calendar.DAY_OF_MONTH,(day.getDate() - day.getDay() + 1) - 7 * preOrLetter);
        weekStart.set(Calendar.HOUR_OF_DAY, 00);
        weekStart.set(Calendar.MINUTE, 00);
        weekStart.set(Calendar.SECOND, 00);
        weekStart.set(Calendar.MILLISECOND, 000);
        return weekStart.getTime();
    }

    /**
     * 获取当前周  前几周  或 后几周
     * @param preOrLetter  前几周 就是 正数，后几周 负数
     * @return
     */
    public static Date getWeekEnd(Integer preOrLetter){
        Date day = new Date();
        //一周开始和结束的差
        //当前是星期三
        //2021年12月15日 星期三
        //3
        //int dayWeek = day.getDay();
        // 7 - 3 = 4
        //int weekStartEndDiff = 7 - day.getDay() ;
        //当月的第几天
        //15
        //int dayMonth = day.getDate();
        //15 + 4 = 19 2021年12月19日是 星期七
        //int lastDayWeek = dayMonth + weekStartEndDiff;

        Calendar weekStart = Calendar.getInstance();
        weekStart.set(Calendar.DAY_OF_MONTH,day.getDate() + (7 - day.getDay()) - 7 * preOrLetter);
        weekStart.set(Calendar.HOUR_OF_DAY, 23);
        weekStart.set(Calendar.MINUTE, 59);
        weekStart.set(Calendar.SECOND, 59);
        weekStart.set(Calendar.MILLISECOND, 999);
        return weekStart.getTime();
    }

    public static Date getWeekEnd(){
        Date day = new Date();
        //一周开始和结束的差
        //当前是星期三
        //2021年12月15日 星期三
        //3
        //int dayWeek = day.getDay();
        // 7 - 3 = 4
        //int weekStartEndDiff = 7 - day.getDay() ;
        //当月的第几天
        //15
        //int dayMonth = day.getDate();
        //15 + 4 = 19 2021年12月19日是 星期七
        //int lastDayWeek = dayMonth + weekStartEndDiff;

        Calendar weekStart = Calendar.getInstance();
        weekStart.set(Calendar.DAY_OF_MONTH,day.getDate() + (7 - day.getDay()));
        weekStart.set(Calendar.HOUR_OF_DAY, 23);
        weekStart.set(Calendar.MINUTE, 59);
        weekStart.set(Calendar.SECOND, 59);
        weekStart.set(Calendar.MILLISECOND, 999);
        return weekStart.getTime();
    }

    /**
     * 获取本月第一天
     * @return
     */
    public static Date getMonthStart(){
        Calendar monthStart = Calendar.getInstance();
        monthStart.set(Calendar.DAY_OF_MONTH,1);
        monthStart.set(Calendar.HOUR_OF_DAY, 00);
        monthStart.set(Calendar.MINUTE, 00);
        monthStart.set(Calendar.SECOND, 00);
        monthStart.set(Calendar.MILLISECOND, 000);
        return monthStart.getTime();
    }


    //获取指定月份的第一天
    public static Date getMonthStart(Integer month){
        Calendar monthStart = Calendar.getInstance();
        monthStart.set(Calendar.MONTH,month);
        monthStart.set(Calendar.DAY_OF_MONTH,1);
        monthStart.set(Calendar.HOUR_OF_DAY, 00);
        monthStart.set(Calendar.MINUTE, 00);
        monthStart.set(Calendar.SECOND, 00);
        monthStart.set(Calendar.MILLISECOND, 000);
        return monthStart.getTime();
    }

    /**
     * 获取本月最后一天
     * @return
     */
    public static Date getMonthEnd(){
        Date day = new Date();
        Calendar weekStart = Calendar.getInstance();
        weekStart.set(Calendar.MONTH,day.getMonth() + 1);
        weekStart.set(Calendar.DAY_OF_MONTH,1);
        weekStart.set(Calendar.HOUR_OF_DAY, 00);
        weekStart.set(Calendar.MINUTE, 00);
        weekStart.set(Calendar.SECOND, 00);
        weekStart.set(Calendar.MILLISECOND, -1);
        return weekStart.getTime();
    }

    public static Date getMonthEnd(Integer month){
        Calendar weekStart = Calendar.getInstance();
        weekStart.set(Calendar.MONTH,month + 1);
        weekStart.set(Calendar.DAY_OF_MONTH,1);
        weekStart.set(Calendar.HOUR_OF_DAY, 00);
        weekStart.set(Calendar.MINUTE, 00);
        weekStart.set(Calendar.SECOND, 00);
        weekStart.set(Calendar.MILLISECOND, -1);
        return weekStart.getTime();
    }

    public static Calendar getCalendar(Integer year,Integer month,Integer day,Integer hour,Integer second,Integer mills){
        Calendar target = Calendar.getInstance();
        target.set(Calendar.MONTH,month + 1);
        target.set(Calendar.DAY_OF_MONTH,1);
        target.set(Calendar.HOUR_OF_DAY, 00);
        target.set(Calendar.MINUTE, 00);
        target.set(Calendar.SECOND, 00);
        target.set(Calendar.MILLISECOND, -1);
        return target;
    }

    public static Long getTime(LocalDateTime time){
        return time.toEpochSecond(defaultZoneOffset);
    }
    public static Long getTimes(LocalDateTime time){
        return time.toInstant(defaultZoneOffset).toEpochMilli();
    }

    public static ZoneId getDefaultZoneId(){
        return defaultZoneId;
    }


}
