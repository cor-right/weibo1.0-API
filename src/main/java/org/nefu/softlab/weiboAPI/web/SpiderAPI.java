package org.nefu.softlab.weiboAPI.web;

import org.nefu.softlab.weiboAPI.biz.service.SpiderService;
import org.nefu.softlab.weiboAPI.biz.service.UserService;
import org.nefu.softlab.weiboAPI.common.RESTData;
import org.nefu.softlab.weiboAPI.common.util.JsonUtil;
import org.nefu.softlab.weiboAPI.common.util.TokenUtil;
import org.nefu.softlab.weiboAPI.core.PO.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    @RequestMapping(value = "spider", method = RequestMethod.GET)
    public RESTData getIppoolData(HttpServletRequest request) {
        // 用户验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get Ippool Data : " + JsonUtil.getJsonString(user));
        // 执行查询

    }


}
