package org.nefu.softlab.weiboAPI.biz.scheduler.job;

import org.nefu.softlab.weiboAPI.core.dao.mongo.StatisticsDao;
import org.nefu.softlab.weiboAPI.core.pojo.SpiderDataPojo;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Jiaxu_Zou on 2018-4-10
 *
 * 每秒钟都会去计算爬虫速率的定时器
 *
 * 并且在启动后
 *      未满五分钟，会计算已过时间内平均速率
 *      五分钟后会计算五分钟内的平均速率
 */
@Service
@Transactional
public class NoteRecordCount implements Job, Serializable {

    // dao
    private static StatisticsDao statisticsDao;

    // pojo
    private static SpiderDataPojo spiderDataPojo;

    // logger
    private static final Logger logger  = LoggerFactory.getLogger(NoteRecordCount.class);

    public NoteRecordCount() {
    }

    /**
     * 这里计算方法执行的时间
     * 是为了去除方法体执行时间所造成的准确率的影响
     * 一般方法执行时间会在几十毫秒到几毫秒之间
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long startmills = new Date().getTime();     // 获取方法执行的初试时间
        long lastcount = statisticsDao.getTotalRecordCount();   // 最新的数据
        long priviouscount = spiderDataPojo.getNewcount();  // 上一次的数据
//        System.out.println("lastcount : " + lastcount);
//        System.out.println("priviouscount : " + priviouscount);
        double speedinone = ((double)(lastcount - priviouscount)) / (double)(new Date().getTime() - startmills + 1000) * 1000D;   // 单位是条/秒
        // 更新保存数据的pojo中的数据
        spiderDataPojo.setOldcount_one(priviouscount);
        spiderDataPojo.setNewcount(lastcount);
        spiderDataPojo.setSpeedInOneSec(speedinone);
        // 保存到五分钟内的记录中，并进行测速
        List<Long> counts = spiderDataPojo.getCountInFiveMinutes();
        counts.add(lastcount);
        if (counts.size() > 300)    // 超过了五分钟
            counts.remove(0);
        long countBeforeFiveMin = counts.get(0);
        double speedinfive = ((double)(lastcount - countBeforeFiveMin)) / (counts.size() * 1.0);   // 单位是条/秒
        // 保存数据
        spiderDataPojo.setSpeedInFiveMin(speedinfive);
        logger.info("Refresh current spider speed : speed in 1s - " + speedinone + " , speed in 5m - " + speedinfive);
        // 测试
//        logger.info("Current speed In one sec is :" + spiderDataPojo.getSpeedInOneSec() + " 条/秒");
//        logger.info("Average speed in sive minutes is : " + spiderDataPojo.getSpeedInFiveMin());
//        logger.info("Storage list size is : " + spiderDataPojo.getCountInFiveMinutes().size());
    }

    // setter

    public static void setStatisticsDao(StatisticsDao statisticsDao) {
        NoteRecordCount.statisticsDao = statisticsDao;
    }

    public static void setSpiderDataPojo(SpiderDataPojo spiderDataPojo) {
        NoteRecordCount.spiderDataPojo = spiderDataPojo;
    }
}
