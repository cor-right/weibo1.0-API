package org.nefu.softlab.weiboAPI.biz.scheduler;

/**
 * Created by Jiaxu_Zouo on 2018-4-10
 *
 * 爬虫定时任务的执行位置
 */
public interface SpiderScheduler {

    /**
     * 对爬虫速率进行记录的定时触发器执行方法
     * 通过直接改变SpiderServiceImpl中的值来达到目的
     */
    void startSpiderSpeedCountTrigger();



}
