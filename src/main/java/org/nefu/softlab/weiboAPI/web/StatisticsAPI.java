package org.nefu.softlab.weiboAPI.web;

import org.nefu.softlab.weiboAPI.biz.service.StatisticsService;
import org.nefu.softlab.weiboAPI.biz.service.UserService;
import org.nefu.softlab.weiboAPI.common.RESTData;
import org.nefu.softlab.weiboAPI.common.util.JsonUtil;
import org.nefu.softlab.weiboAPI.common.util.TokenUtil;
import org.nefu.softlab.weiboAPI.core.PO.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-7
 *
 * 数据量统计模块
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api/statistics")
@RestController
public class StatisticsAPI {

    // service
    private final UserService userService;
    private final StatisticsService statisticsService;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(StatisticsAPI.class);

    @Autowired
    public StatisticsAPI(UserService userService, StatisticsService statisticsService) {
        this.userService = userService;
        this.statisticsService = statisticsService;
    }

    /**
     * mongoDB数据状态查询-多主机
     * @param request
     * @return
     */
    @RequestMapping(value = "splited", method = RequestMethod.GET)
    public RESTData getSplitedStatistics(HttpServletRequest request) {
        // 用户验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get Splited Statistics : " + JsonUtil.getJsonString(user));
        // 执行查询
        List<Map<String, Object>> returnData = statisticsService.getSplitedStatistics();
        return returnData != null ? new RESTData(returnData)
                : new RESTData(1, "获取信息失败，请稍后重试");
    }

    /**
     * mongoDB数据状态查询-合计
     * @param request
     * @return
     */
    @RequestMapping(value = "total", method = RequestMethod.GET)
    public RESTData getTotalStatistics(HttpServletRequest request) {
        // 用户验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get Total Statistics : " + JsonUtil.getJsonString(user));
        // 执行查询
        Map<String, Object> returnData = statisticsService.getTotalStatistics();
        return returnData != null ? new RESTData(returnData)
                : new RESTData(1, "获取信息失败，请稍后重试");
    }

    /**
     * 指定数据库socket查询具体信息
     * @param socket
     * @param request
     * @return
     */
    @RequestMapping(value = "{socket}", method = RequestMethod.GET)
    public RESTData getSpecificServerStatistics(@PathVariable("socket") String socket, HttpServletRequest request) {
        // 用户验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get Specific Server Statistics : " + JsonUtil.getJsonString(user) + ", { socket : " + socket + "}");
        // 执行查询
        Map<String, Object> returnData = statisticsService.getSpecificServerStatistics(socket);
        return returnData != null ? new RESTData(returnData)
                : new RESTData(1, "请检查输入的套接字是否正确");
    }

    /**
     * 查询数据库服务器磁盘占用率
     * @param request
     * @return
     */
    @RequestMapping(value = "disk", method = RequestMethod.GET)
    public RESTData getDiskSpaceStatus(HttpServletRequest request) {
        // 用户验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get Disk Memory Status : " + JsonUtil.getJsonString(user));
        // 执行查询
        List returnData = statisticsService.getDiskSpaceStatus();
        return returnData != null ? new RESTData(returnData)
                : new RESTData(1, "当前系统繁忙请稍后重试");
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
        List returnData = statisticsService.getMemoryStatus();
        return returnData != null ? new RESTData(returnData)
                : new RESTData(1, "当前系统繁忙请稍后重试");
    }



}
