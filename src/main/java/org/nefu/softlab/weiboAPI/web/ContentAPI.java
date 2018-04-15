package org.nefu.softlab.weiboAPI.web;

import org.nefu.softlab.weiboAPI.biz.service.ContentService;
import org.nefu.softlab.weiboAPI.biz.service.UserService;
import org.nefu.softlab.weiboAPI.common.RESTData;
import org.nefu.softlab.weiboAPI.common.util.LogUtil;
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

/**
 * Created by Jiaxu_Zou on 2018-4-15
 *
 * 数据检索模块
 * 该模块用于进行数据库中数据的检索
 * 包括用户的信息检索和微博的信息检索
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api/content")
@RestController
public class ContentAPI {

    // service
    private final UserService userService;
    private final ContentService contentService;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(ContentAPI.class);

    @Autowired
    public ContentAPI(UserService userService, ContentService contentService) {
        this.userService = userService;
        this.contentService = contentService;
    }

    @RequestMapping(value = "user/famous", method = RequestMethod.GET)
    public RESTData getFamousSinaUser(HttpServletRequest request) {
        // 用户验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get famous Sina User Data : " + LogUtil.getUserInfo(user));
        // 执行查询
        List returnList = contentService.getFamousSinaUser();
        return returnList == null ? new RESTData(1, "获取IP相关信息失败，请联系系统管理员")
                : new RESTData(returnList);
    }

}
