package org.nefu.softlab.weiboAPI.common.util;

import org.nefu.softlab.weiboAPI.core.po.Log;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * Util for User Login Log
 */
public class LogUtil {

    /**
     * 根据请求的参数获取登录记录
     * @param request
     * @return
     */
    public static Log getLog(HttpServletRequest request, Log lastLog) {
        Log log = new Log();
        // 设置基本参数
        log.setLogid(UUIDUtil.getRandomID());   // id
        log.setTimestamp(DateUtil.getCurTimeStamp()); // 本次登录的时间
        // 装载登录的统计信息
        int count = lastLog == null ? 1 : lastLog.getCount() + 1;
        log.setCount(count);    // 登录次数
        log.setLastlogin(lastLog == null ? null : lastLog.getLogid());   // 上条登录记录ID
        // 装载本次登陆的信息
        log.setUseragent(request.getHeader("user-agent"));  // 浏览器
        log.setIp(request.getRemoteAddr()); // IP
        log.setHostname(request.getRemoteHost());   // 客户端主机名
        return log;
    }

}
