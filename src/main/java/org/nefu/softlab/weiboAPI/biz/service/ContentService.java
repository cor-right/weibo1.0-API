package org.nefu.softlab.weiboAPI.biz.service;

import org.nefu.softlab.weiboAPI.core.po.WeiboUser;

import java.util.List;

/**
 * Created by Jiaxu_Zou on 2018-4-15
 *
 * 数据检索模块业务逻辑
 */
public interface ContentService {

    List<WeiboUser> getFamousSinaUser();

}
