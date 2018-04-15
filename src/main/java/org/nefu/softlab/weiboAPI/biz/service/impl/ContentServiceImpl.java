package org.nefu.softlab.weiboAPI.biz.service.impl;

import org.nefu.softlab.weiboAPI.biz.service.ContentService;
import org.nefu.softlab.weiboAPI.core.dao.mapper.WeiboUserMapper;
import org.nefu.softlab.weiboAPI.core.po.User;
import org.nefu.softlab.weiboAPI.core.po.WeiboUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jiaxu_Zou on 2018-4-15
 */
@Service
public class ContentServiceImpl implements ContentService{

    // mapper
    private final WeiboUserMapper weiboUserMapper;

    @Autowired
    public ContentServiceImpl(WeiboUserMapper weiboUserMapper) {
        this.weiboUserMapper = weiboUserMapper;
    }


    @Override
    public List<WeiboUser> getFamousSinaUser() {
        return weiboUserMapper.getFamousUsers();
    }

}
