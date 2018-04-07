package org.nefu.softlab.weiboAPI.core.DAO.mongo;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.nefu.softlab.weiboAPI.common.constant.MongoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * 用于获取MongoDB的统计信息的DAO
 */
@Repository
public class StatisticsDao {

    private static final List<MongoClient> clients;
    private static final List<MongoDatabase> databases;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(StatisticsDao.class);

    static {    // 初始化连接
        clients = new ArrayList<>();
        databases = new ArrayList<>();
        Stream.of(MongoConfig.hostlist)
                .forEach(host -> {
                    MongoClient client = new MongoClient(host, MongoConfig.port);
                    clients.add(client);    // 初始化DBMS连接
                    databases.add(client.getDatabase(MongoConfig.database));    // 初始化DB连接
                });
        logger.info(clients.size() + " connections been built successfully ");
    }


    /**
     * 获取指定集合中文档的信息
     * @param collection
     * @return
     */
    public Map<String, Object> getDocumentsCount(DBCollection collection) {
        return null;
    }


}
