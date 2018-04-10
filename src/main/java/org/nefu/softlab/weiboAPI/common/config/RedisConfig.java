package org.nefu.softlab.weiboAPI.common.config;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * 这里是对Redis进行配置
 *
 */
public interface RedisConfig {

    String host = "222.27.227.116";
    int port = 6379;

    String username = "";
    String password = "";

    int db = 0;
    String namespace = "weibo";

}
