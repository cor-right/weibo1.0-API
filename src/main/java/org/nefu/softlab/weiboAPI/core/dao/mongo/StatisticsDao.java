package org.nefu.softlab.weiboAPI.core.dao.mongo;

import com.mongodb.CommandResult;
import com.mongodb.MongoClient;
import org.nefu.softlab.weiboAPI.common.component.connectionPool.MongoPool;
import org.nefu.softlab.weiboAPI.common.config.MongoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * 用于获取MongoDB的统计信息的DAO
 */
@Repository
public class StatisticsDao extends BaseDao{

    // logger
    private static final Logger logger = LoggerFactory.getLogger(StatisticsDao.class);

    /**
     * 获取多台主机的数据量统计信息形成的列表
     * @return List of Map
     */
    public List<Map<String, Object>> getSplitedStatistics() {
        // 遍历数据库的连接获取DBCollection，藉此形成Map的List
        List<Map<String, Object>> returnList = new ArrayList<>();
        List<MongoClient> clients = MongoPool.getClientList();
        clients.stream()
                .forEach(client -> {
                    Map<String, Object> data = new HashMap<>();
                    CommandResult stats = super.getStats(client.getDB(MongoConfig.database).getCollection(MongoConfig.collection)); // 调用basedao中的方法
                    // 装载stats中的信息到returnData映射中并返回Map
                    data.put("count", stats.get("count"));
                    data.put("size", stats.get("size"));
                    data.put("avgSize", stats.get("avgObjSize"));
                    data.put("storageSize", stats.get("storageSize"));
                    data.put("totalIndexSize", stats.get("totalIndexSize"));
                    data.put("status", stats.ok());
                    data.put("host", client.getAddress());  // 加入basedao中无法加入的属性
                    returnList.add(data);   // 添加到返回值当中
                });
        MongoPool.returnClientList(clients);
        return returnList;
    }

    /**
     * 获取当前所有数据库中所有微博记录的个数
     * @return
     */
    public long getTotalRecordCount() {
        final Long[] sum = {0L};    // 流操作内部只能有逻辑常量
        List<MongoClient> clients = MongoPool.getClientList();
        clients.stream()
                .forEach(client -> {
                    CommandResult stats = super.getStats(client.getDB(MongoConfig.database).getCollection(MongoConfig.collection)); // 调用basedao中的方法
                    sum[0] += (long)((int)stats.get("count"));
                });
        MongoPool.returnClientList(clients);
        return sum[0];
    }

}
