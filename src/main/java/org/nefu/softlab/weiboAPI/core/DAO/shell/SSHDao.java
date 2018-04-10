package org.nefu.softlab.weiboAPI.core.DAO.shell;


import ch.ethz.ssh2.Connection;
import org.nefu.softlab.weiboAPI.common.config.SSHConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SSHDao extends BaseDao {

    // connections
    private static final List<Connection> connections;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(SSHDao.class);

    static {
        connections = new ArrayList<>();   // 会话
        List<Map<String, String>> configList = SSHConfig.configList;
        configList.stream() // 遍历所有配置信息创建所有可创建的连接并最终初始化会话
                .forEach(map -> {
                    try {
                        Connection connection = new Connection(map.get("hostname"));    // 配置连接
                        connection.connect();   // 尝试连接
                        if (connection.authenticateWithPassword(map.get("username"), map.get("password")) == false)  // 权限验证
                            throw new IOException();
                        connections.add(connection);    // 保存连接
                    } catch (IOException e) {
                        logger.warn("SSH Connect to server " + map.get("hostname") + " failed .");
                    }
                });
        logger.info("SSH Session built successfully . Session count is " + connections.size());
    }

    public static List<Connection> getConnections() {
        return connections;
    }

    /**
     * 获取各个mongoDB数据库服务器的存储状态
     * @return
     */
    public List<Map<String, Object>> getServerMemStatus() {
        List<Map<String, Object>> returnList = new ArrayList<>();
        connections.stream().forEach(
                connection -> {
                    Map<String, Object> data = new HashMap<>();
                    // 进行计算，尽量减少进行SSH会话的次数
                    long all = super.getTotalMemory(connection);
                    long available = super.getAvailableMemory(connection);
                    long used = all - available;
                    double rate = (double)used / (double)all;
                    // 装配数据并返回
                    data.put("host", super.getIP(connection));
                    data.put("used", used);
                    data.put("all", all);
                    data.put("rate", rate);
                    returnList.add(data);
                }
        );
        return returnList;
    }

    /**
     * 获取各个mongoDB服务器的磁盘占用情况
     * @return
     */
    public List<Map<String, Object>> getServerDiskStatus() {
        List<Map<String, Object>> returnList = new ArrayList<>();
        connections.stream()
                .forEach(connection -> {
                    Map<String, Object> data = new HashMap<>();
                    // 进行计算，尽量减少进行SSH会话的次数
                    long all = super.getTotalDiskSpace(connection);
                    long available = super.getAvailableDiskSpace(connection);
                    long used = all - available;
                    double rate = (double)used / (double)all;
                    // 装配数据并返回
                    data.put("host", super.getIP(connection));
                    data.put("used", used);
                    data.put("all", all);
                    data.put("rate", rate);
                    returnList.add(data);
                });
        return returnList;
    }

}
