package org.nefu.softlab.weiboAPI.core.DAO.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-9
 *
 * 该DAO的功能是对redis中IP池相关的信息进行获取
 */
@Repository
public class IPPoolDao {

    private final RedisTemplate redisTemplate;


    @Autowired
    public IPPoolDao(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        System.out.println(redisTemplate.opsForList().range("weibo:ippool", 0, 8));
    }


    /**
     * 将IP池中当前的IP以列表的形式全部返回
     * 每个IP以字符串的形式保存，每个字符串的格式是一个socket
     * @return
     */
    public List<String> getIPPoolList() {
        return redisTemplate.opsForList().range("weibo:ippool", 0, 8);
    }



}
