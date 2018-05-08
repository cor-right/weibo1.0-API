package org.nefu.softlab.weiboAPI.common.config;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * 这里对多数据源的MONGO进行配置
 *
 * 目前这里是最基础的形式，即
 * 可以指定多个主机
 * 但是只能使用同一个数据库名字下的同一个集合
 */
public interface MongoConfig {

    String [] hostlist = {
            "222.27.227.104",
            "222.27.227.107",
            "222.27.227.110",
            "222.27.227.113"
    };

    String username = null;
    String passwd = null;

    int port = 27018;
    String database = "Sina";
    String collection = "weibo";

}
