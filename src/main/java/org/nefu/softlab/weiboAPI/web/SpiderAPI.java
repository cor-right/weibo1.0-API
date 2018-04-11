package org.nefu.softlab.weiboAPI.web;

import org.nefu.softlab.weiboAPI.biz.service.SpiderService;
import org.nefu.softlab.weiboAPI.biz.service.UserService;
import org.nefu.softlab.weiboAPI.common.RESTData;
import org.nefu.softlab.weiboAPI.common.util.JsonUtil;
import org.nefu.softlab.weiboAPI.common.util.TokenUtil;
import org.nefu.softlab.weiboAPI.core.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-10
 *
 * 爬虫监控模块
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api/spider")
@RestController
public class SpiderAPI {

    // service
    private final SpiderService spiderService;
    private final UserService userService;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(SpiderAPI.class);

    @Autowired
    public SpiderAPI(SpiderService spiderService, UserService userService) {
        this.spiderService = spiderService;
        this.userService = userService;
    }

    /**
     * 获取IP池的相关信息
     * @param request
     * @return
     */
    @RequestMapping(value = "ippool", method = RequestMethod.GET)
    public RESTData getIppoolData(HttpServletRequest request) {
        // 用户验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get Ippool Data : " + JsonUtil.getJsonString(user));
        // 执行查询
        Map returnMap = spiderService.getIppoolData();
        return returnMap == null ? new RESTData(1, "获取IP相关信息失败，请联系系统管理员")
                : new RESTData(returnMap);
    }

    /**
     * 获取爬虫的速率等相关信息
     * @param request
     * @return
     */
    @RequestMapping(value = "status", method = RequestMethod.GET)
    public RESTData getStatus(HttpServletRequest request) {
        // 用户验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get Spider Status Data : " + JsonUtil.getJsonString(user));
        // 执行查询
        Map returnMap = spiderService.getStatus();
        return returnMap == null ? new RESTData(1, "获取爬虫状态信息失败，请联系系统管理员")
                : new RESTData(returnMap);
    }

    /**
     * 获取近七天内集群的容量数据
     * @param request
     * @return
     */
    @RequestMapping(value = "sevenday", method = RequestMethod.GET)
    public RESTData getSevenDayDataRange(HttpServletRequest request) {
        // 用户验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get Seven Day Status Data : " + JsonUtil.getJsonString(user));
        // 执行查询
        List returnMap = spiderService.getSevenday();
        return returnMap == null ? new RESTData(1, "获取爬虫状态信息失败，请联系系统管理员")
                : new RESTData(returnMap);
    }

    /**
     * 查询数据库服务器内存占用率
     * @param request
     * @return
     */
    @RequestMapping(value = "mem", method = RequestMethod.GET)
    public RESTData getMemoryStatus(HttpServletRequest request) {
        // 用户验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get Memory Status : " + JsonUtil.getJsonString(user));
        // 执行查询
        List returnData = spiderService.getMemoryStatus();
        return returnData != null ? new RESTData(returnData)
                : new RESTData(1, "当前系统繁忙请稍后重试");
    }



}
