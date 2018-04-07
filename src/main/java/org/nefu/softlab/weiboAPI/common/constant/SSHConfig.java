package org.nefu.softlab.weiboAPI.common.constant;

import com.beust.jcommander.internal.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-7
 *
 * 操作系统的登陆信息
 */
public interface SSHConfig {

    List<Map<String, String>> configList = Arrays.asList(
            Maps.newHashMap("hostname", "222.27.227.104", "username", "hadoop-104", "password", "hadoop-104"),
            Maps.newHashMap("hostname", "222.27.227.107", "username", "hadoop-107", "password", "hadoop-107"),
            Maps.newHashMap("hostname", "222.27.227.110", "username", "hadoop-110", "password", "hadoop-110"),
            Maps.newHashMap("hostname", "222.27.227.113", "username", "hadoop-113", "password", "hadoop-113")
    );


}
