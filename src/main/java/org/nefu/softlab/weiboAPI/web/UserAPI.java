package org.nefu.softlab.weiboAPI.web;

import org.nefu.softlab.weiboAPI.biz.service.UserService;
import org.nefu.softlab.weiboAPI.common.RESTData;
import org.nefu.softlab.weiboAPI.common.util.LogUtil;
import org.nefu.softlab.weiboAPI.core.PO.Log;
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
        // 检测
        user = userService.getUserByUsernameAndPasswd(user);
        if (user == null)
            return new RESTData(1, "用户名或密码不匹配");
        // 构建新的登陆记录
        Log newLogRecord = LogUtil.getLog(request, userService.getLastLog(user));
        // 添加登陆记录
        return userService.addLoginRecord(user) == true ? new RESTData()
                : new RESTData(1, "服务器无法记录您的登录记录，请稍后重试");
    }


}
