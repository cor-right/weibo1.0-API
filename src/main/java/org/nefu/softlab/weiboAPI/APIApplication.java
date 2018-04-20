package org.nefu.softlab.weiboAPI;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * 启动类
 *
 */
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@EnableTransactionManagement
@EnableScheduling
public class APIApplication {

    public static void main(String [] args) {
        SpringApplication.run(APIApplication.class, args);
    }

}
