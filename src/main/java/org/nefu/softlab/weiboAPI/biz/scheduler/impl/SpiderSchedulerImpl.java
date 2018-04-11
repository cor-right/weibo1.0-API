package org.nefu.softlab.weiboAPI.biz.scheduler.impl;

import org.nefu.softlab.weiboAPI.biz.scheduler.SpiderScheduler;
import org.nefu.softlab.weiboAPI.biz.scheduler.job.NoteRecordCount;
import org.nefu.softlab.weiboAPI.core.dao.mongo.StatisticsDao;
import org.nefu.softlab.weiboAPI.core.pojo.SpiderDataPojo;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpiderSchedulerImpl implements SpiderScheduler {

    // scheduler
    private final Scheduler spiderScheduler = StdSchedulerFactory.getDefaultScheduler();

    // dao
    private StatisticsDao statisticsDao;

    // pojo
    private SpiderDataPojo spiderDataPojo;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(SpiderSchedulerImpl.class);


    @Autowired
    public SpiderSchedulerImpl(StatisticsDao statisticsDao, SpiderDataPojo spiderDataPojo) throws SchedulerException {
        this.statisticsDao = statisticsDao;
        this.spiderDataPojo = spiderDataPojo;
        this.setBeans(); // 设置依赖
        this.startSpiderSpeedCountTrigger();    // 设置爬虫速率的计算的定时器
        spiderScheduler.start();
    }


    @Override
    public void startSpiderSpeedCountTrigger() {
        // 设置一秒间隔的更新记录数的定时器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("speedRecord", "spider")     // 设置name和group
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)       // 设置时间间隔，这里是1秒
                        .repeatForever()        // 设置循环次数，这里是无限
                ).build();
        JobDetail job = JobBuilder.newJob(NoteRecordCount.class)      // 设置Job
                .withIdentity("noteRecordCount", "spider")
                .build();
        try {
            spiderScheduler.scheduleJob(job, trigger);      // 加入调度
        } catch (SchedulerException e) {
            logger.error("Spider Speed Count Trigger Start Failed .");
        }
    }

    /**
     * Job不支持spring的依赖注入
     * 所以这里用setter进行直接设置
     */
    private void setBeans() {
        NoteRecordCount.setStatisticsDao(this.statisticsDao);
        NoteRecordCount.setSpiderDataPojo(this.spiderDataPojo);
    }


}
