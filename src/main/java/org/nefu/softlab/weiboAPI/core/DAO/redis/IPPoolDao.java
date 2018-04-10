package org.nefu.softlab.weiboAPI.core.DAO.redis;

import org.junit.jupiter.api.Test;
import org.nefu.softlab.weiboAPI.common.config.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Jiaxu_Zou on 2018-4-9
 *
 * 该DAO的功能是对redis中IP池相关的信息进行获取
 */
@Repository
public class IPPoolDao extends BaseDao{

    //  jedis
    private final Jedis jedis;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(IPPoolDao.class);

    // strings
    private final String namespace;
    private final String ippookey;
    private final String refreshtimekey;

    public IPPoolDao() {
        this.jedis = new Jedis(RedisConfig.host, RedisConfig.port, RedisConfig.db);
        jedis.connect();
        this.namespace = RedisConfig.namespace;
        this.ippookey = RedisConfig.namespace + ":ippool";
        this.refreshtimekey = RedisConfig.namespace + ":refreshTime";
    }

    /**
     * 获取IP列表
     * @return
     */
    public List<String> getIPList() {
        return getList(this.jedis, this.ippookey);
    }

    /**
     * 获取IP池的更新时间的表
     * .get(0)获取的是最近一次更新的时间
     * .get(1)获取的是大上次更新的时间
     * @return
     */
    public List<String> getPoolRefreshTime() {
        return getList(this.jedis, this.refreshtimekey);
    }


    @Test
    public void test() {
        getPoolRefreshTime().stream()
                .forEach(n -> {
                    System.out.println(n);
                });
    }




}
