package org.nefu.softlab.weiboAPI.web;

import org.nefu.softlab.weiboAPI.biz.service.UserService;
import org.nefu.softlab.weiboAPI.common.RESTData;
import org.nefu.softlab.weiboAPI.core.PO.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * 用户模块
 */
@Controller
@RequestMapping("/api/user")
public class UserAPI {

    // serivec
    private final UserService userService;

    // logger
    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserAPI.class);

    @Autowired
    public UserAPI(UserService userService) {
        this.userService = userService;
    }


    /**
     * 用户登录
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public RESTData postLogin(User user, HttpServletRequest request) {
        // 检查参数是否存在
        if (user.getUsername() == null || user.getPasswd() == null || user.getUsername().trim().equals("") == true || user.getPasswd().trim().equals("") == true)
            return new RESTData(1, "用户名或密码不能为空");
        // 查询
        return null;
    }


}
