package com.hjy.cloud.utils;

import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public static Integer getIntegerParam(JSONObject jsonObject,String paramName){
        if(jsonObject.get(paramName)!=null){
            String param = String.valueOf(jsonObject.get(paramName));
            return Integer.parseInt(param);
        }
        return null;
    }
    public static Date getDateParam(JSONObject jsonObject, String pattern,String paramName) throws ParseException {
        if(jsonObject.get(paramName)!=null){
            String dateStr = (String) jsonObject.get(paramName);
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date parse = sdf.parse(dateStr);
            return parse;
        }
        return null;
    }
}
