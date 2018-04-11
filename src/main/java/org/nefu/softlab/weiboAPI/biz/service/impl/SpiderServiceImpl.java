package org.nefu.softlab.weiboAPI.biz.service.impl;

import org.nefu.softlab.weiboAPI.biz.service.SpiderService;
import org.nefu.softlab.weiboAPI.common.util.DateUtil;
import org.nefu.softlab.weiboAPI.common.util.PropertiesUtil;
import org.nefu.softlab.weiboAPI.core.dao.redis.IPPoolDao;
import org.nefu.softlab.weiboAPI.core.pojo.SpiderDataPojo;
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

    // static
    private static final String SPIDER_PROPERTY_FILENAME = "spider.properties";

    // pojo
    private final SpiderDataPojo spiderDataPojo;

    @Autowired
    public SpiderServiceImpl(IPPoolDao ippoolDao, SpiderDataPojo spiderDataPojo) {
        this.ippoolDao = ippoolDao;
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

}
