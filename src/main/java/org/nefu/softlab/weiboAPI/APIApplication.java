package org.nefu.softlab.weiboAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * 启动类
 *
 */
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@EnableTransactionManagement
public class APIApplication {

    public static void main(String [] args) {
        SpringApplication.run(APIApplication.class, args);
    }

}
