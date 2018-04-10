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
     * 获取指定集合的统计统计信息
     * @param collection
     * @return
     */
    protected CommandResult getStats(DBCollection collection) {
        return collection.getStats();
    }

    /**
     * 获取磁盘占用信息
     */

}
