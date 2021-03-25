package com.hjy.cloud.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间工具类
 */
public class DateUtil {

    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DATE_PATTERN = "yyyy-MM-dd";
    public static final String YEAR_MONTH_PATTERN = "yyyy-MM";
    //有效期
    public static final Integer EXPIRE_TIME = 1;

//    public static String formatFullTime(LocalDateTime localDateTime) {
//        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
//    }

//    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
//        return localDateTime.format(dateTimeFormatter);
//    }

//    public static String formatCSTTime(String date, String format) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//        Date d = sdf.parse(date);
//        return DateUtil.getDateFormat(d, format);
//    }
    /**
     * 获取某时刻的月份
     * @param date 时刻
     * @param dateFormatType 格式化的格式
     * @return String类型的月份
     */
    public static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simformat = new SimpleDateFormat(dateFormatType);
        return simformat.format(date);
    }

    /**
     * 将String类型的json时间转化为自定义格式（年月日）Date类型的时间
     * @param date String类型的json时间
     * @return （年月日）Date类型的时间
     * @throws ParseException
     */
    public static Date formatTime(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DATE_PATTERN);
        Date parse = sdf.parse(date);
        return parse;
    }
    /**
     * 将String类型的json时间转化为自定义格式（年月）Date类型的时间
     * @param date String类型的json时间
     * @return （年月）Date类型的时间
     * @throws ParseException
     */
    public static Date formatTime2(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_PATTERN);
        Date parse = sdf.parse(date);
        return parse;
    }
    /**
     * 时刻+时间段
     * @param date 时刻
     * @return 时刻
     * @throws ParseException
     */
    public static Date addTime(Date date,int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,num);
        date = calendar.getTime();
        return date;
    }
    /**
     * 时刻+时间段
     * @param date 时刻
     * @return 时刻
     * @throws ParseException
     */
    public static Date addSYQTime(Date date,String num){
        if(date == null){
            return null;
        }else {
            int sc = Integer.parseInt(num);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH,sc);
            date = calendar.getTime();
            return date;
        }
    }
    /**
     * 计算今日是周几
     */
    public static String todayIs(){
        //计算今日是周几
        Calendar calendar = Calendar.getInstance();
//        //今日为一周的第几天-2
//        int day = calendar.get(Calendar.DAY_OF_WEEK);
        //星期几-星期一
        String displayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        return displayName;
    }
    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.before(begin)) {
            return 1;
        } else if(date.after(begin) && date.before(end)) {
            return 2;
        } else{
            return 3;
        }
    }

    /**
     *
     * @param nowDate
     * @param timeSlot 时间段
//     * @param dkType
     * @return
     */
    public static int belongCalendar2(Date nowDate, String timeSlot) throws ParseException {
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        //09:00-17:30
        String[] timeSlots = timeSlot.split("-");
        Date nowTime = df.parse(df.format(nowDate));
        //时间段开始的时刻
        Date beginTime = df.parse(timeSlots[0]);
        //时间段结束的时刻
        Date endTime = df.parse(timeSlots[1]);
        int status = belongCalendar(nowTime, beginTime, endTime);
        System.err.println("status"+status);
        return status;
    }
    /**
     * 两时间点相差的分钟数
     * 09:00-17:30
     * @param beginTime
     * @param endTime
     * @return
     */
    public static long getMinute(Date beginTime, Date endTime) {
        long beginLong = beginTime.getTime();
        long endLong = endTime.getTime();
        long total = (endLong - beginLong) / (1000 * 60);
//        long hourNum = total / 60;
//        long minuteNum = total % 60;
//        if(hourNum == 0){
//            return minuteNum+"分钟";
//        }else {
//            return hourNum+"小时"+minuteNum+"分钟";
//        }
        return total;
    }
    /**
     * 两时间点是否为同一时间
     * @param firstTime
     * @param secondTime
     * @return
     */
    public static boolean isSameDay(Date firstTime, Date secondTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DATE_PATTERN);
        String firstTimeStr = sdf.format(firstTime);
        String secondTimeStr = sdf.format(secondTime);
        if(firstTimeStr.equals(secondTimeStr)){
            return true;
        }else {
            return false;
        }
    }

    public static String minuteDesc(Integer valueOf) {
        int hourNum = valueOf / 60;
        int minuteNum = valueOf % 60;
        if(hourNum == 0){
            return minuteNum+"分钟";
        }else {
            return hourNum+"小时"+minuteNum+"分钟";
        }
    }
    public static long getLongMinute(String param) {
        String[] strings = param.split("-");
        long total = Integer.valueOf(strings[0]) * 60;
        if(strings.length == 2){
            total += Integer.valueOf(strings[1]);
        }
        return total;
    }
}
