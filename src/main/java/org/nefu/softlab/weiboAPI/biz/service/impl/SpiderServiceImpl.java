package org.nefu.softlab.weiboAPI.biz.service.impl;

import org.nefu.softlab.weiboAPI.biz.service.SpiderService;
import org.nefu.softlab.weiboAPI.common.util.DateUtil;
import org.nefu.softlab.weiboAPI.core.dao.redis.IPPoolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SpiderServiceImpl implements SpiderService{

    // dao
    private final IPPoolDao ippoolDao;

    // data
    private long recordCountFiveSec = 0;        // 五秒前的数据库的记录数
    private long recordCountOneSec = 0;        // 一秒后的数据库的记录数

    // logger

    @Autowired
    public SpiderServiceImpl(IPPoolDao ippoolDao) {
        this.ippoolDao = ippoolDao;
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

    // getter and setter

    public long getRecordCountFiveSec() {
        return recordCountFiveSec;
    }

    public void setRecordCountFiveSec(long recordCountFiveSec) {
        this.recordCountFiveSec = recordCountFiveSec;
    }

    public long getRecordCountOneSec() {
        return recordCountOneSec;
    }

    public void setRecordCountOneSec(long recordCountOneSec) {
        this.recordCountOneSec = recordCountOneSec;
    }
}
