package org.nefu.softlab.weiboAPI.biz.service.impl;

import org.nefu.softlab.weiboAPI.biz.service.SpiderService;
import org.nefu.softlab.weiboAPI.common.util.DateUtil;
import org.nefu.softlab.weiboAPI.core.DAO.redis.IPPoolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpiderServiceImpl implements SpiderService{

    // dap
    private final IPPoolDao ippoolDao;

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
}
