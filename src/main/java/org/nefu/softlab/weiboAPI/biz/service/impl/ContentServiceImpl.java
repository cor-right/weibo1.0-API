package org.nefu.softlab.weiboAPI.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.nefu.softlab.weiboAPI.biz.service.ContentService;
import org.nefu.softlab.weiboAPI.common.util.DateUtil;
import org.nefu.softlab.weiboAPI.core.VO.RecordsSelectVo;
import org.nefu.softlab.weiboAPI.core.dao.mapper.WeiboDataMapper;
import org.nefu.softlab.weiboAPI.core.dao.mapper.WeiboUserMapper;
import org.nefu.softlab.weiboAPI.core.dao.mongo.StatisticsDao;
import org.nefu.softlab.weiboAPI.core.po.WeiboData;
import org.nefu.softlab.weiboAPI.core.po.WeiboUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-15
 */
@Service
public class ContentServiceImpl implements ContentService {

    // mapper
    private final WeiboUserMapper weiboUserMapper;
    private final WeiboDataMapper weiboDataMapper;

    // dao
    private final StatisticsDao statisticsDao;

    @Autowired
    public ContentServiceImpl(WeiboUserMapper weiboUserMapper, WeiboDataMapper weiboDataMapper, StatisticsDao statisticsDao) {
        this.weiboUserMapper = weiboUserMapper;
        this.weiboDataMapper = weiboDataMapper;
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

    @Override
    public Map getSpecificRecords(RecordsSelectVo vo) {
        // 设置查询的参数
        Map<String, Object> condition = new HashMap<>();
        // 获取时间范围
        if (vo.getDateFrom() != null && vo.getDateFrom().trim().equals("") == false)
            condition.put("from",
                    DateUtil.transferDateToMills(DateUtil.parseDate(vo.getDateFrom())));
        if (vo.getDateTo() != null && vo.getDateTo().trim().equals("") == false)
            condition.put("to",
                    DateUtil.transferDateToMills(DateUtil.parseDate(vo.getDateTo())));
        // 如果设置了用户的限定，先精确查询用户的uid，之后再模糊查询，这里顺便确定用户的数量
//        int userNum = 0;
//        int weiboNum = 0;
        if (vo.getUname() != null && vo.getUname().trim().equals("") == false) {
            List<String> uids = new LinkedList<>();
            String uid = weiboUserMapper.selectUidByName(vo.getUname());
            if (uid != null && uid.equals("") == false) {
                uids.add(uid);
//                userNum = 1;    // 更新用户数
            }
            else {
                uids = weiboUserMapper.selectUidsbyName(vo);
//                userNum = 100;  // 更新用户数
            }
//            weiboNum = weiboDataMapper.selectWeiboCount(uids);  // 更新微博数
            condition.put("uids", uids);    // 设置微博查询的参数
        }
        // 设置分页
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<WeiboData> weiboData = weiboDataMapper.selectRecords((HashMap) condition);
        PageInfo pageInfo = new PageInfo<>(weiboData, vo.getPageSize());
        // 配置返回信息
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("records", pageInfo);
//        returnMap.put("weibo", weiboNum);
//        returnMap.put("user", userNum);
        return returnMap;
    }

}
