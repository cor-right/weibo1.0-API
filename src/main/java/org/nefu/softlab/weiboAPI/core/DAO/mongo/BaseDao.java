package org.nefu.softlab.weiboAPI.core.DAO.mongo;

import com.mongodb.CommandResult;
import com.mongodb.DBCollection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-7
 *
 * 将通用的对MongoDB的操作进行封装
 */
public abstract class BaseDao {

    /**
     * 获取指定集合的统计信息
     * @param collection
     * @return
     */
    protected Map<String, Object> getDocumentsProperties(DBCollection collection) {
        // 初始化
        Map<String, Object> returnData = new HashMap<>();
        CommandResult stats = collection.getStats();
        // 装载stats中的信息到returnData映射中并返回Map
        returnData.put("count", stats.get("count"));
        returnData.put("size", stats.get("size"));
        returnData.put("avgSize", stats.get("avgObjSize"));
        returnData.put("storageSize", stats.get("storageSize"));
        returnData.put("totalIndexSize", stats.get("totalIndexSize"));
        returnData.put("status", stats.ok());
        return returnData;
    }

    /**
     * 获取磁盘占用信息
     */


}
