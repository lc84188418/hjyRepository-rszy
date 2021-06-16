package com.hjy.cloud.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author liuchun
 * @Package com.hjy.common.utils
 * @date 2021/5/12 11:28
 * description:
 */
public class SystemUtil {
    /**
     * 获取程序当前运行环境是Windows还是Linux
     * @return
     */
    public static boolean runningOnWindows(){
        String system = System.getProperty("os.name");
        if (system.indexOf("Windows") >= 0) {
            return true;
        } else {
            return false;
        }
    }
    public static String getWebIp(String webIp){
        if(StringUtils.isEmpty(webIp)){
            //获取ip
            if (runningOnWindows()){
                //说明为windows系统
                webIp = PropertiesUtil.getValue("spring.cloud.application.ip.windows");
            }else {
                //说明为Linux系统
                webIp = PropertiesUtil.getValue("spring.cloud.application.ip.linux");
            }
        }
        return webIp;
    }
}
