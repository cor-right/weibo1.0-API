package org.nefu.softlab.weiboAPI.biz.trigger.scheduler.job;

import org.nefu.softlab.weiboAPI.biz.trigger.scheduler.impl.SpiderSchedulerImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Jiaxu_Zou on 2018-4-10
 *
 * 这个Job执行的是
 * 每秒钟都去更新速率
 */
public class NoteRecordCountPerSec implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(SpiderSchedulerImpl.oldcount++);
    }
}
