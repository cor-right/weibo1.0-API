package org.nefu.softlab.weiboAPI.common.component.connectionPool;

import ch.ethz.ssh2.Connection;
import com.mongodb.MongoClient;
import org.nefu.softlab.weiboAPI.common.config.SSHConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * Created by Jiaxu_Zou on 2018-4-14
 *
 * SSH连接池
 */
public class SSHPool {


    // 参数
    public static final int POOL_SIZE = 10;

    // 内置线程池
    private static Queue<List<Connection>> connections;

    // 日志
    private static final Logger logger = LoggerFactory.getLogger(MongoPool.class);

    // 锁
    private static final Lock lock = new ReentrantLock(false);  // 创建非公平锁，注重效率

    // 初始化
    static {
        init();
    }

    // 构造函数
    public SSHPool() {
        init();
    }

    /**
     * 初始化
     */
    private static void init() {
        // 创建指定大小的线程池
        connections = new LinkedList<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            // 加入
            connections.offer(createConnection());
        }
        // 打印日志
        logger.info("SSH Connection Pool (Size : " + connections.size() + ") Built Successfully .");
    }

    /**
     * 创建一组新的连接线程
     *
     * @return
     */
    private static List<Connection> createConnection() {
        List<Map<String, String>> configList = SSHConfig.configList;
        List<Connection> connections = new LinkedList<>();
        configList.stream() // 遍历所有配置信息创建所有可创建的连接并最终初始化会话
                .forEach(map -> {
                    try {
                        Connection connection = new Connection(map.get("hostname"));    // 配置连接
                        connection.connect();   // 尝试连接
                        if (connection.authenticateWithPassword(map.get("username"), map.get("password")) == false)  // 权限验证
                            throw new IOException();
                        connections.add(connection);
                    } catch (IOException e) {
                        logger.warn("SSH Connect to server " + map.get("hostname") + " failed .");
                    }
                });
        return connections;
    }

    /**
     * 获取SSH连接
     * 如果当前线程池中有线程，则从池中取
     * 否则创建新连接并返回
     * @return list of mongoClients
     */
    public static List<Connection> getConnection() {
        try {
            lock.lock();    // 加锁
            if (connections.isEmpty() == false) {   // 如果线程池中有线程
                return connections.poll();  // 出队并且返回
            } else {
                return createConnection(); // 否则创建新线程
            }
        } finally {
            logger.debug("One SSH connection left connection pool, left threads number is : " + connections.size());
            lock.unlock();  // 解锁
        }
    }

    /**
     * 归还线程
     * 可以直接将连接返还避免创建连接的开销
     * 但是容量不能超过预设的值
     */
    public static void returnConnection(List<Connection> connection) {
        try {
            lock.lock();
            if (connections.size() < POOL_SIZE)
                connections.offer(connection);      // 放回线程池
            else
                return ;
            logger.debug("One SSH connection return back to pool  , left connection number is : " + connections.size() );
        } finally {
            lock.unlock();
        }
    }

}
