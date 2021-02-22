package com.hjy.cloud.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    /**
     * 获取获取配置文件中的全局变量信息,以方便后期维护修改
     * @param key 配置文件属性名
     * @return 配置文件属性值
     */
    public static String getValue(String key){
        Properties prop = new Properties();
        InputStream in = new PropertiesUtil().getClass().getResourceAsStream("/application.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}
