package org.nefu.softlab.weiboAPI.common.util;

import org.junit.jupiter.api.Test;

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

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
     * 用于爬虫中对课程的周次统一格式化
     * 需要处理的格式极限情况是：1,2,9-11 ->1,2,9,10,11
     */
    public static String formatWeeks(String oriString) {
        if (oriString.contains("-")) {
            StringBuilder builder = new StringBuilder("");
            if (oriString.contains(",")) {  // 极限情况
                String [] datas = oriString.trim().split(",");
                System.out.println(datas.length);
                Arrays.stream(datas).forEach(n -> {
                    if (n.contains("-")) {
                        String [] tempdatas = n.trim().split("-");
                        int from = Integer.parseInt(datas[0]);
                        int to = Integer.parseInt(datas[1]);
                        for (int i = from; i <= to; i++) {
                            builder.append(i);
                            builder.append(",");
                        }
                    } else {
                        builder.append(n);
                        builder.append(",");
                    }
                });
            } else {
                String [] datas = oriString.trim().split("-");
                int from = Integer.parseInt(datas[0]);
                int to = Integer.parseInt(datas[1]);
                for (int i = from; i <= to; i++) {
                    builder.append(i);
                    builder.append(",");
                }
            }
            String returnStr = builder.toString();
            return returnStr.substring(0, returnStr.length() - 1);
        }
        else
            return oriString;
    }

    @Test
    public void test() {
        System.out.println(getCurDayInWeek());
    }
}
