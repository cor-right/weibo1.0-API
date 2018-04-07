package org.nefu.softlab.weiboAPI.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jiaxu_Zou on 2018-4-7
 *
 * 数据量统计模块
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api/user")
@RestController
public class StatisticsAPI {
}
