package org.nefu.softlab.weiboAPI.common.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * Util of Date
 */
public class DateUtil {

    //  formatter
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    // logger
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 获取当前时间的时间戳
     */
    public static String getCurTimeStamp() {
        return formatter.format(new Date());
    }

    /**
     * 获取当前星期数
     * 1 ~ 7
     */
    public static int getCurDayInWeek() {
        return (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 7 - 1) % 7;
    }

    /**
     * 将制定格式的时间戳解析成Date对象
     * @param timestamp
     * @return
     */
    public static Date parseTimestamp(String timestamp) {
        try {
            return formatter.parse(timestamp);
        } catch (ParseException e) {
            logger.error("Parse timestamp string : " + timestamp + " failed .");
            return null;
        }
    }




    /**
     * 获取两个时间戳之间的时间差，单位是毫秒
     * @param oldtimestamp
     * @param newtimestamp
     * @return
     */
    public static long getTimeInterval(String oldtimestamp, String newtimestamp) {
        Date oldtime = parseTimestamp(oldtimestamp);
        Date newtime = parseTimestamp(newtimestamp);
        return newtime.getTime() - oldtime.getTime();
    }

    /**
     * 获取只包含当前日期的时间戳
     * @return
     */
    public static String getCurDate() {
        return dateFormatter.format(new Date());
    }

    /**
     * 将毫秒数转换为时间戳
     * @param mills
     * @return
     */
    public static String transferMillsToTimestamp(Long mills) {
        return formatter.format(new Date(mills));
    }


    @Test
    public void test() {
        long time = 1304438400000L;
        System.out.println(formatter.format(new Date(time)));
    }
}
