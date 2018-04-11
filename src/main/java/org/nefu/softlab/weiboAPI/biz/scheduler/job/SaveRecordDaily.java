package org.nefu.softlab.weiboAPI.biz.scheduler.job;

import com.mongodb.ServerAddress;
import org.nefu.softlab.weiboAPI.common.util.DateUtil;
import org.nefu.softlab.weiboAPI.common.util.UUIDUtil;
import org.nefu.softlab.weiboAPI.core.dao.mapper.DailyRecordMapper;
import org.nefu.softlab.weiboAPI.core.dao.mongo.StatisticsDao;
import org.nefu.softlab.weiboAPI.core.po.DailyRecord;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jiaxu_Zou on 2018-4-11
 *
 * 每天的最后一秒执行的操作
 * 查询当前所有数据的数据量，并且保存到数据库中
 */
@Service
@Transactional
public class SaveRecordDaily implements Job, Serializable{

    // mapper
    private static DailyRecordMapper dailyRecordMapper;

    // dao
    private static StatisticsDao statisticsDao;

    // logger
    private static final Logger logger  = LoggerFactory.getLogger(SaveRecordDaily.class);


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 解析数据并逐个构建DailyRecord的PO对象
        List<DailyRecord> records = new LinkedList<>();
        String curDate = DateUtil.getCurDate(); // 统一取时间防止因为运算超时导致日期错误
        // 初始化总属性的记录
        DailyRecord summary = new DailyRecord();    // 这个summary计算的是总数，在数据库里用socket : 0.0.0.0:27017表示
        summary.setRid(UUIDUtil.getRandomID());
        summary.setSocket("0.0.0.0:27017");
        summary.setRecordnumber(0);
        summary.setRecordsize(0D);
        summary.setDate(curDate);
        summary.setSavetimestamp(DateUtil.getCurTimeStamp());
        // 分别设置每个属性并计算总属性中的recordsize和recordnumber
        statisticsDao.getSplitedStatistics().stream()   // 首先查询各个集群的数据量
                .forEach(map -> {
                    DailyRecord record = new DailyRecord();
                    // 设置单个记录属性
                    record.setRid(UUIDUtil.getRandomID());      // 设置RID
                    record.setSocket(((ServerAddress)map.get("host")).getSocketAddress().toString().replace("/", ""));   // 设置socket
                    record.setRecordnumber((Integer)map.get("count"));  // 设置记录数
                    record.setRecordsize((Double)map.get("storageSize"));    // 设置存储容量
                    record.setDate(curDate);  // 设置当前日期
                    record.setSavetimestamp(DateUtil.getCurTimeStamp());        // 设置记录保存的时间
                    // 设置总属性
                    summary.setRecordsize(summary.getRecordsize() + record.getRecordsize());
                    summary.setRecordnumber(summary.getRecordnumber() + record.getRecordnumber());
                    // 将单机的记录存入数据库
                    dailyRecordMapper.insert(record);
                });
        dailyRecordMapper.insert(summary);  // 将总属性记录插入数据库
        logger.info("Save daily record data into database succeed .");
    }

    public static void setDailyRecordMapper(DailyRecordMapper dailyRecordMapper) {
        SaveRecordDaily.dailyRecordMapper = dailyRecordMapper;
    }

    public static void setStatisticsDao(StatisticsDao statisticsDao) {
        SaveRecordDaily.statisticsDao = statisticsDao;
    }
}
