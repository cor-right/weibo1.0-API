package org.nefu.softlab.weiboAPI.core.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Jiaxu_Zou on 2018-4-11
 *
 * 这个pojo用于保存一些spider的统计方面的中间数据
 */
@Component
public class SpiderDataPojo {

    // record count
    private long oldcount_one = 0;   // 一秒后数据库的存储量

    private long newcount = 0;   // 最新的数据库存储量

    private List<Long> countInFiveMinutes = new ArrayList<>();      // 五分钟内的数据量记录，长度不超过300

    // speed
    private double speedInOneSec = 0.0D;    // 一秒钟内的爬虫速率
    private double speedInFiveMin = 0.0D;   // 五分钟内的爬虫速率



    // times
    //private int detectiveTimes = 0;   // 爬虫测速的次数

    // enable status
    //private boolean fiveAvgSpeed = false;       // 是否满足计算五分钟内平均速率的条件（也就是系统中测速的次数达到了500次则置为true）

    // getter and setter


    public List<Long> getCountInFiveMinutes() {
        return countInFiveMinutes;
    }

    public void setCountInFiveMinutes(List<Long> countInFiveMinutes) {
        this.countInFiveMinutes = countInFiveMinutes;
    }

    public long getOldcount_one() {
        return oldcount_one;
    }

    public void setOldcount_one(long oldcount_one) {
        this.oldcount_one = oldcount_one;
    }

    public double getSpeedInOneSec() {
        return speedInOneSec;
    }

    public void setSpeedInOneSec(double speedInOneSec) {
        this.speedInOneSec = speedInOneSec;
    }

    public double getSpeedInFiveMin() {
        return speedInFiveMin;
    }

    public void setSpeedInFiveMin(double speedInFiveMin) {
        this.speedInFiveMin = speedInFiveMin;
    }

    public long getNewcount() {
        return newcount;
    }

    public void setNewcount(long newcount) {
        this.newcount = newcount;
    }


}
