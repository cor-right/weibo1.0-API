package org.nefu.softlab.weiboAPI.common.util;


import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * Util For Token
 */
public class TokenUtil {

    /**
     * 从Cookie中获取token
     * 如果请求中没有token则返回null
     *
     * @param request
     * @return token
     */
    public static String getToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    /**
     * 创建一个新的token
     * @return token
     */
    public static String newToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
