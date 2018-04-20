package org.nefu.softlab.weiboAPI.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.nefu.softlab.weiboAPI.biz.service.ContentService;
import org.nefu.softlab.weiboAPI.biz.service.UserService;
import org.nefu.softlab.weiboAPI.common.RESTData;
import org.nefu.softlab.weiboAPI.common.util.LogUtil;
import org.nefu.softlab.weiboAPI.common.util.TokenUtil;

import org.nefu.softlab.weiboAPI.core.VO.RecordsSelectVo;
import org.nefu.softlab.weiboAPI.core.po.User;
import org.nefu.softlab.weiboAPI.core.po.WeiboData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    /**
     * 获取粉丝数TOP的微博大咖
     * @param request
     * @return
     */
    @RequestMapping(value = "user/famous", method = RequestMethod.GET)
    public RESTData getFamousSinaUser(HttpServletRequest request) {
        // 用户验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get famous Sina User Data : " + LogUtil.getUserInfo(user));
        // 执行查询
        List returnList = contentService.getFamousSinaUser();
        return returnList == null ? new RESTData(1, "获取微博TOP用户相关信息失败，请联系系统管理员")
                : new RESTData(returnList);
    }


    /**
     * 获取记录数
     * 可以根据参数自行选择获取哪种参数
     * 因为获取参数还是挺费时间的
     * @param request
     * @return
     */
    @RequestMapping(value = "number", method = RequestMethod.GET)
    public RESTData getRecordsNumber(@RequestParam("user") Boolean haveUser, @RequestParam("weibo") Boolean haveWeibo,  HttpServletRequest request) {
        // 验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (haveUser == null || haveWeibo == null)
            return new RESTData(1, "请检查您输入的参数是否正确");
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get famous Sina User Data : " + LogUtil.getUserInfo(user));
        // 执行查询
        Map returnMap = contentService.getRecordsNumber(haveUser, haveWeibo);
        return returnMap == null ? new RESTData(1, "获取系统中用户或微博数失败，请联系系统管理员")
                : new RESTData(returnMap);
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "records", method = RequestMethod.GET)
    public RESTData getSpecificRecords(RecordsSelectVo vo, HttpServletRequest request) {
        // 验证
        User user = userService.getUserByToken(TokenUtil.getToken(request));
        if (user == null)
            return new RESTData(1, "请检查当前登陆状态");
        logger.info("GET Get specific weibo records,  User :  : " + LogUtil.getUserInfo(user) + " | params : " + vo);
        // 执行查询，这里涉及到分页的逻辑
        PageInfo<WeiboData> page  = contentService.getSpecificRecords(vo);
        return page == null ?
                new RESTData(1, "查询微博数据失败，请联系系统管理员")
                : contentService.getUid(vo) == null ?  new RESTData(0, "未查询到当前用户的信息", page) : new RESTData(page);
    }








}
