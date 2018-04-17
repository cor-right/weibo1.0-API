package org.nefu.softlab.weiboAPI.common.config;

/**
 * Created by Jiaxu_Zou on 2018-4-17
 *
 * 记录一些爬虫的最基本的信息
 * 代替配置文件发挥作用
 */
public interface SpiderConfig {

    boolean SPIDER_ENBALE = true;   // 爬虫启用状态

    int NODE_COUNT = 12;    // 爬虫节点总数量
    int NODE_ENABLE = 8;    // 爬虫节点启动数量

}
