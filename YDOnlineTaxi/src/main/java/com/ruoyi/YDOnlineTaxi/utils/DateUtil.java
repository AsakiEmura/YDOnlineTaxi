package com.ruoyi.YDOnlineTaxi.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {


    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static String getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date currYearFirst = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static String getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date currYearLast = calendar.getTime();

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    /**
     * 获取某月的第一天
     * @Title:getFirstMomentOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     * @throws
     */
    public static String getFirstMomentOfMonth(int year,int month)
    {
        Calendar calendar = Calendar.getInstance();
        //设置年份
        calendar.set(Calendar.YEAR,year);
        //设置月份
        calendar.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    /**
     * 获取某月的最后一天
     * @Title:getLastMomentOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     * @throws
     */
    public static String getLastMomentOfMonth(int year,int month)
    {
        Calendar calendar = Calendar.getInstance();
        //设置年份
        calendar.set(Calendar.YEAR,year);
        //设置月份
        calendar.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static String getStartOfDay(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static String getEndOfDay(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static String getStartOfDay(int year,int month,int day) {
        Calendar calendar = Calendar.getInstance();
        //设置年份
        calendar.set(Calendar.YEAR,year);
        //设置月份
        calendar.set(Calendar.MONTH, month-1);
        //设置第几天
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static String getEndOfDay(int year,int month,int day) {
        Calendar calendar = Calendar.getInstance();
        //设置年份
        calendar.set(Calendar.YEAR,year);
        //设置月份
        calendar.set(Calendar.MONTH, month - 1);
        //设置第几天
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    public static Map<String, String> getIntervalDate(Integer year, Integer month, Integer day) {
        String minTransportTime = null;
        String maxTransportTime = null;
        if (year != null && month == null && day == null) {
            minTransportTime = DateUtil.getYearFirst(year);
            maxTransportTime = DateUtil.getYearLast(year);
        } else if (year != null && month != null && day == null) {
            minTransportTime = DateUtil.getFirstMomentOfMonth(year, month);
            maxTransportTime = DateUtil.getLastMomentOfMonth(year, month);
        } else if (year != null && month != null && day != null) {
            minTransportTime = DateUtil.getStartOfDay(year, month, day);
            maxTransportTime = DateUtil.getEndOfDay(year, month, day);
        }
        Map<String, String> map = new HashMap<>();
        map.put("minTransportTime", minTransportTime);
        map.put("maxTransportTime", maxTransportTime);
        return map;
    }
}
