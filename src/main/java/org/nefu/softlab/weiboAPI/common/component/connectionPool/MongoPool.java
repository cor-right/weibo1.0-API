package org.nefu.softlab.weiboAPI.common.component.connectionPool;

import com.mongodb.MongoClient;
import org.nefu.softlab.weiboAPI.common.config.MongoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * Created by Jiaxu_Zou on 2018-4-14
 *
 * MongoDB的连接池
 */
public class MongoPool {

    // 参数
    public static final int POOL_SIZE = 10;

    // 内置线程池
    public static Queue<List<MongoClient>> clients;

    // 日志
    private static final Logger logger = LoggerFactory.getLogger(MongoPool.class);

    // 锁
    private static final Lock lock = new ReentrantLock(false);  // 创建非公平锁，注重效率

    // 初始化
    static {
        init();
    }

    // 构造函数
    public MongoPool() {
    }

    /**
     * 初始化
     */
    private static void init() {
        // 创建指定大小的线程池
        clients = new LinkedList<>();
            for (int i = 0; i < POOL_SIZE; i++) {
            // 加入
            clients.offer(createClientsList());
            logger.info("Add one more set of mongoDB connection into list .");
        }
        // 打印日志
        logger.info("MongoDB Connection Pool (Size : " + clients.size() + ") Built Successfully .");
    }

    /**
     * 创建一组新的连接线程
     *
     * @return
     */
    private static List<MongoClient> createClientsList() {
        List<MongoClient> clientList =  new ArrayList<>();
        Stream.of(MongoConfig.hostlist)
                .forEach(host -> {
                    MongoClient client = new MongoClient(host, MongoConfig.port);
                    clientList.add(client);    // 初始化DBMS连接
                });
        return clientList;
    }

    /**
     * 获取一组Mongo Client
     * 如果当前线程池中有线程，则从池中取
     * 否则创建新连接并返回
     * @return list of mongoClients
     */
    public static List<MongoClient> getClientList() {
        try {
            lock.lock();    // 加锁
            if (clients.isEmpty() == false) {   // 如果线程池中有线程
                return clients.poll();  // 出队并且返回
            } else {
                return createClientsList(); // 否则创建新线程
            }
        } finally {
            logger.info("One mongoDB connection left connection pool, left threads number is : " + clients.size());
            lock.unlock();  // 解锁
        }
    }

    /**
     * 归还线程
     * 可以直接将连接返还避免创建连接的开销
     * 但是容量不能超过预设的值
     */
    public static void returnClientList(List<MongoClient> clientList) {
        try {
            lock.lock();
            if (clients.size() < POOL_SIZE)
                clients.offer(clientList);      // 放回线程池
            else
                return ;
            logger.info("One mongoDB connection return back to pool  , left connection number is : " + clients.size() );
        } finally {
            lock.unlock();
        }
    }







}
