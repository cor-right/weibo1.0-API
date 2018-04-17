package org.nefu.softlab.weiboAPI.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Jiaxu_Zou on 2018-4-16
 *
 * 弃用
 */
public class PropertiesUtil {

    // static
    private static final String filepathPrefix = "target/classes/";

    // logger
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);


    public static int getPropertiesIntValue(String propertyFilename, String propertyKey) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(filepathPrefix + propertyFilename)));
        } catch (IOException e) {
            logger.error("Property file \"" + propertyFilename + "\" couldn\'t load .");
        }
        return Integer.parseInt((String) properties.get(propertyKey));
    }

    public static boolean getPropertiesBooleanValue(String propertyFilename, String propertyKey) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(filepathPrefix + propertyFilename)));
        } catch (IOException e) {
            logger.error("Property file \"" + propertyFilename + "\" couldn\'t load .");
        }
        return Boolean.parseBoolean((String) properties.get(propertyKey));
    }

}
