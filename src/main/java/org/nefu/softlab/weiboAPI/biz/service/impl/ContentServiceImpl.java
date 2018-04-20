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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-15
 */
@Service
@Transactional
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

    /**
     * 获取模糊查询是可能相关的用户ID
     * @param vo
     * @return
     */
    @Override
    public List<String> getUids(RecordsSelectVo vo) {
        if (vo.getUname() != null && vo.getUname().trim().equals("") == false) {
            List<String> uids = new LinkedList<>();
            String uid = weiboUserMapper.selectUidByName(vo.getUname());
            if (uid != null && uid.equals("") == false)
                uids.add(uid);
            else
                uids = weiboUserMapper.selectUidsbyName(vo);
            return uids;
        }
        else
            return null;
    }

    @Override
    public PageInfo<WeiboData> getSpecificRecords(RecordsSelectVo vo) {
        // 设置查询的参数
        HashMap<String, Object> condition = new HashMap<>();
        // 获取时间范围
        if (vo.getDateFrom() != null && vo.getDateFrom().trim().equals("") == false)
            condition.put("from",
                    DateUtil.transferDateToMills(DateUtil.parseDate(vo.getDateFrom())));
        if (vo.getDateTo() != null && vo.getDateTo().trim().equals("") == false)
            condition.put("to",
                    DateUtil.transferDateToMills(DateUtil.parseDate(vo.getDateTo())));
        condition.put("uid", weiboUserMapper.selectUidByName(vo.getUname()));
//         设置分页
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());    // 启动分页查询
        List<WeiboData> weiboData = weiboDataMapper.selectRecords(condition);  // 这里是固定的查找
        System.out.println(weiboData.size());
        PageInfo<WeiboData> page = new PageInfo<>(weiboData);
        return page;
    }

    @Override
    public String getUid(RecordsSelectVo vo) {
        return weiboUserMapper.selectUidByName(vo.getUname());
    }

}
