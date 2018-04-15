package org.nefu.softlab.weiboAPI.biz.service.impl;

import org.nefu.softlab.weiboAPI.biz.service.ContentService;
import org.nefu.softlab.weiboAPI.core.dao.mapper.WeiboUserMapper;
import org.nefu.softlab.weiboAPI.core.dao.mongo.StatisticsDao;
import org.nefu.softlab.weiboAPI.core.po.WeiboUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-15
 */
@Service
public class ContentServiceImpl implements ContentService{

    // mapper
    private final WeiboUserMapper weiboUserMapper;

    // dao
    private final StatisticsDao statisticsDao;

    @Autowired
    public ContentServiceImpl(WeiboUserMapper weiboUserMapper, StatisticsDao statisticsDao) {
        this.weiboUserMapper = weiboUserMapper;
        this.statisticsDao = statisticsDao;
    }


    @Override
    public List<WeiboUser> getFamousSinaUser() {
        return weiboUserMapper.getFamousUsers();
    }

    @Override
    public Map<String, Object> getRecordsNumber(Boolean user, Boolean weibo) {
        Map<String, Object> returnMap = new HashMap<>();
        if (user)  // 获取用户记录信息
            returnMap.put("user", weiboUserMapper.selectUserCount());
        if (weibo)
            returnMap.put("weibo", statisticsDao.getTotalRecordCount());
        return returnMap;
    }

}
