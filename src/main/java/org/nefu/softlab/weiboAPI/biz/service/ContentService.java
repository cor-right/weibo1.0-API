package org.nefu.softlab.weiboAPI.biz.service;

import com.github.pagehelper.PageInfo;
import org.nefu.softlab.weiboAPI.core.VO.RecordsSelectVo;
import org.nefu.softlab.weiboAPI.core.po.WeiboData;
import org.nefu.softlab.weiboAPI.core.po.WeiboUser;

import java.util.List;
import java.util.Map;

/**
 * Created by Jiaxu_Zou on 2018-4-15
 *
 * 数据检索模块业务逻辑
 */
public interface ContentService {

    /**
     * 获取微博大咖的业务逻辑
     * @return
     */
    List<WeiboUser> getFamousSinaUser();

    /**
     * 获取默认的微博数和用户数的业务逻辑
     * @param user
     * @param weibo
     * @return
     */
    Map<String, Object> getRecordsNumber(Boolean user, Boolean weibo);

    List<String> getUids(RecordsSelectVo vo);

    /**
     * 检索指定条件下的数据的业务逻辑
     * @param vo
     * @return
     */
    PageInfo<WeiboData> getSpecificRecords(RecordsSelectVo vo);

    String getUid(RecordsSelectVo vo);
}
