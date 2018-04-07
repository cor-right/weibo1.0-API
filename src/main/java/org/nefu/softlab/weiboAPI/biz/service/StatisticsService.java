package org.nefu.softlab.weiboAPI.biz.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-7
 *
 * 数据量统计模块业务逻辑
 */
public interface StatisticsService {

    /**
     * 获取多主机的MongoDB数据量
     * @return list of map
     */
    List<Map<String, Object>> getSplitedStatistics();


    /**
     * 获取合计的MongoDB数据量
     * 这里实在getSplitedStatistics方法的返回值的基础上再进一步的处理
     * @return list of map
     */
    Map<String, Object> getTotalStatistics();

    /**
     * 获取指定mongo的数据量
     * @param socket
     * @return
     */
    Map<String, Object> getSpecificServerStatistics(String socket);



}
