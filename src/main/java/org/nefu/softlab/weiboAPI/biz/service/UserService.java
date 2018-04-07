package org.nefu.softlab.weiboAPI.biz.service;

import org.nefu.softlab.weiboAPI.core.PO.Log;
import org.nefu.softlab.weiboAPI.core.PO.User;

import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * 用户模块业务逻辑
 */
public interface UserService {

    /**
     * 根据用户名和密码获取用户
     * @param user
     * @return user/null
     */
    User getUserByUsernameAndPasswd(User user);

    /**
     * 根据用户名获取用户
     * @param username
     * @return user/null
     */
    User getUserByUsername(String username);

    /**
     * 用户注册
     * @param user
     * @return
     */
    boolean addUserRecord(User user);

    /**
     * 新建用户登录记录并持久化
     * @param user
     * @param log
     * @param oldLog
     * @return map/null
     */
    Map addLoginRecord(User user, Log log, Log oldLog);

    /**
     * 获取用户最近的登录记录
     * @param user
     * @return log/null
     */
    Log getLastLog(User user);

    /**
     * 为用户设置新的密码
     * @param user
     * @return true/false
     */
    boolean setNewPasswd(User user, String newpasswd);

    /**
     * 用户注销
     * 具体逻辑是，将token在数据库中置为空
     * @param user
     * @return true/false
     */
    boolean deleteLogin(User user);

    /**
     * 根据token获取用户
     * @param token
     * @return user/null
     */
    User getUserByToken(String token);

}
