package org.nefu.softlab.weiboAPI.biz.trigger.scheduler.impl;

import org.nefu.softlab.weiboAPI.biz.trigger.scheduler.SpiderScheduler;
import org.nefu.softlab.weiboAPI.biz.trigger.scheduler.job.NoteRecordCountPerSec;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Component
public class SpiderSchedulerImpl implements SpiderScheduler{

    // scheduler
    private final Scheduler spiderScheduler = StdSchedulerFactory.getDefaultScheduler();

    // private
    public static long oldcount = 0;

    
    public SpiderSchedulerImpl() throws SchedulerException {
        // 设置一秒间隔的更新记录数的定时器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("oneSecondSpeedRecord", "spider")     // 设置name和group
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(1)       // 设置时间间隔，这里是1秒
                    .repeatForever()        // 设置循环次数，这里是无限
                ).build();
        // 设置Job
        JobDetail job = JobBuilder.newJob(NoteRecordCountPerSec.class)
                .withIdentity("noteRecordCountPerSec", "spider")
                .build();
        // 加入调度
        spiderScheduler.scheduleJob(job, trigger);
        // 启动scheduler
        spiderScheduler.start();
    }


    @Override
    public void startSpiderSpeedCountTrigger() {

    }
}
