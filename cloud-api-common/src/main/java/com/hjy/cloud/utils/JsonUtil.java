package com.hjy.cloud.utils;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
    /**
     * 自定义获取json数据封装方法，以防取到字串类型的'null'
     * @param jsonObject json对象
     * @param paramName json参数名字
     * @return
     */
    public static String getStringParam(JSONObject jsonObject,String paramName){
        if(jsonObject.get(paramName)!=null){
            return String.valueOf(jsonObject.get(paramName));
        }
        return null;
    }
}
