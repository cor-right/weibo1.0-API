package org.nefu.softlab.weiboAPI.biz.service.impl;

import org.nefu.softlab.weiboAPI.biz.service.UserService;
import org.nefu.softlab.weiboAPI.common.util.MD5Util;
import org.nefu.softlab.weiboAPI.common.util.TokenUtil;
import org.nefu.softlab.weiboAPI.common.util.UUIDUtil;
import org.nefu.softlab.weiboAPI.core.DAO.mapper.LogMapper;
import org.nefu.softlab.weiboAPI.core.DAO.mapper.UserMapper;
import org.nefu.softlab.weiboAPI.core.PO.Log;
import org.nefu.softlab.weiboAPI.core.PO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    // mapper
    private final UserMapper userMapper;
    private final LogMapper logMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, LogMapper logMapper) {
        this.userMapper = userMapper;
        this.logMapper = logMapper;
    }

    @Override
    public User getUserByUsernameAndPasswd(User user) {
        return userMapper.selectUserByUsernameAndPasswd(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public boolean addUserRecord(User user) {
        user.setUid(UUIDUtil.getRandomID());
        user.setPasswd(MD5Util.MD5(user.getPasswd()));
        try {
            return userMapper.insert(user) != 0 ? true : false;
        } catch(RuntimeException ex) {
            return false;
        }
    }

    @Override
    public Map addLoginRecord(User user, Log log, Log oldLog) {
        // 先持久化log
        if (logMapper.insert(log) == 0)
            return null;
        // 再绑定user和新插入的log
        user.setLastlogin(log.getLogid());
        // 生成新的token并进行返回值的配置
        user.setToken(TokenUtil.newToken());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("username", user.getUsername());
        returnMap.put("token", user.getToken());
        returnMap.put("lastLogin", oldLog == null ? null : oldLog.getTimestamp());
        return userMapper.updateByPrimaryKey(user) != 0 ? returnMap : null;
    }

    @Override
    public Log getLastLog(User user) {
        if (user.getLastlogin() == null)
            return null;
        else
            return logMapper.selectByPrimaryKey(user.getLastlogin());
    }

    @Override
    public boolean setNewPasswd(User user, String newpasswd) {
        user.setPasswd(MD5Util.MD5(newpasswd));
        return userMapper.updateByPrimaryKey(user) != 0 ? true : false;
    }

    @Override
    public boolean deleteLogin(User user) {
        user.setToken(null);
        return userMapper.updateByPrimaryKey(user) != 0 ? true : false;
    }

    @Override
    public User getUserByToken(String token) {
        return userMapper.selectUserByToken(token);
    }
}
