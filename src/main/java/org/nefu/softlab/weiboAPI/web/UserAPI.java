package org.nefu.softlab.weiboAPI.web;

import com.mongodb.util.JSON;
import org.junit.jupiter.api.Test;
import org.nefu.softlab.weiboAPI.biz.service.UserService;
import org.nefu.softlab.weiboAPI.common.RESTData;
import org.nefu.softlab.weiboAPI.common.constant.SSHConfig;
import org.nefu.softlab.weiboAPI.common.util.JsonUtil;
import org.nefu.softlab.weiboAPI.common.util.LogUtil;
import org.nefu.softlab.weiboAPI.common.util.MD5Util;
import org.nefu.softlab.weiboAPI.common.util.TokenUtil;
import org.nefu.softlab.weiboAPI.core.PO.Log;
import org.nefu.softlab.weiboAPI.core.PO.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

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
        newLogRecord.setUid(user.getUid());
        // 添加登陆记录
        Map returnData = userService.addLoginRecord(user, newLogRecord, lastLogRecord);
        return returnData != null ? new RESTData(returnData)
                : new RESTData(1, "登录失败，请稍后重试");
    }

    /**
     * 用户注册
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public RESTData postRegister(@RequestBody User user, HttpServletRequest request) {
        logger.info("POST Post Register : " + JsonUtil.getJsonString(user));
        // 做一些最基本的格式判断
        if (user.getUsername() == null || user.getPasswd() == null || user.getUsername().trim().equals("") == true || user.getPasswd().trim().equals("") == true)
            return new RESTData(1, "用户名或密码不能为空");
        // 直接注册
        return userService.addUserRecord(user) == true ? new RESTData()
                : new RESTData(1, "请检查您的注册信息");
    }

    /**
     * 用户名判重
     * @param username
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public RESTData getRegister(@RequestParam("username") String username) {
        logger.info("GET Get Register : { username : " +  username + " } ");
        // 直接查然后返回
        return userService.getUserByUsername(username) == null ? new RESTData(true)
                : new RESTData(1, "用户名已存在", false);
    }

    /**
     * 用户修改密码
     * @param oldpasswd
     * @param newpasswd
     * @param request
     * @return
     */
    @RequestMapping(value = "passwd", method = RequestMethod.PUT)
    public RESTData putPasswd(@RequestParam("oldpasswd") String oldpasswd, @RequestParam("newpasswd") String newpasswd, HttpServletRequest request) {
        logger.info("PUT Put Passwd : { oldpasswd : " + oldpasswd + " , newpasswd : " + newpasswd + "}");
        // 判断旧密码是否正确
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)   // 登陆状态不正确
            return new RESTData(1, "请检查当前登陆状态");
        else if (oldpasswd.equals(newpasswd) == true)   // 密码必须不同
            return new RESTData(1, "新密码必须和旧密码不同");
        else if (user.getPasswd().equals(MD5Util.MD5(oldpasswd)) == false)     // 旧密码不正确
            return new RESTData(1, "密码输入错误");
        // 设置新密码
        return userService.setNewPasswd(user, newpasswd) == true ? new RESTData()
                : new RESTData(1, "密码修改失败，请稍后重试");
    }

    /**
     * 用户注销登录
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.DELETE)
    public RESTData deleteLogin(HttpServletRequest request) {
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        logger.info("DELETE Delete Login : " + JsonUtil.getJsonString(user));
        if (user == null)   // 登陆状态不正确
            return new RESTData(1, "请检查当前登陆状态");
        // 执行注销逻辑
        return userService.deleteLogin(user) == true ? new RESTData()
                : new RESTData(1, "注销失败，请稍后重试");
    }


}
