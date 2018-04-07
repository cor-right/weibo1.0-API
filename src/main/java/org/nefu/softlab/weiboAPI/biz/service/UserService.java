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
     * @return
     */
    User getUserByUsernameAndPasswd(User user);

    /**
     * 新建用户登录记录并持久化
     * @param user
     * @param log
     * @param oldLog
     * @return map
     */
    Map addLoginRecord(User user, Log log, Log oldLog);


    /**
     * 获取用户最近的登录记录
     * @param user
     * @return log
     */
    Log getLastLog(User user);

}
