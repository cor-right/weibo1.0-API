package org.nefu.softlab.weiboAPI.core.DAO.shell;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import org.junit.jupiter.api.Test;
import org.nefu.softlab.weiboAPI.common.constant.SSHConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SSHDao extends BaseDao {

    // connections
    private static final List<Session> sessions;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(SSHDao.class);

    static {
        sessions = new ArrayList<>();   // 会话
        List<Map<String, String>> configList = SSHConfig.configList;
        configList.stream() // 遍历所有配置信息创建所有可创建的连接并最终初始化会话
                .forEach(map -> {
                    try {
                        Connection connection = new Connection(map.get("hostname"));    // 配置连接
                        connection.connect();   // 尝试连接
                        if (connection.authenticateWithPassword(map.get("username"), map.get("password")) == false)  // 权限验证
                            throw new IOException();
                        sessions.add(connection.openSession());     // 开启会话
                    } catch (IOException e) {
                        logger.warn("SSH Connect to server " + map.get("hostname") + " failed .");
                    }
                });
        logger.info("SSH Session built successfully . Session count is " + sessions.size());
    }



    @Test
    public void test() {
        sessions.stream()
                .forEach(session -> {
                    BufferedReader br = getSout(session);
                    System.out.println(getIP(session, br));
                    try {
                        session.execCommand("cat /proc/meminfo | grep MemAva | awk '{print $2}'");  // 获取可用内存大小
                        System.out.println(br.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }


}
