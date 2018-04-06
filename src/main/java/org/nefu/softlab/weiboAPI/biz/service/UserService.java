package org.nefu.softlab.weiboAPI.biz.service;

import org.nefu.softlab.weiboAPI.core.PO.User;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * 用户模块业务逻辑
 */
public interface UserService {

    /**
     * 根据用户名和密码获取用户
     * @return
     */
    User getUserByUsernameAndPasswd();

}
