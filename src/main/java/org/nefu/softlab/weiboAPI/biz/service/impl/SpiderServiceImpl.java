package org.nefu.softlab.weiboAPI.biz.service.impl;

import com.mongodb.ServerAddress;
import org.nefu.softlab.weiboAPI.biz.service.SpiderService;
import org.nefu.softlab.weiboAPI.common.util.DateUtil;
import org.nefu.softlab.weiboAPI.common.util.PropertiesUtil;
import org.nefu.softlab.weiboAPI.core.dao.mapper.DailyRecordMapper;
import org.nefu.softlab.weiboAPI.core.dao.mongo.StatisticsDao;
import org.nefu.softlab.weiboAPI.core.dao.redis.IPPoolDao;
import org.nefu.softlab.weiboAPI.core.dao.shell.SSHDao;
import org.nefu.softlab.weiboAPI.core.po.DailyRecord;
import org.nefu.softlab.weiboAPI.core.pojo.SpiderDataPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class SpiderServiceImpl implements SpiderService{

    // mapper
    private final DailyRecordMapper dailyRecordMapper;

    // dao
    private final IPPoolDao ippoolDao;
    private final SSHDao sshDao;
    private final StatisticsDao statisticsDao;

    // static
    private static final String SPIDER_PROPERTY_FILENAME = "spider.properties";

    // pojo
    private final SpiderDataPojo spiderDataPojo;

    @Autowired
    public SpiderServiceImpl(DailyRecordMapper dailyRecordMapper, IPPoolDao ippoolDao, SSHDao sshDao, StatisticsDao statisticsDao, SpiderDataPojo spiderDataPojo) {
        this.dailyRecordMapper = dailyRecordMapper;
        this.ippoolDao = ippoolDao;
        this.sshDao = sshDao;
        this.statisticsDao = statisticsDao;
        this.spiderDataPojo = spiderDataPojo;
    }

    @Override
    public Map getIppoolData() {
        Map<String, Object> ippoolData = new HashMap<>();
        //
        List datelist = ippoolDao.getPoolRefreshTime();
        List poollist = ippoolDao.getIPList();
        String oldtime = (String) datelist.get(1);
        String newtime = (String) datelist.get(0);
        long interval = DateUtil.getTimeInterval(oldtime, newtime) / 1000;
        //
        ippoolData.put("status", true);
        ippoolData.put("lastRefresh", newtime);
        ippoolData.put("refreshInterval", interval);
        ippoolData.put("pool", poollist);
        return ippoolData;
    }

    @Override
    public Map getStatus() {
        Map<String, Object> returnMap = new HashMap<>();
        // 获取节点数
        int nodecount = PropertiesUtil.getPropertiesIntValue(SPIDER_PROPERTY_FILENAME, "spider.node.enable");
        boolean enablestatus = PropertiesUtil.getPropertiesBooleanValue(SPIDER_PROPERTY_FILENAME, "spider.status.enable");
        double currate = spiderDataPojo.getSpeedInOneSec();
        double fiveminRate = spiderDataPojo.getSpeedInFiveMin();
        double avgrate = currate / nodecount;
        // 配置数据并返回
        returnMap.put("status", enablestatus);
        returnMap.put("nodeCount", nodecount);
        returnMap.put("curRate", currate);
        returnMap.put("curAvgRate", avgrate);
        returnMap.put("rateInFive", fiveminRate);
        return returnMap;
    }

    @Override
    public List getSevenday() {
        List<Map<String, Object>> returnList = new LinkedList<>();
        dailyRecordMapper.getLastSevenDayRecord().stream()  // 获取七天内的数据并且遍历
                .forEach(dailyRecord -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("date", dailyRecord.getDate());
                    data.put("count", dailyRecord.getRecordnumber());
                    data.put("disk", dailyRecord.getRecordsize());
                    returnList.add(data);
                });
        return returnList;
    }

    @Override
    public List<Map<String, Object>> getMemoryStatus() {
        return sshDao.getServerMemStatus();
    }

    @Override
    public List<Map<String, Object>> getTodayIncreasement() {
        List<Map<String, Object>> returnList = new LinkedList<>();
        // 获取当前集群各机器的数据
        List<Map<String, Object>> currentStatus = statisticsDao.getSplitedStatistics();     // 当前最新的记录
        currentStatus.sort(Comparator.comparingInt(a -> ((ServerAddress)a.get("host")).getHost().hashCode()));
        List<DailyRecord> lastDayRecords = dailyRecordMapper.getLastDayRecord();        // 昨天的记录
        // 遍历并进行配置
        for (int i = 0; i < currentStatus.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("host", lastDayRecords.get(i).getSocket()
                    .substring(0, lastDayRecords.get(i).getSocket().indexOf(":"))); // 设置host
            map.put("grow_disk", (Double)currentStatus.get(i).get("storageSize") - lastDayRecords.get(i).getRecordsize());  // 设置增长的记录容量
            map.put("grow_count", (Integer)currentStatus.get(i).get("count") - lastDayRecords.get(i).getRecordnumber());    // 设置增长的记录条数
            returnList.add(map);
        }
        return returnList;
    }

}
