package org.nefu.softlab.weiboAPI.biz.scheduler.impl;

import org.nefu.softlab.weiboAPI.biz.scheduler.SpiderScheduler;
import org.nefu.softlab.weiboAPI.biz.scheduler.job.NoteRecordCount;
import org.nefu.softlab.weiboAPI.biz.scheduler.job.SaveRecordDaily;
import org.nefu.softlab.weiboAPI.core.dao.mapper.DailyRecordMapper;
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

    // mapper
    private final DailyRecordMapper dailyRecordMapper;

    // dao
    private StatisticsDao statisticsDao;

    // pojo
    private SpiderDataPojo spiderDataPojo;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(SpiderSchedulerImpl.class);


    @Autowired
    public SpiderSchedulerImpl(DailyRecordMapper dailyRecordMapper, StatisticsDao statisticsDao, SpiderDataPojo spiderDataPojo) throws SchedulerException {
        this.dailyRecordMapper = dailyRecordMapper;
        this.statisticsDao = statisticsDao;
        this.spiderDataPojo = spiderDataPojo;
        this.setBeans(); // 设置依赖
        this.startSpiderSpeedCountTrigger();    // 设置爬虫速率的计算的定时触发器
        this.dailyRecordTrigger();  // 设定每天23:59:59记录集群数据量的定时触发器
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

    @Override
    public void dailyRecordTrigger() {
        // 设置一秒间隔的更新记录数的定时器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("dailyRecord", "spider")     // 设置name和group
                .withSchedule(CronScheduleBuilder.cronSchedule("59 59 23 ? * *")) // 设置触发的时间，参数分别代表：second, minute, hour, day, month, week，这里设置每天的23:59:59
                .build();
        JobDetail job = JobBuilder.newJob(SaveRecordDaily.class)      // 设置Job
                .withIdentity("saveRecordDaily", "spider")
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
        // 设置NoteRecordCount
        NoteRecordCount.setStatisticsDao(this.statisticsDao);
        NoteRecordCount.setSpiderDataPojo(this.spiderDataPojo);
        // 设置SaveRecordDaily
        SaveRecordDaily.setStatisticsDao(this.statisticsDao);
        SaveRecordDaily.setDailyRecordMapper(this.dailyRecordMapper);
    }


}
