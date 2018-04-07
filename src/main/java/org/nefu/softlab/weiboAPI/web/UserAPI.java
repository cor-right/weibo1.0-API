package org.nefu.softlab.weiboAPI.web;

import org.nefu.softlab.weiboAPI.biz.service.UserService;
import org.nefu.softlab.weiboAPI.common.RESTData;
import org.nefu.softlab.weiboAPI.common.util.JsonUtil;
import org.nefu.softlab.weiboAPI.common.util.LogUtil;
import org.nefu.softlab.weiboAPI.core.PO.Log;
import org.nefu.softlab.weiboAPI.core.PO.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * 用户模块
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api/user")
@RestController
public class UserAPI {

    // service
    private final UserService userService;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(UserAPI.class);

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
    public RESTData postLogin(@RequestBody User user, HttpServletRequest request) {
        logger.info("POST Post Login : " + JsonUtil.getJsonString(user));
        // 检查参数是否存在
        if (user.getUsername() == null || user.getPasswd() == null || user.getUsername().trim().equals("") == true || user.getPasswd().trim().equals("") == true)
            return new RESTData(1, "用户名或密码不能为空");
        // 检测
        user = userService.getUserByUsernameAndPasswd(user);
        if (user == null)
            return new RESTData(1, "用户名或密码不匹配");
        // 构建新的登陆记录
        Log lastLogRecord = userService.getLastLog(user);
        Log newLogRecord = LogUtil.getLog(request, lastLogRecord);
        // 添加登陆记录
        Map returnData = userService.addLoginRecord(user, newLogRecord, lastLogRecord);
        return returnData != null ? new RESTData(returnData)
                : new RESTData(1, "登录失败，请稍后重试");
    }


}
