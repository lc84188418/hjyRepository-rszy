package com.hjy.cloud.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static String formatFullTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
    }

    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    private static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simformat = new SimpleDateFormat(dateFormatType);
        return simformat.format(date);
    }

    public static String formatCSTTime(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date d = sdf.parse(date);
        return DateUtil.getDateFormat(d, format);
    }
    public static Date formatTime(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DATE_PATTERN);
        Date parse = sdf.parse(date);
        return parse;
    }
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
        int sc = Integer.parseInt(num);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,sc);
        date = calendar.getTime();
        return date;
    }
}
