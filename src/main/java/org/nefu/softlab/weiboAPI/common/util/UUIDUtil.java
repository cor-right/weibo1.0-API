package org.nefu.softlab.weiboAPI.common.util;

import java.util.UUID;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 */
public class UUIDUtil {

    /**
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 构建新Token
     * @return
     */
    public static String getToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 利用UUID生成一个随机的id值
     * @return
     */
    public static String getRandomID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }

}
