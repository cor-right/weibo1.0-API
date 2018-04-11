package org.nefu.softlab.weiboAPI.biz.service;

import java.util.Map;

/**
 * 爬虫监控相关的业务逻辑
 */
public interface SpiderService {

    /**
     * 获取Ippool的相关信息
     * @return
     */
    Map getIppoolData();

    /**
     * 获取爬虫的状态信息
     * @return
     */
    Map getStatus();


}
