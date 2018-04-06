package org.nefu.softlab.weiboAPI.biz.service.impl;

import org.nefu.softlab.weiboAPI.biz.service.UserService;
import org.nefu.softlab.weiboAPI.core.PO.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUsernameAndPasswd() {
        return null;
    }
}
