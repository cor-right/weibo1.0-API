package org.nefu.softlab.weiboAPI.core.DAO.redis;

import org.nefu.softlab.weiboAPI.common.config.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * Created by Jiaxu_Zou on 2018-4-9
 *
 * 获取redis相关信息的Dao的BaseDao
 */
public abstract class BaseDao {

    private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

    private final String namespace = RedisConfig.namespace;

    // common

    /**
     * 判断redis数据库指定命名空间中是否含有参数的key
     * 参数key的格式为：   weibo:ippool
     * @param jedis
     * @param key
     * @return
     */
    public boolean hasKey(Jedis jedis, String key) {
        Set<String> keys = jedis.keys(key);
        try {
            return keys.contains(key);
        } catch(Exception ex) {
            logger.error("Exception occured during judging the exist of key : " + key);
            return false;
        }
    }

    // list

    /**
     * 获取List的长度
     * @param jedis
     * @param key
     * @return
     */
    public Long getListLength(Jedis jedis, String key) {
        return jedis.llen(key);
    }

    /**
     * 获取整个List
     * @param jedis
     * @param key
     * @return
     */
    public List getList(Jedis jedis, String key) {
        return jedis.lrange(key, 0, getListLength(jedis, key));
    }


}
