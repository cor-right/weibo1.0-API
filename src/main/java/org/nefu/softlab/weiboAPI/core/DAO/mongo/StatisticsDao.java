package org.nefu.softlab.weiboAPI.core.DAO.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.nefu.softlab.weiboAPI.common.config.MongoConfig;
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
public class StatisticsDao extends BaseDao{

    // connections
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
     * 获取多台主机的数据量统计信息形成的列表
     * @return List of Map
     */
    public List<Map<String, Object>> getSplitedStatistics() {
        // 遍历数据库的连接获取DBCollection，藉此形成Map的List
        List<Map<String, Object>> returnList = new ArrayList<>();
        clients.stream()
                .forEach(client -> {
                    Map data = super.getDocumentsProperties(client.getDB(MongoConfig.database).getCollection(MongoConfig.collection)); // 调用basedao中的方法
                    data.put("host", client.getAddress());  // 加入basedao中无法加入的属性
                    returnList.add(data);   // 添加到返回值当中
                });
        return returnList;
    }


    public static List<MongoClient> getClients() {
        return clients;
    }

    public static List<MongoDatabase> getDatabases() {
        return databases;
    }
}
