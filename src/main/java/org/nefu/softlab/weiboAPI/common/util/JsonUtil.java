package org.nefu.softlab.weiboAPI.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * Util for Json String
 *
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    // org.codehaus.jackson.map.ObjectMapper
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getJsonString(Object object) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(object);
        }catch(Exception ee) {
            logger.error(ee.getMessage());
        }
        return json;
    }

}
