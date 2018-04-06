package org.nefu.softlab.weiboAPI.common.constant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * 这里对多数据源的MONGO进行配置
 *
 */
public interface MongoConfig {

    String [] hostlist = {
            "222.27.227.103",
            "222.27.227.107",
            "222.27.227.110",
            "222.27.227.113"
    };

    String username = null;
    String passwd = null;

    int port = 27017;
    String database = "Sina";
    String collection = "weibo";


}
