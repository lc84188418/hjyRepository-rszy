package com.hjy.cloud.utils;

public class StringUtil {
    public static boolean isNotEmptyAndNull(String str){
        if(null !=str && str != "" && str != "null"){
            return true;
        }
        return false;
    }
}
