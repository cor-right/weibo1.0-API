package org.nefu.softlab.weiboAPI.common.component.connectionPool;

import com.mongodb.MongoClient;
import org.nefu.softlab.weiboAPI.common.config.MongoConfig;
import org.nefu.softlab.weiboAPI.common.config.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * Redis的连接池
 */
public class RedisPool {

    // 参数
    public static final int POOL_SIZE = 10;

    // 内置线程池
    public static Queue<Jedis> jedisCollection;

    // 日志
    private static final Logger logger = LoggerFactory.getLogger(RedisPool.class);

    // 锁
    private static final Lock lock = new ReentrantLock(false);  // 创建非公平锁，注重效率

    // 初始化
    static {
        init();
    }

    // 构造函数
    public RedisPool() {
        init();
    }

    /**
     * 初始化
     */
    private static void init() {
        jedisCollection = new LinkedList<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            jedisCollection.offer(createJedis());
        }
        logger.info("Jedis connection pool (size : " + POOL_SIZE + ") built successfully .");
    }

    /**
     * 创建一组新的连接线程
     *
     * @return
     */
    private static Jedis createJedis() {
        Jedis jedis = new Jedis(RedisConfig.host, RedisConfig.port, RedisConfig.db);
        jedis.connect();
        return jedis;
    }

    /**
     * 获取Jedis连接
     * 如果当前线程池中有线程，则从池中取
     * 否则创建新连接并返回
     * @return list of mongoClients
     */
    public static Jedis getJedis() {
        try {
            lock.lock();    // 加锁
            if (jedisCollection.isEmpty() == false)
                return jedisCollection.poll();
            else
                return createJedis();
        } finally {
            logger.debug("One jedis connection left connection pool, left threads number is : " + jedisCollection.size());
            lock.unlock();  // 解锁
        }
    }

    /**
     * 归还线程
     * 可以直接将连接返还避免创建连接的开销
     * 但是容量不能超过预设的值
     */
    public static void returnJedis(Jedis jedis) {
        try {
            lock.lock();
            if (jedisCollection.size() < POOL_SIZE)
                jedisCollection.offer(jedis);      // 放回线程池
            else
                return ;
            logger.debug("One jedis connection return back to pool , left connection number is : " + jedisCollection.size() );
        } finally {
            lock.unlock();
        }
    }


}
